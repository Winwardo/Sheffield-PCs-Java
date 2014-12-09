<%@ page import="java.util.*, data.*"%>

<h5>Tap on a building to get directions from your current location.</h5>

<ul class="info_blocks">
	<%
	    Double latitude = Double.parseDouble( request.getParameter("latitude") );
		Double longitude = Double.parseDouble( request.getParameter("longitude") );
		Integer minimum = Integer.parseInt( request.getParameter("minimum") );
			
	    List<Building> buildings = Building.getClosest(latitude, longitude, minimum);
	    int count = 4;
	    
		for (Building building : buildings) {
		    if (count-- <= 0) { break; }
		    
	    	if (building.name == null) { continue; }
	    	
	    	String labelType = "label label-success";
	    	if (building.current < 5) {
	    	    labelType = "label label-danger";
	    	} else if (building.current < 10) {
	    	    labelType = "label label-warning";
	    	} else if (building.current < 20) {
	    	    labelType = "label label-primary";
	    	}
	%>
	<li data-buildingid="<%=building.id%>" data-latitude="<%=building.latitude%>" data-longitude="<%=building.longitude%>" class="buildingInfo"><img
		id="buildingImg_<%=building.id%>" alt="<%=building.name%>"
		class="img-circle pull-left"
		style="height: 72px; width: 72px; margin-right: 8px;"> <strong><%=building.name%></strong>&nbsp;

		<span class="<%=labelType%> pull-right"><%=building.getCurrent()%>
			free</span><br> <span class="hidden-xs"><%=building.current%> of
			<%=building.maximum%> PCs are free.</span> <span class="visible-xs"><%=building.current%>/<%=building.maximum%></span>
	</li>

	<script>
	$(function() {
		$("#buildingImg_<%=building.id%>").attr("src", "<%=building.photo%>");
	});
	</script>

	<%
	    }
	%>
</ul>

<script>
	$(function(){
		$(".buildingInfo").click(function(e) {
			var $e = $(e.target);
			var buildingId = $e.data("buildingid");
			
			if (buildingId == undefined) {
				$e = $e.parent();
			}
			
			var newIframe = '<iframe height="' + (screen.height * 0.8) + '" frameborder="0" style="border:0; width: 100%" ' +
			'src="https://www.google.com/maps/embed/v1/directions?key=AIzaSyC0Hf436jiPNvDrKX8Dh-81QSW5Hz1fXNU' +
			'&origin=<%=latitude%>,<%=longitude%>' +
			'&destination=' + $e.data("latitude") + ',' + $e.data("longitude") +
			'&mode=walking' +
			'">' +
			'</iframe>'
		
			$("#map-location").html(newIframe);
			$("html, body").animate({ scrollTop: $('#map-location').offset().top }, 1000);
		});		
	});
</script>