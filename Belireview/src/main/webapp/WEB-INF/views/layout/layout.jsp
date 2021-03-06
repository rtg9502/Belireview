<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <title>BeliReview</title>
	    <!-- 파비콘-->
		<link rel="shortcut icon" href="/brw/resources/images/ican/title_logo4.png">
		
	    <!-- fonts -->
	    <link href='http://fonts.googleapis.com/css?family=Raleway:400,300,500,600,700' rel='stylesheet' type='text/css'>
	    <link href='http://fonts.googleapis.com/css?family=Roboto+Slab:400,700,300' rel='stylesheet' type='text/css'>
	    <link href='http://fonts.googleapis.com/css?family=Playfair+Display:400,700' rel='stylesheet' type='text/css'>
	    <link href='/brw/resources/font-awesome/css/font-awesome.css' rel='stylesheet' type='text/css'>
	      
	    <!-- Bootstrap -->
	    <link href="/brw/resources/css/bootstrap.min.css" rel="stylesheet">
	    
	    <!-- main css -->
	    <link href="/brw/resources/style.css" rel="stylesheet">
	    <link href="/brw/resources/responsive.css" rel="stylesheet">
	    
	    <!-- Slider -->
	    <link href="/brw/resources/css/owl.carousel.css" rel="stylesheet">
	    <link href="/brw/resources/css/owl.theme.css" rel="stylesheet">
	    <link href="/brw/resources/css/owl.transitions.css" rel="stylesheet"> 
	      
	    <!-- lightbox -->
	    <link href="/brw/resources/css/prettyPhoto.css" rel="stylesheet">
	    
	    <!-- 메뉴  -->
	    <script language="JavaScript" src="/brw/resources/js/script.js"></script>
	    
	    
	    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	    <script src="/brw/resources/js/library.js"></script>
	    <!-- Include all compiled plugins (below), or include individual files as needed -->
	    <script src="/brw/resources/js/bootstrap.min.js"></script>
	    <script src="/brw/resources/js/owl.carousel.min.js"></script>
	    <script src="/brw/resources/js/jquery.raty.js"></script>
	    <script src="/brw/resources/js/ui.js"></script>
	    <script src="/brw/resources/js/jquery.prettyPhoto.js"></script>
	    <script src="/brw/resources/js/jquery.selectbox-0.2.js"></script>
	    <script src="/brw/resources/js/theme-script.js"></script>
	    
	    <!-- admin -->
	    <script src="/brw/resources/js/admin/post_to_url.js"></script>
	    
	    <!-- jQuery 자동완성 -->
	    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	</head>

	<body>
		<div>
			<tiles:insertAttribute name="header"/>
		</div>
		<div>
			<tiles:insertAttribute name="body"/>
		</div>
		<div>
			<tiles:insertAttribute name="footer"/>
		</div>
	</body>
</html>