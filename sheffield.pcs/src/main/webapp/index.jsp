<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<script src="js/d3.v3.min.js" charset="utf-8"></script>
<script src="js/nv.d3.min.js" charset="utf-8"></script>
<script src="js/jquery-2.1.1.min.js" charset="utf-8"></script>

<link rel="icon" href="img/crest_white.gif" />
<link href="http://bootswatch.com/paper/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/nv.d3.css">
<link rel="stylesheet" type="text/css" href="css/stylesheet.css">

<title>Sheffield Uni PCs counter</title>
</head>

<body>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<ul class="nav navbar-nav">
				<li><a class="navbar-brand" href="#">ShefUniPCs.info</a></li>
				<li><a href="#ICdata">IC data</a></li>
				<li><a href="api-docs/">API</a></li>
				<li><a href="https://github.com/Winwardo/Sheffield-PCs-Java">Source code</a></li>
			</ul>


			<ul class="nav navbar-nav navbar-right">
				<li><a href="http://uk.linkedin.com/in/topherwinward/">Built by Topher Winward</a></li>
			</ul>
		</div>
	</div>

	<div style="height: 80px;"></div>

	<div class="container" id="ICdata">
		<div class="well">
			<h2 style="margin-top: 2px;">
				Current number of available PCs in the IC <br><small>Higher
					values mean more spaces!</small>
			</h2>

			<c:import url="graph.jsp">
				<c:param name="buildings">14,15,16,18,19,21</c:param>
			</c:import>

		</div>

		<div class="row">
			<div class="col-md-8" id="infosection">
				<c:import url="buildingQuickInfo.jsp"></c:import>
			</div>
			<div class="col-md-4 hidden-sm" id="moreinfo">
				<div id="pusher"></div>
				{{> moreinfo}}
			</div>
		</div>
	</div>
</body>
</html>