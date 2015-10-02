function updateData(buildings) {	
	var time = new Date();
	$("#last-updated").html(time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds());
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
	window.setInterval(function(){ location.reload() }, 600000); // Every 10 minutes
});