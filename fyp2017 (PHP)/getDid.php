<?php

		$ddetail = $_POST['ddetail'];
	    //$ddetail = "{address=Hong Kong,date=2017-05-12,title=HONG KONG TRIP,content=KIKI,time=21:39:19,did=JKA11d2}";


		list($address, $date, $title, $content, $time, $did) = explode(",", $ddetail);


		list($name, $id) = explode("=", $did);

		
		list($get, $no) = explode("}", $id);
		echo $get;

	
?>