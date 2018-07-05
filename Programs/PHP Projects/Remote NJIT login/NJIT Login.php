<?php
/*
Dhruv Patel - Mid End

Group Members:
Elad Bergrin
Daniel Thomas
*/

function NJIT_Login($user, $pass) {
	$Login_Curl = curl_init();
	curl_setopt($Login_Curl, CURLOPT_URL, "https://cp4.njit.edu/cp/home/login");	//NJITs login form URL
	curl_setopt($Login_Curl, CURLOPT_SSL_VERIFYPEER, FALSE);	//Compatible with HTTPS
	curl_setopt($Login_Curl, CURLOPT_RETURNTRANSFER, TRUE);	//output no results
	curl_setopt($Login_Curl, CURLOPT_FOLLOWLOCATION, FALSE);	//ignore all redirects
	curl_setopt($Login_Curl, CURLOPT_POST, TRUE);	//Allow POST variables to be sent
	curl_setopt($Login_Curl, CURLOPT_POSTFIELDS, "pass=$pass&user=$user&uuid=0xACA021");	//Send POST variables to NJIT login form
	
	$contents = curl_exec($Login_Curl); //CURL request content to NJIT login
	
	curl_close($Login_Curl);
	unset($Login_Curl);

	return strpos($contents, "loginok.html") !== false; //Check if CURL request had a redirect to a "Successful Login" page
}

function Curl_To_Back($url, $data) {
	$Curl_Back = curl_init();
	curl_setopt($Curl_Back, CURLOPT_URL, $url);
	curl_setopt($Curl_Back, CURLOPT_POST, TRUE);
	curl_setopt($Curl_Back, CURLOPT_POSTFIELDS, $data);
	curl_setopt($Curl_Back, CURLOPT_RETURNTRANSFER, TRUE);
	$headers= array('Accept: application/json','Content-Type: application/json'); 
	curl_setopt($Curl_Back, CURLOPT_HTTPHEADER, $headers); 	
	
	$Backend_Response = curl_exec($Curl_Back); //CURL request to BackEnd 

	curl_close($Curl_Back);
	
	unset($Curl_Back);
	return $Backend_Response; //receive JSON response from Back End
}

$JSON_Data = json_decode(file_get_contents('php://input'), true); //Decode JSON data from FrontEnd

$BackEndJSON = json_decode(Curl_To_Back("http://afsaccess3.njit.edu/~eb86/cs490/model.php", json_encode($JSON_Data)), true); //Decode JSON data from MidEnd

$user = $JSON_Data["username"];
$pass = $JSON_Data["password"];

if(NJIT_Login($user , $pass)){
	$NJITValid = TRUE;
}
else{
	$NJITValid = FALSE;
}

$BackEndJSON["NJITValid"] = $NJITValid; //Update JSON from MidEnd and append if user/pass is a valid NJIT account 

echo json_encode($BackEndJSON);
?>