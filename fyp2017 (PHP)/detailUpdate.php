<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
  
		$Did = $_POST['did'];
		$date =  $_POST['date'];
		$time =  $_POST['time'];
		$address =  $_POST['address'];
		$title =  $_POST['title'];
		$content =  $_POST['content'];
		$image =  $_POST['image'];
		 
		$sql = "UPDATE TravelDetails SET Date = '$date' , Time = '$time' , Address = '$address' , Title = '$title' , Content = '$content', Image = '$image' where Did = '$Did'";

		$result = mysqli_query($con,$sql);
		
		if($result!=null){
		  echo "1,";
		  echo "update success,";
		}else{
		  echo "0,";
		  echo "cannot update,";
		}
	}
  
?>