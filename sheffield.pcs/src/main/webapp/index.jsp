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
<link href="http://bootswatch.com/flatly/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="//brick.a.ssl.fastly.net/Raleway:400">
<link rel="stylesheet" type="text/css" href="css/nv.d3.css">
<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
</head>

<body>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Is there space in the IC?!</a>
			</div>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="http://winwardo.co.uk">Built by Topher Winward</a></li>
			</ul>
		</div>
	</div>

	<div style="height: 60px;"></div>

	<div class="container">
		<h1>Is there space in the IC?!</h1>
		<div class="well">
			<h3>
				Current number of available PCs in the IC <small>Higher
					values mean more spaces!</small>
			</h3>

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