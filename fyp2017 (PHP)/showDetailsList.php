<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
		   
		
		$tid = $_POST['tID'];
	    //$tid = "JKA11";
	   
		$result = mysqli_query($con,"SELECT Did, Date, Time, Title, Address, Content FROM TravelDetails WHERE Tid = '$tid'");


		while($row = mysqli_fetch_array($result) ){
			$aDid = $row['Did']."%";
			$aDate = $row['Date']."%";
			$aTime = $row['Time']."%";
			$aTitle = $row['Title']."%";
			$aAddress = $row['Address']."%";
			$aContent = $row['Content']."%";
			
			echo $aDid;
			echo $aDate;
			echo $aTime;
			echo $aTitle;
			echo $aAddress;
			echo $aContent;

		}
				
		
		
		if( mysqli_num_rows($result) <= 0){
			echo " %";
			echo " %";
			echo " %";
			echo "No detail Here%";
			echo " %";
			echo " %";			
		}
		
		
		mysqli_close($con);
	}
	
?>