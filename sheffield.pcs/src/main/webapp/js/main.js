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
	$("#btn-showAllPcs").click(function() {
		NProgress.start();
		$("#buildingInfo").load("buildingQuickInfo.jsp", function() {
			$("#buildingInfo").slideDown("500");
			NProgress.done();
		});
	});

	$("#btn-findAPc").click(function() {
		NProgress.start();
		
		var handle_error = function(error) {
			alert("Can't find you a PC without your location!");
			NProgress.done();			
		}
		
		navigator.geolocation.getCurrentPosition(function(position, handle_error) {				
			$("#buildingInfo").load("buildingClosest.jsp?latitude=" + position.coords.latitude + "&longitude=" + position.coords.longitude + "&minimum=10", function() {
				$("#buildingInfo").slideDown("500");
				NProgress.done();
			});			
		});		
	});

	updateData();
	window.setInterval(updateData, 300000); // Every 5 minutes
});