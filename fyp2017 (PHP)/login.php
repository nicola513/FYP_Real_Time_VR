<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "1,";
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
	
	   $email= $_POST['email'];
	   $password = $_POST['password'];
	   $result = mysqli_query($con,"SELECT Userid FROM Account where 
	   Email='$email' and Password='$password'");
	   $row = mysqli_fetch_array($result);
	   $data = $row[0];

		$sql= mysqli_query($con,"SELECT Userid, Username FROM Account where Email='$email' and Password='$password'");


	   if(sizeof($row)>0){
		  echo "0,";
		  
			while($r = mysqli_fetch_array($sql) ){
			$aUserid = $r['Userid'].",";
			$aUsername = $r['Username'].",";


			echo $aUserid;
			echo $aUsername;

		}
	   }else{
		  echo "1,";
		  echo "The email does not exist or the password is wrong.";
	   }
		
	   mysqli_close($con);
   }
?>