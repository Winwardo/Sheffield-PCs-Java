function updateData(buildings) {
	NProgress.start();
	$("#spaces_Mappin").load("api/scraper/mappin/current");
	$("#spaces_IC").load("api/scraper/ic/current");
	$("#spaces_Western").load("api/scraper/western/current");
	
	var time = new Date();
	$("#last-updated").html(time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds());
	
	$("#pcs_graph").load("graph_line.jsp", function() {
		NProgress.done();
	});
}

// jQuery calls
$(function() {	
	$("#btn-toggleBuildingInfo").click(function() {
		$("#buildingInfo").slideToggle("500");
	});

	updateData();
	window.setInterval(updateData, 300000); // Every 5 minutes
});