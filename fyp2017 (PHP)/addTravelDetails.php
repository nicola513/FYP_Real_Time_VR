<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
	
	    $tid = $_POST['tID'];
		$did = 'd';
		
		$tdate =  $_POST['date'];
		$ttime =  $_POST['time'];
		$taddress =  $_POST['address'];
		$ttitle =  $_POST['title'];
		$tcontent =  $_POST['content'];
		$timage =  $_POST['image'];
		
	   
		$result = mysqli_query($con,"SELECT COUNT(*) FROM TravelDetails where Tid = '$tid';");
		$row = mysqli_fetch_array($result);
		$data = $row[0];
		$num = $data+1;
		$temp = $tid.$did.$num;
		$add = $num+1;	
		
		
		$result2 = mysqli_query($con,"SELECT Did FROM TravelDetails where Did = '$temp';");
		
		if( mysqli_num_rows($result2) > 0){
			echo $$tid.$did.$add;
			
			$result = mysqli_query($con," INSERT INTO TravelDetails (Did, Tid, Date, Time, Address, Title, Content, Image) VALUES ('$tid$did$add', '$tid', '$tdate', '$ttime', '$taddress', '$ttitle', '$tcontent', '$timage')");
			
			
			
		}else{
			echo "ok:";
			echo $temp;
			$result = mysqli_query($con," INSERT INTO TravelDetails (Did, Tid, Date, Time, Address, Title, Content, Image) VALUES ('$temp', '$tid', '$tdate', '$ttime', '$taddress', '$ttitle', '$tcontent', '$timage')");
			
		}
		
	
		
		echo "insert success,";

	}
	
?>