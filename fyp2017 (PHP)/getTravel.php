<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
		   
		$userID = $_POST['userID'];
		$tID = $_POST['tID'];

	   
	$result = mysqli_query($con,"SELECT Tid, Title, ImageTable.Image FROM Travelogue, ImageTable WHERE Travelogue.Image_id = ImageTable.Image_id and Tid = '$tID'");



		while($row = mysqli_fetch_array($result) ){
			$aTid = $row['Tid'].",";
			$aTitle = $row['Title'].",";
			$aImage = $row['Image'].",";

			echo $aTid;
			echo $aTitle;
			echo $aImage;


		}
		
		mysqli_close($con);
	}
	
?>