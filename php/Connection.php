<?php
$server_name = "localhost";
$user_name = "root1";
$password = "root1";
$db= 'freebieslearning';
$conn = mysqli_connect($server_name, $user_name, $password,$db);

if(!$conn){
	die("Error Connection");
}

?>