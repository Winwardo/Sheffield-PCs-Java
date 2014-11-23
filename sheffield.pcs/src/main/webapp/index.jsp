<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>

<link rel="icon" href="img/crest_white.gif" />
<link href="//bootswatch.com/paper/bootstrap.min.css"
	rel="stylesheet">
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
				<li><a href="#ICdata">IC data</a></li>
				<li><a href="api-docs/">API</a></li>
				<li><a href="https://github.com/Winwardo/Sheffield-PCs-Java">Source
						code</a></li>
			</ul>


			<ul class="nav navbar-nav navbar-right">
				<li><a href="http://uk.linkedin.com/in/topherwinward/">Built
						by Topher Winward</a></li>
			</ul>
		</div>
	</div>

	<div style="height: 80px;"></div>

	<div class="container" id="ICdata">
		<div class="well">
			
			<div class="row">
				<div class="col-sm-4">
					<c:import url="MappinInfo.jsp"></c:import>
				</div>
				<div class="col-sm-4">
					<c:import url="ICinfo.jsp"></c:import>
				</div>
				<div class="col-sm-4">
					<c:import url="WesternBankInfo.jsp"></c:import>
				</div>
			</div>
			
			<hr>

			<div id="pcs_graph">
				<br> <br> <img src="img/loading.gif" alt="Loading"
					width="92px"><br>
				<h6>Loading...</h6>
			</div>

		</div>

		<div class="row" style="text-align: center;">
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
		<div class="row">
			<c:import url="buildingQuickInfo.jsp"></c:import>
		</div>
	</div>
</body>

<!--
<script src="js/main.js" charset="utf-8"></script>
-->
<script src="js/minified_after.js" charset="utf-8"></script>

</html>