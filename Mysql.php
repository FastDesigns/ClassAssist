<?php

require_once 'login/constants.php';

class Mysql{
	private $conn;

	function __construct() {
		$this->conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME) or 
			die('Problem connecting to Database');
	}

	function verify_Username_and_Pass($un, $pwd) {

		$query = "SELECT *
			FROM Users 
			WHERE username = ? 
			AND password = ?
			LIMIT 1";

		if($stmt = $this->conn->prepare($query)) {
			$stmt->bind_param('ss', $un, $pwd);
			$stmt->execute();

			if($stmt->fetch()) {
				$stmt->close();
				return true;
			}
		}
	}
}
