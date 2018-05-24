<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
		   
		
		$dID = $_POST['dID'];

	   
		$result = mysqli_query($con,"SELECT Date,Time, Address, Title, Content, Image FROM TravelDetails WHERE Did = '$dID'");


		while($row = mysqli_fetch_array($result) ){
			$aDate = $row['Date']."%";
			$aTime = $row['Time']."%";
			$aAddress = $row['Address']."%";
			$aTitle = $row['Title']."%";
			$aContent = $row['Content']."%";
			$aImage = $row['Image']."%";

			echo $aDate;
			echo $aTime;
			echo $aAddress;
			echo $aTitle;
			echo $aContent;
			echo $aImage;


		}
		
		mysqli_close($con);
	}
	
?>