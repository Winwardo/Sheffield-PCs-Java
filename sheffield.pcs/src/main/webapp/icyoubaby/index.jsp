<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns:fb="https://www.facebook.com/2008/fbml">
<head>
<meta charset="UTF-8">
<meta name="author" content="Topher Winward">
<meta name="description" content="Get realtime availability information about Sheffield University computers.">
<meta name="keywords" content="Sheffield,University,available,free,pcs,computers,ic,information commons,western bank">
<meta property="og:title" content="Sheffield Uni PCs Info"/>
<meta property="og:description" content="Displays information about available PCs around Sheffield Uni campus"/>
<meta property="og:image" content="http://sheffieldpcs-topherio.rhcloud.com/img/facebook-share.png"/>
<meta property="og:image" content="http://sheffieldpcs-topherio.rhcloud.com/img/crest_white.gif"/>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" href="img/sheffield.png" />
<link href="//bootswatch.com/paper/bootstrap.min.css"
	rel="stylesheet">
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="css/nv.d3.css">
<link rel="stylesheet" type="text/css" href="css/nprogress.css">
<link rel="stylesheet" type="text/css" href="css/stylesheet.css">

<script src="js/jquery-2.1.1.min.js" charset="utf-8"></script>
<script src="js/d3.v3.min.js" charset="utf-8"></script>
<script src="js/nv.d3.min.js" charset="utf-8"></script>
<script src="js/nprogress.js" charset="utf-8"></script>
<script src="//underscorejs.org/underscore-min.js" charset="utf-8"></script>

<title>IC You Baby</title>

</head>

<body>
	<div class="navbar navbar-inverse hidden-xs" role="navigation">
		<div class="container">
			<ul class="nav navbar-nav">
				<li><a class="navbar-brand" href="#">ShefUniPCs.info/ICYouBaby</a></li>
			</ul>


			<ul class="nav navbar-nav navbar-right hidden-xs">
				<li><a href="http://uk.linkedin.com/in/topherwinward/">Built
						by Topher Winward</a></li>
			</ul>
		</div>
	</div>

	<div class="big-group" id="ICdata">	
		<h2 class="visible-xs text-center light-blue" style="margin: 0px; padding-top: 16px; padding-bottom: 0px; font-size: 3em">ShefUniPCs.info <hr></h2>
		
		<div class="container">			
			<div class="row">
				<div class="col-sm-12 hidden-xs">
					<c:import url="ICinfo.jsp"></c:import>
				</div>
			</div>			
			<br>
		</div>
		
		<hr>
	</div>
		
	<div class="container">
		Created by <a href="http://uk.linkedin.com/in/topherwinward">Topher Winward</a>.<br>
		All data retrieved from <a href="https://www.sheffield.ac.uk/cics/findapc/" target="_blank">The University Of Sheffield's Find a PC</a> page.<br>
		ShefUniPCs.info is not officially associated with <a href="https://www.sheffield.ac.uk/">The University Of Sheffield</a>.
	</div>
	<br>

<script>
	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	ga('create', 'UA-41332914-3', 'auto');
	ga('require', 'displayfeatures');
	ga('send', 'pageview');
</script>

</body>
</html>