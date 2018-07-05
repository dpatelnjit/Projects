<?php /*
 Dhruv Patel - Mid End

 Group Members:
 Elad Bergrin
 Daniel Thomas
 */

$JSON_Data = json_decode(file_get_contents('php://input'), true); //Decode JSON data from FrontEnd
echo "<br><br><b> JSON Data From FrontEnd -- </b><br>";
var_dump($JSON_Data);
$user = $JSON_Data["user"]; //get user
$examName = $JSON_Data["questions"]["test"]; //get exam name
$getAnswers = $JSON_Data ["questions"]["description"]; //get the inputed exam answers

$examNameCurl = array('ExamName' => $examName);
$getExam = json_encode(array("RequestType" => "seeExam", "Data" => $examNameCurl));
$getTestQuestions = json_decode(Curl_To_Back("http://afsaccess3.njit.edu/~eb86/cs490/model.php", $getExam), true);  //get exam info
echo "<br><br><b> Exam Information From BackEnd -- </b><br>";
var_dump($getTestQuestions);

/**
 * PARSE PYTHON METHODS
 */

 
$questions = $getTestQuestions;
$answers = $getAnswers;
$user = $user;
$examName = $examName;
$allAnswersData = [];


for ($x = 0; $x < count($questions); $x++) {
	$question = $questions[$x];
	$answer = nl2br(trim($answers[$x]));
	
	echo "<br><br><b> Question -- </b><br>";
	var_dump($question);
	
	echo "<br><br><b> Answer -- </b><br>";
	var_dump($answer);
		
	$questionFunctionName = $question['functionName'];
	$questionOriginalAnswer = $answers[$x];
	$questionText = $question["Text"];
	$questionPoints = $question["Points"];
	$questionReturnType = $question["returnType"];
	$questionConstraints = explode(',', $question["Constraints"]);
	$questionParameterTypes = explode(',', $question["Parameter types"]);
	$questionParameterNames = explode(',', $question["Parameter names"]);
	$questionParameters = array();
	$questionInputs = explode(';', $question["inputs"]);
	$questionOutputs = explode(';', $question["outputs"]);	
	
	$questionInputs = array_map('trim', $questionInputs);
	$questionOutputs = array_map('trim', $questionOutputs);

	
	for ($q = 0; $q < count($questionParameterTypes); $q++) {
		array_push($questionParameters, array($questionParameterTypes[$q], $questionParameterNames[$q]));
	}
	
	foreach($questionInputs as $k=>$v){
		$questionInputs[$k] = explode(',', $v);
	}
	
	foreach($questionOutputs as $k=>$v){
		$questionOutputs[$k] = explode(',', $v);
	}
	
	if(count($questionDetails["questionConstraints"]) == 1 && $questionDetails["questionConstraints"] == ""){
		echo "KEKEK";
	}
		
	$questionDetails = array("questionFunctionName" => $questionFunctionName, "questionOriginalAnswer" => $questionOriginalAnswer, "questionText" => $questionText, "questionPoints" => $questionPoints, "questionReturnType" => $questionReturnType, "questionParameters" => $questionParameters, "questionConstraints" => $questionConstraints);
	//all question details used later for checking answer
	echo "<br><br><b> Original Question Details -- </b><br>";
	var_dump($questionDetails);
		
	$allLines = preg_split("/\\r\\n|\\r|\\n/", $answer);
	
	$lines = array(); //store all non empty lines (used for parsing later)
	for ($a = 0; $a < count($allLines); $a++) {
		$line = $allLines[$a];
		$line = str_replace("<br />", "", $line); //remove excess <br> tags from current line
		if ($line != "") {//not a empty line
			var_dump($line);
			array_push($lines, $line);
		}
	}

	$pythonSyntax = array('{', '}', '(', ')', '[', ']', '==', '!=', '<', '>', '<=', '>=', ';', ',', ':', '+', '-', '*', '/');

	for ($a = 0; $a < count($lines); $a++) { 	//add space before and after syntaxes to check each token individually
		if(substr_count($lines[$a], '=') == 1){
			$lines[$a] = str_replace('=', ' ' . '=' . ' ', $lines[$a]);
		} else if(((substr_count($lines[$a], '\'') > 0 || substr_count($lines[$a], '\"') > 0)) && (substr_count($lines[$a], '+') > 0 || substr_count($lines[$a], '-') > 0 || substr_count($lines[$a], '*') > 0 || substr_count($lines[$a], '/') > 0)) {
			if( (substr_count($lines[$a], "==")) == 1){
				$lines[$a] = str_replace('==', ' ' . '==' . ' ', $lines[$a]);
			}
			if( (substr_count($lines[$a], ":")) == 1){
				$lines[$a] = str_replace(':', ' ' . ':' . ' ', $lines[$a]);
			}
			continue;
		} else {
			for ($b = 0; $b < count($pythonSyntax); $b++) { 	//add space before and after syntaxes to check each token individually
				$lines[$a] = str_replace($pythonSyntax[$b], ' ' . $pythonSyntax[$b] . ' ', $lines[$a]);
			}
		}
	}

	echo "<br><br><b> All Non Empty Lines (Before Parsing) -- </b><br>";
	var_dump($lines);
	
	$testCasesOutput = array(); 	//store all testCases outputs
	for ($w = 0; $w < count($questionInputs); $w++) { //loop through the parser to get a output from the testcase inputs
		/**
			INITIALIZE/RESET VARIABLES BEFORE PARSING ANSWER
		**/
		
		$parameters = array(); 	//store method parameters
		$variables = array(); 	//store variables
		$constraints = array();  //store all constraints found
		$answerProp = array(); 	//store all answer properties while parsing
		$answerProp["methodReturn"] = array(); //store method's return type, return value
		$answerProp["constraints"] = array(); //store method's constraints
		$validConstraint = true;
		$ifDone = false;
		$maxscore = $questionPoints;
		$score = number_format($maxscore, 2);
		$comments = "";
		$returnType = "";
		$returnValue = "";
		$answer = "";
		$SyntaxError = 0;
		$indentCount = 0;
		$methodDec = false;
		$methodDone = false;
		$operation = false;
		$state = 1; //starting state, used to iterate through each word/token
			
		for($q = 0; $q < count($questionInputs[$w]); $q++){  //initialize testcases inputs
			$varName = $questionParameters[$q][1];
			$varValue = $questionInputs[$w][$q];
			if (($varValue[0] == "\"" && substr($varValue, -1) == "\"") || ($varValue[0] == "'" && substr($varValue, -1) == "'")) {
				array_push($variables, array($varName, $varValue, 'String'));
			} else if (isfloat($varValue)) {
				array_push($variables, array($varName, $varValue, 'float'));
			} else if (is_numeric($varValue)) {
				array_push($variables, array($varName, $varValue, 'int'));
			} else if (isBool($varValue)) {
				array_push($variables, array($varName, $varValue, 'boolean'));
			}
		}
		
		echo "<br><br><b> PARAMTERS INITIALIZATION -- </b><br>";
		var_dump($variables);
		
		for ($i = 0; $i < count($lines); $i++) {
			if($methodDone) {
				break;
			}
			
			if (trim($lines[$i]) == "") {
				continue;
			}//in case empty line, skip it
						
			$indentCountLine = strlen($lines[$i])-strlen(ltrim($lines[$i]));
			if($indentCountLine < $indentCount){
				$comments .= "Incorrect Indentation" . " (-5%)\n";
				$score -= ($maxscore * .05);
			}		
			
			$words = preg_split('/\s+/', $lines[$i]);
			$variableType = "";
			$variableName = "";

			if (!$validConstraint)
				$validConstraint = false;
			else
				$validConstraint = true;

			if (!$ifDone)
				$ifDone = false;
			else
				$ifDone = true;

			$line;
			$semi;

			if($methodDec){
				$stage = 50;
			}

			for ($j = 0; $j < count($words); $j++) {
				if($methodDone) {
					break;
				}
				$word = trim($words[$j]);
				if ($word == "") {
					continue;
				}//in case empty word, skip it
				echo "<br><br><b> WORD: " .$word. " STATE: ".$state. "</b><br>";
				switch($state) {
					case 1 :
						if ($word == "def") {
							$state = 2;
						} else {
							$comments .= "Missing def in method declaration (-5%)\n";;
							$score -= ($maxscore * .05);
							$state = 2;
						}
						break;
					case 2 :
						$answerProp["methodName"] = $word;
						$state = 3;
						break;
					case 3 :
						if ($word == '(') {
							$state = 4;
						}
						break;
					case 4 :
						if ($word == ')') {
							$state = 5;
						} else if ($word == ',') {
							$state = 4;
						} else {
							array_push($parameters, $word);
							$state = 4;
						}
						break;
					case 5 :
						if ($word == ':') {
							$state = 50;
						} else {
							$comments .= "Syntax Error: Missing semi colon in method declaration (-5%)\n";
							$score -= ($maxscore * .02);
							$state = 50;
						}
						$methodDec = true;
						$indentCount++;
						break;
					case 50 :
						if ($word == "for") {
							$constraintName = $word;
							$validConstraint = true;
							array_push($constraints, "for loop");
							$state = 2000;
						} else if ($word == "while") {
							$constraintName = $word;
							$validConstraint = true;
							array_push($constraints, "while loop");
							$state = 3000;
						} else if ($word == "if") {
							$constraintName = $word;
							$validConstraint = true;
							array_push($constraints, "if/else");
							$state = 4000;
						} else if ($word == "return") {
							$state = 90;
						} else {
							$state = 1000;
							$variableName = $word;
						}
						break;

					/**
					 Initializing variables
					 */
					case 1000 :
						if ($word == "=" && $variableName != "") {
							$state = 1001;
						} else {
							$state = 50;
						}
						break;
					case 1001 :
						if (($word[0] == "\"" && substr($word, -1) == "\"") || ($word[0] == "'" && substr($word, -1) == "\'")) {
							$varType = 'String';
						} else if (isfloat($word)) {
							$varType = 'float';
						} else if (is_numeric($word)) {
							$varType = 'int';
						}  else if (isBool($word)) {
							$varType = 'boolean';
						} else {
							$varType = 'undefined';
						}
						array_push($variables, array($variableName, $word, $varType));
						$variableName = "";
						$state = 50;
						break;

					/**
					 For Loop
					 */
					case 2000 :
						$state = 50;
						break;

					/**
					 While Loop
					 */
					case 3000 :
						$state = 50;
						break;

					/**
					 If/Else
					 */
					case 4000 :
						$ifVar1 = "";
						if (($word[0] == "\"" && substr($word, -1) == "\"") || ($word[0] == "'" && substr($word, -1) == "'") || (isfloat($word)) || (is_numeric($word)) || (isBool($word))) {
							$ifVar1 = $word;
						} else {
							for ($k = 0; $k < count($variables); $k++) {
								if ($variables[$k][0] == $word) {
									$ifVar1 = $variables[$k][1];
									break;
								}
							}
						}
						$state = 4001;					
						break;
					case 4001:
						$ifOperator = $word;
						$state = 4002;
						break;
					case 4002:
						$ifVar2 = "";
						var_dump($word);
						if (($word[0] == "\"" && substr($word, -1) == "\"") || ($word[0] == "'" && substr($word, -1) == "'") || (isfloat($word)) || (is_numeric($word)) || (isBool($word))) {
							$ifVar2 = $word;
						} else {
							for ($k = 0; $k < count($variables); $k++) {
								if ($variables[$k][0] == $word) {
									$ifVar2 = $variables[$k][1];
									break;
								}
							}
						}
						$state = 4003;
						break;
					case 4003:
						echo "<br><br><b> IF ELSE VARIABLES -- </b><br>";
						if($word == ":"){
							echo $ifVar1 . "  " . $ifOperator . "  " . $ifVar2;
							var_dump($ifVar1);
														var_dump($ifVar2);

							if($ifOperator == "=="){
								if($ifVar1 == $ifVar2) {
									echo "<br><br><b> FOUND THE RIGHT IFELSE -- </b><br>";
									echo $ifVar1 . "  " . $ifOperator . "  " . $ifVar2;
									$state = 50;
								} else {
									$state = 4004;
								}
							}
						}
						break;
					case 4004:
						if($word == "elif") {
							$state=4000;
						} else if($word == "else") {
							$state=4005;
						} else {
						  $state = 4004;
						}
						break;				    
					case 4005:
						if($word == ":"){
							$state = 50;
						} else {
							$state = 4005;
						}
						break;
					/**
					 Return
					 */
					case 90 :
						$returnVar1 = $word;
						$nextWord = trim($words[$j+1]);
						if($nextWord == "+" || $nextWord == "-" || $nextWord == "*" || $nextWord == "/"){
							$operation = true;
						}
						var_dump($operation);
	
						if($operation == true){
							echo '<br><b>OPERATION RETURN</b></br>';
							$state = 91;
						} else {
							if (($word[0] == "\"" && substr($word, -1) == "\"") || ($word[0] == "'" && substr($word, -1) == "'")) {
								array_push($answerProp["methodReturn"], array('String', $word));
							} else if (isfloat($word)) {
								array_push($answerProp["methodReturn"], array('float', $word));
							} else if (is_numeric($word)) {
								array_push($answerProp["methodReturn"], array('int', $word));
							} else if (isBool($word)) {
								array_push($answerProp["methodReturn"], array('boolean', $word));
							} else {
								for ($k = 0; $k < count($variables); $k++) {
									if ($variables[$k][0] == $word) {
										array_push($answerProp["methodReturn"],  array($variables[$k][2], $variables[$k][1]));
										var_dump($answerProp["methodReturn"]);
										break;
									}
								}
							}
							$state = 100;
						}
						break;
					case 91:
						$returnOp = $word;
						$state = 92;
						break;
					case 92:
						$returnVar2 = $word;
						$returnVar1;
						if (($returnVar1[0] == "\"" && substr($returnVar1, -1) == "\"") || ($returnVar1[0] == "'" && substr($returnVar1, -1) == "'") || (isfloat($returnVar1)) || (is_numeric($returnVar1)) || (isBool($returnVar1))) {
							$returnVar1 = $returnVar1;
						} else {
							for ($k = 0; $k < count($variables); $k++) {
								if ($variables[$k][0] == $returnVar1) {
									$returnVar1 = $variables[$k][1];
									break;
								}
							}
						}
						
						$returnVar2;
						if (($returnVar2[0] == "\"" && substr($returnVar2, -1) == "\"") || ($returnVar2[0] == "'" && substr($returnVar2, -1) == "'") || (isfloat($returnVar2)) || (is_numeric($returnVar2)) || (isBool($returnVar2))) {
							$returnVar2 = $returnVar2;
						} else {
							for ($k = 0; $k < count($variables); $k++) {
								if ($variables[$k][0] == $returnVar2) {
									$returnVar2 = $variables[$k][1];
									break;
								}
							}
						}				
						
						if($returnOp == "+") {
							$returnValue = $returnVar1 + $returnVar2;					
						} else if ($returnOp == "-") {
							$returnValue = $returnVar1 - $returnVar2;					
						} else if ($returnOp == "*") {
							$returnValue = $returnVar1 * $returnVar2;					
						} else if ($returnOp == "/") {
							$returnValue = $returnVar1 / $returnVar2;					
						}
						
						
						if (($returnValue[0] == "\"" && substr($returnValue, -1) == "\"") || ($returnValue[0] == "'" && substr($returnValue, -1) == "'")) {
							array_push($answerProp["methodReturn"], array('String', $returnValue));
						} else if (isfloat($returnValue)) {
							array_push($answerProp["methodReturn"], array('float', $returnValue));
						} else if (is_numeric($returnValue)) {
							array_push($answerProp["methodReturn"], array('int', $returnValue));
						} else if (isBool($returnValue)) {
							array_push($answerProp["methodReturn"], array('boolean', $returnValue));
						} else {
							for ($k = 0; $k < count($variables); $k++) {
								if ($variables[$k][0] == $returnValue) {
									array_push($answerProp["methodReturn"],  array($variables[$k][1], $variables[$k][2]));
									var_dump($answerProp["methodReturn"]);
									break;
								} else {
									$answerProp["methodReturn"] =  array('undefined', $returnValue);
								}
							}
						}
						$state = 100;
					case 100:
						echo '<b><br>METHOD RETURN</b><br>';
						var_dump($answerProp["methodReturn"]);
						echo '<b><br>METHOD TEST CASE OUTPUT</b><br>';
						array_push($testCasesOutput,  $answerProp["methodReturn"][0][1]);
						var_dump($testCasesOutput);
						$methodDone = true;
						break;
				}
			}
		}
	}
	
	$answerProp["methodParameter"] = $parameters;
	$answerProp["testCasesOutput"] = $testCasesOutput;

	$leftPara = substr_count($answer, '(');
	$rightPara = substr_count($answer, ')');

	echo "<br><br><b> Answer Properties -- </b><br>";
	var_dump($answerProp);

	if ($leftPara != $rightPara) {
		$difference = abs($leftPara - $rightPara);
		$score -= $difference * ($maxscore * .02);
		$comments .= "Syntax Error: Invalid Opening/Closing Parentheses (-2%)\n";
	}
	
	if ($questionDetails["questionFunctionName"] != $answerProp["methodName"]) {
		$score -= ($maxscore * .15);
		$comments .= "Invalid Method Name (-15%)\n";
	}
	
	sort($questionParameterNames);
	sort($answerProp["methodParameter"]);
	echo "<br><br><b> PARAMETERS -- </b><br>";
	var_dump($answerProp["methodParameter"]);
	
	if ($questionParameterNames!=$answerProp["methodParameter"]) {
		echo "<br><br><b> PARAMETERS ARE NO GOOD -- </b><br>";
		$score -= ($maxscore * .15);
		$comments .= "Invalid Parameters (-20%)\n";
	}
	sort($questionDetails["questionConstraints"]);
	sort(answerProp["$constraints"]);
	echo "<br><br><b> CONSTRAINTS -- </b><br>";
	var_dump($answerProp["$constraints"]);
	var_dump($questionDetails["questionConstraints"]);
	if ($questionDetails["questionConstraints"]!=answerProp["$constraints"]) {
		echo "<br><br><b> CONSTRAINTS ARE NO GOOD -- </b><br>";
	}
	
	for($h = 0; $h < count($questionOutputs); $h++){		
		if(trim($questionOutputs[$h][0]) == $testCasesOutput[$h]){
			echo "<br><br><b> TEST CASE GOOD -- </b><br>";
			$comments .= "<b>Correct Test Case</b> | Input:" . json_encode(($questionInputs[$h])) . " Expected Output: " . $questionOutputs[$h][0] . " Actual Output: " . $testCasesOutput[$h] . "\n";
		} else {
			$score -= ($maxscore * .15);
			$comments .= "<b>Incorrect Test Case</b> | Input: </b>" . json_encode(($questionInputs[$h])) . " Expected Output: " . $questionOutputs[$h][0] . " Actual Output: " . $testCasesOutput[$h] . " (-15%)\n";
		}
	}
	
	sort($variables);
	echo "<br><br><b> VARIABLES -- </b><br>";
	var_dump($variables);
	
	echo "<br><br><b> ANSWER METHOD RETURN -- </b><br>";
	var_dump($answerProp);
	
	echo "<br><br><b> QUESTION METHOD RETURN -- </b><br>";
	var_dump($questionDetails["questionReturnType"]);
	
	$comments = str_replace("\n", "</br>", $comments);
	
	echo "<br><b>RETURN VALUE<br></b>";
	$returnType =  $answerProp["methodReturn"][0][0];
	$returnValue = $answerProp["methodReturn"][0][1];
	
	if($score < 0){
	     $score = 0;
	}
	
	$answerData = array("code" => $questionOriginalAnswer, "feedback" => $comments, "credit" => $score, "userID" => $user, "questionText" => $questionText, "examName" => $examName, "returnType" => $returnType, "returnValue" => $returnValue);
	array_push($allAnswersData, $answerData);
	
	echo "<br><br><b>QUESTION DATA<br><br></b>";
	var_dump($questionDetails);
	echo "<br><br><b>ANSWER DATA<br><br></b>";
	var_dump($answerData);
}

