<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
	
      $image = $_POST['image'];
     
      $result = mysqli_query($con,"SELECT COUNT(*) FROM ImageTable;");
      $row = mysqli_fetch_array($result);
      $data = $row[0];
      $num = $data+1;
      $imageId = "image_";
      $dataId = '10';
      $result = mysqli_query($con,"INSERT INTO ImageTable (Image_id,Image,Data_id) VALUES 
         ('$imageId$num', '$image','$dataId')");
      if($result){
         echo "insert success,";
		 echo $imageId.$num;
		 				 
      }else{
         echo "cannot upload image";
      }
           
      }
   
?>