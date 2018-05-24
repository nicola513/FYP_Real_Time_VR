<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
	
		$userid = $_POST['userId'];
		$travelId = $_POST['travelId'];
		$title =  $_POST['title'];
		$image =  $_POST['image'];

	   
		$sql = "UPDATE Travelogue SET Title = '$title', Image = '$image' where Userid = '$userid' and Tid = '$travelId'";

		$result = mysqli_query($con,$sql);
		

		
		if($result!=null){
			echo "1,";
			echo "Update success";
		}else{
			echo "0,";
			echo "cannot update";
		}
	}
	
?>