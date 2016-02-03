<?php

require 'login/Mysql.php';

class Membership {

	function validate_user($un, $pwd) {
		$mysql = New Mysql();
		$ensure_credentials = $mysql->verify_Username_And_Pass($un, $pwd);

		if($ensure_credentials) {
			$_SESSION['status'] = 'authorized';
			$_SESSION['username'] = $un;
			return true;
		} else return false;
	}
}
