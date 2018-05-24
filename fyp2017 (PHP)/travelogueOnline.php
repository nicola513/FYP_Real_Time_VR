<head>
	<style type="text/css">

		
		hr {
		background-color: #f0f0f5;
		}
		table{
			table-layout:fixed;
			position:relative;
			box-shadow: 0 1px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.2);
			border: 3px solid #f1f1f1;
			border-radius: 8px;
			padding: 16px ;
			margin-bottom: 20px ;				
			margin:35px auto;
			height:350px;
			width:550px;
			font-size: 18px;
		}


		u{
			font-size: 20px;
		}


		
		
		td{
			overflow: auto;
			border-collapse: collapse;
			padding-left: 10px;
			font-size: 14px;
		}


		#id_header {
				height: 30px;
				box-sizing: border-box;
				position: absolute;
				top: 0;
				width: 100%;
				background-color: #ccccff;
				text-align-last: right;
				padding-top: 2px;
				padding-right: 24px;
				text-align: right;
		}

		#id_footer {
		    height: 40px;
		    box-sizing: border-box;
		    position: relative;;
		    bottom: 0;
		    width: 100%;
				background-color: #ccccff;
				padding: 10px;
		}

		.title{
		  padding-left: 10px;
		  padding-top: 5px;
		  float: left;
		}
		
		.background {
			background-image:url('post.jpg');
			background-size: 100% 100%;
		}
		
		.Travelbackground{
			background-image:url('cover.jpg');
			background-size: 100% 100%;
		}
		
		.image{
			width:50%;

			align: centre;
			padding: 20px;

		}
		
		.id{
			width:8%;
			padding-left:15px;			
			font-weight: bold;
		}
		.num{

			width:15%;
			font-size:26px;
			font-weight: bold;
		}
		.firstline{
			height: 86px;
		
		}
		
		.name{
			width:18px;
			text-align:right;
			padding-right:10px;
			font-size: 14px;
			font-weight: bold;
		}
		.imgrow{
			height: 20px;
		}
		
		.content{
			vertical-align: top;
			height: 50px;
		}
		.titleT{
			vertical-align: bottom;
			text-align:right;
height:50px;
		}
		.tidT{
			vertical-align: top;
			text-align:right;
                        
		}

                .mainT{
                        height:50px;
                        
		}
		.cover{
			padding:10px;
		}
	</style>

</head>
<body>
	<div id="id_header">
		<div class="title"> e-Tourism &nbsp;&nbsp;|&nbsp;&nbsp; Travelogue
		</div>
		
	</div>


	
	
	<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
  
    $tid = $_GET['tid'];
     
    $sql = "SELECT Tid, Title, Image FROM Travelogue WHERE Tid = '$tid'";
	
	
	    $result = mysqli_query($con,$sql);
      if($result!=null){
         while ($row = mysqli_fetch_array($result)) {
			echo '
			
			
			
		<table class = "Travelbackground" align="centre" >
		<col width="35%" />
		<col width="65%" />
<tr>
<th   class="mainT"  vertical-align="bottom" colspan="2"><u><h1 align="center"  >My Travelogue</h1></u></th></tr>
		<tr>
			<th class = "titleT"><h2>'.$row['Title'].'</h2></th>
			<th  rowspan="2" >
			
			<img height=180 width=250 src="data:image/jpeg;base64,'.$row['Image'].'"/>
			
			</th>
		</tr>
	
	
		<tr>
			<th class = "tidT"><h4>( '.$row['Tid'].' )</h4></th>
		</tr>
	</table>
			

			
			';

			
			

         }
         
      }else{
         echo "no data";
      }     
   }
  
?>
	<hr/>
	<?php
	$con = mysqli_connect("localhost","root","root","fyp");

	if (mysqli_connect_errno($con)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
  
    $tid = $_GET['tid'];
     
    $sql = "SELECT Did,Date,Time,Address,Title,Content,Image FROM TravelDetails  WHERE Tid = '$tid'";
	
	
		$num = '1';
		
		
	    $result = mysqli_query($con,$sql);
      if($result!=null){
         while ($row = mysqli_fetch_array($result)) {
		 
			echo ' 
					
			
	<table class = "background" align="centre" >
		<col width="25%" />
		<col width="25%" />
			<tr class="firstline">
				<td class ="num"></td>
				<th colspan="3">
				<u>'.$row['Title'].'</u></th>
				<th class ="num">'.$num.'</th>
			</tr>
		

			<tr  class="imgrow">
				<td colspan="2" rowspan ="5" class="image">
				
				<img height=180 width=250 src="data:image/jpeg;base64,'.$row['Image'].'"/>
				</td>
				
				<td colspan="3">				
					
				</td>
				
			</tr>

			<tr style="height: 10px;">
				<td class = "name">				
					Date: 
				</td>
				<td colspan="2" class = "con">				
					'.$row['Date'].'
				</td>
				
			</tr>
			<tr style="height: 10px;">
				<td class = "name">				
					Time: 
				</td>
				<td colspan="2" class = "con">				
					'.$row['Time'].'
				</td>
				
			</tr>
			<tr style="height: 10px;">
				<td class = "name">				
					Address:  
				</td>
				<td colspan="2" class = "con">				
					'.$row['Address'].'
				</td>
				
			</tr>
			
			<tr class = "content">
				<td class = "name" rowspan="2">				
					Content:  
				</td>
				<td colspan="2" rowspan="2" class = "con">			
					 '.$row['Content'].'
				</td>
				
			</tr>
			<tr>

				<td class = "id"colspan="2">				
					'.$row['Did'].'
				</td>
				
			</tr>
		</table>
			

			
			';
			
			$num++;
			

         }
         
      }else{
         echo "no data";
      }     
   }
  
?>

		

	<footer id="id_footer">
		COMPS456F - Final Year Project &nbsp;
			Group Member: &nbsp;
			NG Tsz Ching Nicola (s11535522)&nbsp;
			KWOK Pui Hei (s11512858)&nbsp;
			LIU Ka Kin (s11516857)&nbsp;

	</footer>
</body>