echo '<br><br><b>ALL ANSWER DATA</b></br><br>';
var_dump($allAnswersData);
echo '<br><br>';
	
$curlAnswerData = json_encode(array("RequestType" => "submitAnswer", "Data" => $allAnswersData)); 
   
$answersCurlResponse = Curl_To_Back("http://afsaccess3.njit.edu/~eb86/cs490/model.php", $curlAnswerData);

echo $answersCurlResponse;

function Curl_To_Back($url, $data) {
	$Curl_Back = curl_init();
	curl_setopt($Curl_Back, CURLOPT_URL, $url);
	curl_setopt($Curl_Back, CURLOPT_POST, TRUE);
	curl_setopt($Curl_Back, CURLOPT_POSTFIELDS, $data);
	curl_setopt($Curl_Back, CURLOPT_RETURNTRANSFER, TRUE);
	$headers = array('Accept: application/json', 'Content-Type: application/json');
	curl_setopt($Curl_Back, CURLOPT_HTTPHEADER, $headers);

	$Backend_Response = curl_exec($Curl_Back);
	//CURL request to BackEnd

	curl_close($Curl_Back);

	unset($Curl_Back);
	return $Backend_Response;
	//receive JSON response from Back End
}

function isfloat($num) {
    return is_float($num) || is_numeric($num) && ((float) $num != (int) $num);
}

function isBool($string){
    $string = strtolower($string);
    return (in_array($string, array("true", "false", "1", "0", "yes", "no"), true));
}
?>