<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
	
  
    $userId = $_POST['userId'];
    $tid = $_POST['travelId'];
	
	$result = mysqli_query($con,"SELECT COUNT(*) FROM traveldetails WHERE Tid = '$tid'");
	
	$row = mysqli_fetch_array($result);
	$data = $row[0];


		if($data == 0){
			//echo "no";
			$qry = "DELETE FROM Travelogue where Userid = '$userId' and Tid = '$tid' " ;
		}else{
			//echo "yes" ;
			$qry ="DELETE FROM TravelDetails, Travelogue USING TravelDetails INNER JOIN Travelogue ON TravelDetails.Tid = Travelogue.Tid  WHERE Travelogue.Userid = '$userId' and Travelogue.Tid = '$tid'";
		}

	
   $result = mysqli_query($con,$qry);
      if($result!=null){
           echo "1,";
           echo "delete success";
         
      }else{
         echo "0,";
         echo "no delete";
      }     
   }
?>