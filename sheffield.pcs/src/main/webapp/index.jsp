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

<title>Sheffield Uni PCs counter</title>

</head>

<body>
	<div class="navbar navbar-inverse hidden-xs" role="navigation">
		<div class="container">
			<ul class="nav navbar-nav">
				<li><a class="navbar-brand" href="#">ShefUniPCs.info</a></li>
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
				<div class="col-sm-4 hidden-xs">
					<c:import url="DiamondInfo.jsp"></c:import>
				</div>
				<div class="col-xs-6 col-sm-4">
					<c:import url="ICinfo.jsp"></c:import>
				</div>
				<div class="col-xs-6 col-sm-4">
					<c:import url="WesternBankInfo.jsp"></c:import>
				</div>
			</div>
			
			<br>
		</div>
		
		<div style="background-color: #f4f4f4; padding-bottom: 16px; margin-bottom: 16px" class="container-fluida">	
			<div class="container">	
				<h3 class="text-center">
					<span class="hidden-xs">Free PCs over the past 24 hours<br></span>
					<small class="hidden-xs">Higher values mean more spaces for you!</small>
					
					<span class="visible-xs">Recent free PCs<br></span>
					<small class="visible-xs">Higher values are better</small>
				</h3>
				
				<div id="pcs_graph">
					<c:import url="graph_line.jsp"></c:import>
				</div>
				
				<small>Last updated: <span id="last-updated"></span></small>
			</div>
		</div>
		
		<div class="container">
			<div class="col-md-12">
				<h4 class="text-center">Tips on getting a PC</h4>
				<ul>
					<li>Arrive before 9am, or after 7pm for best luck.</li>
					<li>On weekdays, people tend to leave 5-10 minutes before the hour to get to lectures.</li>
					<li>Rooms are busiest at 3pm.</li>
					<li>Saturdays are least busy, Mondays are busiest.</li>
				</ul>
			</div>
		</div>
		
		<hr>
		
		<div class="row text-center">
			<button class="btn btn-success btn-lg" id="btn-findAPc">Find me a close PC!</button>
			<br class="visible-xs"><br class="visible-xs">
			<button class="btn btn-primary" id="btn-showAllPcs">Show me all PCs!</button>
			<br><br>
			<div id="buildingInfo" style="display: none;">
				<img src="img/loading.gif" alt="Loading" width="92">
			</div>
		</div>
		
		<div id="map-location">
			
		</div>
		
		<hr>
	</div>
		
	<div class="container">
		Created by <a href="http://uk.linkedin.com/in/topherwinward">Topher Winward</a>.<br>
		All data retrieved from <a href="https://www.sheffield.ac.uk/cics/findapc/" target="_blank">The University Of Sheffield's Find a PC</a> page.<br>
		ShefUniPCs.info is not associated with <a href="https://www.sheffield.ac.uk/">The University Of Sheffield</a>.
	</div>
	<br>
	
	<div class="navbar navbar-inverse navbar-bottom" style="margin-bottom: 0;">
		<div class="container">
			<ul class="nav navbar-nav">
				<li><a href="http://www.facebook.com/plugins/like.php?href=http%3A%2F%2Fshefunipcs.info&width=72&layout=button_count&action=like&show_faces=true&share=true&height=21&appId=1503292819886184" target="_blank"><i class="fa fa-facebook-square"></i> &nbsp;Share on Facebook</a></li>
				<li><a href="https://twitter.com/intent/tweet?url=http://ShefUniPCs.info" target="_blank"><i class="fa fa-twitter"></i> &nbsp;Tweet about this</a></li>
				<li><a href="mailto:crwinward1@sheffield.ac.uk?subject:Sheffield%20PCs%20app%20feedback"><i class="fa fa-envelope"></i> &nbsp;Email me</a></li>
				<li><a href="api-docs/" target="_blank"><i class="fa fa-database"></i> &nbsp;API for nerds</a></li>
				<li><a href="https://github.com/Winwardo/Sheffield-PCs-Java" target="_blank"><i class="fa fa-github"></i> &nbsp;View on GitHub</a></li>
			</ul>
		</div>
	</div>
	
<script src="js/main.js" charset="utf-8"></script>
<script asrc="js/minified_after.js" charset="utf-8"></script>

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