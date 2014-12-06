function loadGraph(buildings) {
	NProgress.start();
	$("#pcs_graph").load("graph_line.jsp", function() {
		NProgress.done();
	});
}

// jQuery calls
$(function() {	
	$("#btn-toggleBuildingInfo").click(function() {
		$("#buildingInfo").slideToggle("500");
	});

	loadGraph();
});