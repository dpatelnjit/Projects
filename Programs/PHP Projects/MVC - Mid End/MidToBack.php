<?php
/*
Dhruv Patel - Mid End

Group Members:
Elad Bergrin
Daniel Thomas
*/

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

echo json_encode($BackEndJSON);
?>