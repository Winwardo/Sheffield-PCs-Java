<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns:fb="https://www.facebook.com/2008/fbml">
<head>
<meta property="og:title" content="Sheffield Uni PCs Info"/>
<meta property="og:description" content="Displays information about available PCs around Sheffield Uni campus"/>
<meta property="og:image" content="http://sheffieldpcs-topherio.rhcloud.com/img/facebook-share.png"/>
<meta property="og:image" content="http://sheffieldpcs-topherio.rhcloud.com/img/crest_white.gif"/>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" href="img/crest_white.gif" />
<link href="//bootswatch.com/paper/bootstrap.min.css"
	rel="stylesheet">
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<!--
<link rel="stylesheet" type="text/css" href="css/nv.d3.css">
<link rel="stylesheet" type="text/css" href="css/nprogress.css">
<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
-->
<link rel="stylesheet" type="text/css" href="css/minified.css">

<!--
<script src="js/jquery-2.1.1.min.js" charset="utf-8"></script>
<script src="js/d3.v3.min.js" charset="utf-8"></script>
<script src="js/nv.d3.min.js" charset="utf-8"></script>
<script src="js/nprogress.js" charset="utf-8"></script>
-->
<script src="js/minified_before.js" charset="utf-8"></script>

<title>Sheffield Uni PCs counter</title>
</head>

<body>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
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

	<div style="height: 80px;"></div>

	<div class="container-fluid" id="ICdata">
		<div class="well">
			
			<div class="row">
				<div class="col-xs-4">
					<c:import url="MappinInfo.jsp"></c:import>
				</div>
				<div class="col-xs-4">
					<c:import url="ICinfo.jsp"></c:import>
				</div>
				<div class="col-xs-4">
					<c:import url="WesternBankInfo.jsp"></c:import>
				</div>
			</div>
			
			<hr>
			<h3 style="text-align: center;">Free PCs over the past 48 hours<br><small>Higher values mean more spaces for you!</small></h3>

			<div id="pcs_graph">
				<br> <br> <img src="img/loading.gif" alt="Loading"
					width="92"><br>
				<h6>Loading...</h6>
			</div>

		</div>

		<div class="row text-center">
			<div class="col-md-12">
				<h5>Quick select:</h5>
				<button class="btn btn-default" id="btn-IC_pcs">IC PCs</button>
				<button class="btn btn-default" id="btn-Mappin_pcs">Mappin PCs</button>
				<button class="btn btn-default" id="btn-WesternBank_pcs">Western Bank PCs</button>
			</div>
			<!--
			<div class="col-md-6">
				<h5>Actions:</h5>
				<button class="btn btn-primary" id="btn-refresh">Refresh graph with current selection</button>
			</div>
			-->
		</div>
		<hr>
		<div class="row text-center">
			<c:import url="buildingQuickInfo.jsp"></c:import>
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
				<li><a href="https://twitter.com/intent/tweet?url=http://ShefUniPCs.info" target="_blank"><i class="fa fa-twitter-square"></i> &nbsp;Tweet about this</a></li>
				<li><a href="api-docs/" target="_blank"><i class="fa fa-database"></i> &nbsp;API</a></li>
				<li><a href="https://github.com/Winwardo/Sheffield-PCs-Java" target="_blank"><i class="fa fa-github"></i> &nbsp;View on GitHub</a></li>
			</ul>
		</div>
	</div>
	
<!--
<script src="js/main.js" charset="utf-8"></script>
-->
<script src="js/minified_after.js" charset="utf-8"></script>

</body>
</html>