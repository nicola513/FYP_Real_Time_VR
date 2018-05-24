<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
		   
		
		$tid = 'u753t08';
	   
		$result = mysqli_query($con,"SELECT Userid, Username FROM Account");


		while($row = mysqli_fetch_array($result) ){
			$aUserid = $row['Userid'].",";
			$aUsername = $row['Username'].",";


			echo $aUserid;
			echo $aUsername;

		}
		
		mysqli_close($con);
	}
	
?>