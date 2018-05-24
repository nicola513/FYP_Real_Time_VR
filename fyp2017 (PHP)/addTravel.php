<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
   
		$title =  $_POST['travelTitle'];
		$image =  $_POST['image'];	   
		$userid = $_POST['userID'];
	   
	   
		$result = mysqli_query($con,"SELECT COUNT(*) FROM Travelogue where Userid = '$userid' ;");
			$row = mysqli_fetch_array($result);
			$data = $row[0];
			$num = $data+1;
			$temp = $userid.$num;
			$add = $num+1;
		
		
		$result2 = mysqli_query($con,"SELECT Tid FROM Travelogue where Tid = '$temp';");
		
		$rown = mysqli_fetch_array($result2);
		
		echo $rown[0]."||";
		
		if( mysqli_num_rows($result2) > 0){
			echo $userid.$add;
			
			
			$result = mysqli_query($con,"INSERT INTO Travelogue (Tid,Userid,Title,Image) VALUES ('$userid$add','$userid', '$title','$image')");
			
			
			
			
		}else{
			echo "ok,";
			echo $temp;
			
			
			$result = mysqli_query($con,"INSERT INTO Travelogue (Tid,Userid,Title,Image) VALUES ('$temp','$userid', '$title','$image')");
			
			
			
		}
		
		
		//echo $in;

	}
	
?>