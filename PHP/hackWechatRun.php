<?php
date_default_timezone_set("Asia/Shanghai");
$uid = $_POST['uid'];
$steps = $_POST['steps'];
$pc = $_POST['pc_value'];
$calories = $distance = $duration = $steps;
$date = mktime(0, 0, 0, date("n"), date("j"), date("Y"));
$key = md5("ldl_pro@".$date."#".$steps."$".$calories."^".$distance."&".$duration);
$url = "http://walk.ledongli.cn/rest/dailystats/upload/v3?uid=".$uid;
$post = "pc=".$pc."&stats=[{\"calories\": ".$calories.", \"distance\": ".$distance.", \"duration\": ".$duration.", \"steps\": ".$steps.", \"key\": \"".$key."\", \"date\": ".$date."}]";
$ch = curl_init($url);
curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_POSTFIELDS, $post);
$rsp = curl_exec($ch);
curl_close($ch);
