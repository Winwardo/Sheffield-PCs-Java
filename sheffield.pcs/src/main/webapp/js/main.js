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
	return JSON.parse("[" + localStorage.getItem("selectedBuildings") + "]");
}

function ICBuildings() {
	var selectedBuildings = [ 14, 15, 16, 17, 18, 19, 20, 21 ];
	// selectedBuildings = [ 95, 96, 97, 98, 99, 100, 101, 102 ];

	localStorage.setItem("selectedBuildings", selectedBuildings);
}

function MappinBuildings() {
	var selectedBuildings = [ 29, 30, 31 ];
	// selectedBuildings = [ 111, 112, 113 ];

	localStorage.setItem("selectedBuildings", selectedBuildings);
}

function WesternBankBuildings() {
	var selectedBuildings = [ 25, 26, 27, 28, 39 ];
	// selectedBuildings = [ 106, 108, 107, 108, 109 ];

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

		loadGraph_();
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