<html>
	<head>
    	<title>iCloud principal and calendar settings</title>
        <style type="text/css">
			table {
				border-collapse:collapse;
				border:1px solid;
				border-color:#999;
			}
			table td {
				padding:10px 20px;
			}
			
			#top_bar {
				overflow:hidden;
				width:100%;
				height:270px;
			}
			
			#result {
				border:1px solid #CCC;
				overflow:auto;
				width:99%;
				top:270px;
				bottom:26px;
				position:absolute;
			}
			
			#copy {
				overflow:hidden;
				width:99%;
				bottom:0px;
				position:absolute;
				height:25px;
				margin-left:5px;
			}
			#copy * {
				font-size:12px;
				color:#333;
			}
			#copy div {
				padding-top:3px;
			}
			
			a, #copy a {
				color:#960;
				text-decoration:none;
			}
			
			a:hover, a:focus, #copy a:hover, #copy a:focus {
				text-decoration:underline;
				color:#300;
			}
		</style>
    </head>
    <body onLoad="document.getElementById('appleID').focus();">
<?php
	//Define iCloud URLs
	$icloudUrls = array();
	for($i = 1; $i < 25; $i++)
		$icloudUrls[] = "https://p".str_pad($i, 2, '0', STR_PAD_LEFT)."-caldav.icloud.com";
	
	//Functions
	function doRequest($user, $pw, $url, $xml)
	{
		//Init cURL
		$c=curl_init($url);
		//Set headers
		curl_setopt($c, CURLOPT_HTTPHEADER, array("Depth: 1", "Content-Type: text/xml; charset='UTF-8'", "User-Agent: DAVKit/4.0.1 (730); CalendarStore/4.0.1 (973); iCal/4.0.1 (1374); Mac OS X/10.6.2 (10C540)"));
		curl_setopt($c, CURLOPT_HEADER, 0);
		curl_setopt($c, CURLOPT_USERAGENT, 'iCloud CalDAV urls in PHP');
		//Set SSL
		curl_setopt($c, CURLOPT_SSL_VERIFYHOST, 0);
		curl_setopt($c, CURLOPT_SSL_VERIFYPEER, 0);
		//Set HTTP Auth
		curl_setopt($c, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
		curl_setopt($c, CURLOPT_USERPWD, $user.":".$pw);
		//Set request and XML
		curl_setopt($c, CURLOPT_CUSTOMREQUEST, "PROPFIND");
		curl_setopt($c, CURLOPT_POSTFIELDS, $xml);
		curl_setopt($c, CURLOPT_RETURNTRANSFER, 1);
		//Execute
		$data=curl_exec($c);
		//Close cURL
		curl_close($c);
		
		return $data;
	}
?>
		<div id="top_bar">
            <h1 style='color:darkred;'>Provide your iCloud login settings:</h1>
            <form action="" method="post">
                <table border='0'>
                    <tr>
                        <td><b style='color:blue;'>Apple ID: </b></td>
                        <td><input type='text' name='appleID' id='appleID' size='50' value='<? echo isset($_POST['appleID']) ? $_POST['appleID'] : "";?>'></td>
                    </tr>
                    <tr>
                        <td><b style='color:blue;'>Password: </b></td>
                        <td><input type='password' name='pw' id='pw' size='50' value='<? echo isset($_POST['appleID']) ? $_POST['pw'] : "";?>'></td>
                    </tr>
                    <tr>
                    	<td><b style='color:blue;'>iCloud server: </b></td>
                        <td><select name='server' id='server'>
<?php
	$set = false;
	foreach($icloudUrls as $server) {
		echo "<option value='".$server."'";
		if(isset($_POST['server']) AND $_POST['server'] == $server)
			echo " selcted";
		else if(!isset($_POST['server']) AND !$set) {
			echo " selected";
			$set = true;
		}
		echo ">".$server."</option>";
	}
?>
							</select>
						</td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><input type='submit' value='Evaluate'></td>
                    </tr>
                </table>
            </form><br>
        </div>
        <div id="result">
<?php
	if(isset($_POST['appleID']))
	{
		$user=$_POST['appleID'];
		$pw=$_POST['pw'];
		
		//Get Principal URL
		$principal_request="<A:propfind xmlns:A='DAV:'>
								<A:prop>
									<A:current-user-principal/>
								</A:prop>
							</A:propfind>";
		$response=simplexml_load_string(doRequest($user, $pw, $_POST['server'], $principal_request));
		//Principal URL
		$principal_url=$response->response[0]->propstat[0]->prop[0]->{'current-user-principal'}->href;
		$userID=explode("/", $principal_url);
		$userID=$userID[1];
		
		//Get Calendars
		$calendars_request="<A:propfind xmlns:A='DAV:'>
								<A:prop>
									<A:displayname/>
								</A:prop>
							</A:propfind>";
		$url=$_POST['server']."/".$userID."/calendars/";
		$response=simplexml_load_string(doRequest($user, $pw, $url, $calendars_request));
		//To array
		$calendars=array();
		foreach($response->response as $cal)
		{
			$entry["href"]=$cal->href;
			$entry["name"]=$cal->propstat[0]->prop[0]->displayname;
			$calendars[]=$entry;
		}

		//CardDAV URL
		$cardserver = str_replace('caldav', 'contacts', $_POST['server']);
		$cardurl = "/".$userID."/carddavhome/card/";
		
		//Output
		echo "<h1 style='color:darkred;'>Your principal settings</h1>";
		echo "<table border='1' style='border-collapse:collapse;'>
				<tr>
					<td><b style='color:blue;'>iCloud CalDAV server:</b></td>
					<td style='color:darkgreen;'>".$_POST['server']."</td>
				</tr>
				<tr>
					<td><b style='color:blue;'>User-ID: </b></td>
					<td style='color:darkgreen;'>".$userID."</td>
				</tr>
				<tr>
					<td><b style='color:blue;'>Principal-URL: </b></td>
					<td style='color:darkgreen;'>".$principal_url."</td>
				</tr>
				<tr>
					<td><b style='color:blue;'>Contacts-URL: </b></td>
					<td style='color:darkgreen;'>".$cardurl."</td>
				</tr>
			</table><br>";
		echo "<h1 style='color:darkred;'>Your calendars</h1>";
		echo "<table border='1' style='border-collapse:collapse;'>
				<tr>
					<td align='center'><b style='color:blue;'>Calendar</td>
					<td align='center'><b style='color:blue;'>Calendar href/td>
					<td align='center'><b style='color:blue;'>URL</td>
				</tr>";
		foreach($calendars as $calendar)
			echo "<tr>
					<td style='color:darkgreen;'>".$calendar["name"]."</td>
					<td>".$calendar["href"]."</td>
					<td>".$_POST['server'].$calendar["href"]."</td>
				</tr>";
		echo "</table><br>";
	}
	else
	{
?>
		<br>
        &nbsp;&nbsp;&nbsp;<i>Please provide your login credentials first.</i><br><br>
        &nbsp;&nbsp;&nbsp;<b><u>Note:</u></b> if you are reaching this site publicly and are not the publisher, please send the corresponding URL to <a href="http://www.niftyside.com/cms/contact/" target="_blank">NiftySide</a>! <i>Thank you!</i>
<?php
	}
?>
		</div>
		<div id="copy">
        	<div>Version v1.3 ; Script copyright &copy; 2011-2013 by <a href='http://www.niftyside.com' target="_blank">NiftySide - Daniel M&uuml;hlbachler</a>
            &nbsp;&nbsp;&nbsp;;&nbsp;&nbsp;&nbsp;
            <a href="http://www.icloud.com" target="_blank">iCloud</a> is a service provided by <a href="http://www.apple.com" target="_blank">Apple Inc.</a></div>
        </div>
	</body>
</html>

<!-- Copyright (C) 2011-2013 by NiftySide - Daniel Muehlbachler (http://www.niftyside.com) -->
<!-- You are not allowed to remove the copyright notices anywhere in this document! -->
<!-- Please read the dedicated README file for further information on the usage and copyright! -->
