<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
	
		$imageID =  $_POST['imageID'];
		$image =  $_POST['image'];
	   
		$sql = "UPDATE ImageTable SET Image = '$image' where Image_id = '$imageID'";

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