<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>

<link rel="icon" href="img/crest_white.gif" />
<link href="http://bootswatch.com/paper/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/nv.d3.css">
<link rel="stylesheet" type="text/css" href="css/nprogress.css">
<link rel="stylesheet" type="text/css" href="css/stylesheet.css">

<script src="js/jquery-2.1.1.min.js" charset="utf-8"></script>
<script src="js/d3.v3.min.js" charset="utf-8"></script>
<script src="js/nv.d3.min.js" charset="utf-8"></script>
<script src="js/nprogress.js" charset="utf-8"></script>

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
			<div class="col-md-6">
				<h5>Quick select:</h5>
				<button class="btn btn-default" id="btn-IC_pcs">IC PCs</button>
				<button class="btn btn-default" id="btn-Mappin_pcs">Mappin PCs</button>
				<button class="btn btn-default" id="btn-WesternBank_pcs">Western Bank PCs</button>
			</div>
			<div class="col-md-6">
				<h5>Actions:</h5>
				<button class="btn btn-primary" id="btn-refresh">Refresh graph with current selection</button>
			</div>
		</div>
		<hr>
		<div class="row">
			<c:import url="buildingQuickInfo.jsp"></c:import>
		</div>
	</div>
</body>
<script>
	function loadGraph(buildings) {
		NProgress.start();
		$("#pcs_graph").load("graph.jsp", {
			buildings : $.unique(buildings).toString()
		}, function() {
			NProgress.done();
		});
	}

	function loadGraph_() {
		loadGraph(getCurrentBuildings());
	}

	function getCurrentBuildings() {
		return JSON
				.parse("[" + localStorage.getItem("selectedBuildings") + "]");
	}

	function ICBuildings() {
		var selectedBuildings = [ 14, 15, 16, 17, 18, 19, 20, 21 ];
		selectedBuildings = [ 95, 96, 97, 98, 99, 100, 101, 102 ];

		localStorage.setItem("selectedBuildings", selectedBuildings);
	}

	function MappinBuildings() {
		var selectedBuildings = [ 29, 30, 31 ];
		//selectedBuildings = [ 111, 112, 113 ];

		localStorage.setItem("selectedBuildings", selectedBuildings);
	}

	function WesternBankBuildings() {
		var selectedBuildings = [ 25, 26, 27, 28, 39 ];
		//selectedBuildings = [ 106, 108, 107, 108, 109 ];

		localStorage.setItem("selectedBuildings", selectedBuildings);
	}
	
	function clearBuildings() {
		localStorage.removeItem("selectedBuildings");
	}

	function addBuilding(buildingId) {
		var current = getCurrentBuildings();
		current.push(buildingId);
		localStorage.setItem("selectedBuildings", current);
	}

	function testBuilding(buildingId) {
		var current = getCurrentBuildings();
		return ($.inArray(buildingId, current) >= 0);
	}

	function removeBuilding(buildingId) {
		var current = getCurrentBuildings();

		current = jQuery.grep(current, function(value) {
			return value != buildingId;
		});

		localStorage.setItem("selectedBuildings", current);
	}
	
	function updateBuildingSelectionStyles() {
		$(".buildingInfo").each(function(i, e) {
			var $e = $(e);
			var buildingId = $e.data("buildingid");

			if (testBuilding(buildingId)) {
				$e.addClass("selected");
			} else {
				$e.removeClass("selected");
			}
		});
	}
	
	function updateICCounter() {
		$("#currentPcs").load("api/scraper/ic/current");
	}

	// jQuery calls
	$(function() {
		var selectedBuildings = getCurrentBuildings();
		if (selectedBuildings == null || selectedBuildings[0] == null) {
			ICBuildings();
			selectedBuildings = getCurrentBuildings();
		}

		$(".buildingInfo").click(function(e) {
			// Toggle the building being selected or not
			var $e = $(e.target);
			var buildingId = $e.data("buildingid");
			if (buildingId == undefined) {
				$e = $e.parent();
				buildingId = $e.data("buildingid");
			}

			if (testBuilding(buildingId)) {
				removeBuilding(buildingId);
				$e.removeClass("selected");
			} else {
				addBuilding(buildingId);
				$e.addClass("selected");
			}
		});

		// Add the selected class to all current buildings		
		updateBuildingSelectionStyles();

		$("#btn-refresh").click(function() {
			loadGraph_();
		});
		
		$("#btn-IC_pcs").click(function() {
			ICBuildings();
			updateBuildingSelectionStyles();
			loadGraph_();
		});
		
		$("#btn-Mappin_pcs").click(function() {
			MappinBuildings();
			updateBuildingSelectionStyles();
			loadGraph_();
		});

		$("#btn-WesternBank_pcs").click(function() {
			WesternBankBuildings();
			updateBuildingSelectionStyles();
			loadGraph_();
		});
		
		loadGraph_();
	});
</script>

</html>