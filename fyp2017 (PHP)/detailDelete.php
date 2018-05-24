<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
	
  
    $did = $_POST['dID'];


    $qry = "DELETE FROM TravelDetails where Did = '$did' " ;
	
	
   $result = mysqli_query($con,$qry);
      if($result!=null){
           echo "1,";
           echo "Delete Success";
         
      }else{
         echo "0,";
         echo "no image";
      }     
   }
?>