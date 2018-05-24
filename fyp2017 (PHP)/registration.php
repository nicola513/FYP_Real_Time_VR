<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "1,";
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
	
   $username =  $_POST['username'];
   $password =  $_POST['password'];
   $email = $_POST['email'];

   $result = mysqli_query($con,"SELECT Email FROM Account where 
   Email='$email'");
   $row = mysqli_fetch_array($result);
   $data = $row[0];

   if(sizeof($row)>0){
      echo "1,";
      echo "The email is exist.";
   }else{   

   $input = explode(" ",$username);
  
  if(sizeof($input)>1){
   $array[] = str_split($input[0])[0];
   
		for($i=1;$i<sizeof($input)&&$i<3;$i++){
			array_push($array,str_split($input[$i])[0]);
		}
   }else{
   $array = str_split($input[0])[0];
   }
   
  if(sizeof($array)>1){
   $userid = implode("",$array);
   }else{
   $userid = $array;
   }

  $isexist = true;

   $result = mysqli_query($con,"SELECT COUNT(*) FROM Account;");
   $row = mysqli_fetch_array($result);
   $data = $row[0];
   $num = $data+1;
   $result = mysqli_query($con,"INSERT INTO Account (Userid,Username,Password,Email) VALUES ('$userid$num', '$username','$password','$email')");
        echo "0,";
	echo "insert success";

  
}
 }
?>