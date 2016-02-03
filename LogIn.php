<?php

session_start();
require_once 'login/membership.php';
$membership = new membership();

if($_POST && !empty($_POST['username']) && !empty($_POST['pwd'])) {
	$response = $membership->validate_user($_POST['username'], $_POST['pwd']);	
}

if(isset($response))
{
	echo $response;
}
?>
