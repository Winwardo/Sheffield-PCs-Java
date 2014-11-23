<%@ page
	import="java.util.*, data.*"%>
<ul class="info_blocks">
	<%
	    List<Long> buildingIds = Building.getAllIds();
			for (Long buildingId : buildingIds) {
	    	Building building = Building.get(buildingId);
	    	if (building.name == null) { continue; }
	    	
	    	String labelType = "label label-success";
	    	if (building.current < 5) {
	    	    labelType = "label label-danger";
	    	} else if (building.current < 10) {
	    	    labelType = "label label-warning";
	    	} else if (building.current < 20) {
	    	    labelType = "";
	    	}
	%>
	<li data-buildingid="<%=building.id%>" class="buildingInfo"><img
		src="<%=building.photo%>" alt="<%=building.name%>"
		class="img-circle pull-left"
		style="height: 72px; width: 72px; margin-right: 8px;"> <strong><%=building.name%></strong>&nbsp;

		<span class="<%=labelType%> pull-right"><%=building.current%> free</span><br>

		<span class="hidden-xs"><%=building.current%> of <%=building.maximum%>
			PCs are free.</span>
			
		<span class="visible-xs"><%=building.current%>/<%=building.maximum%></span></li>
	<%
	    }
	%>
</ul>