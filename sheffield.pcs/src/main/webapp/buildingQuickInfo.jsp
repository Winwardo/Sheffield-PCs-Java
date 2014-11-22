<%@ page
	import="java.io.*, java.util.*, java.io.IOException, java.io.PrintWriter, java.sql.Connection,
 java.sql.ResultSet, java.sql.SQLException, java.sql.*, java.util.Arrays, java.util.ArrayList,
javax.servlet.ServletException, javax.servlet.http.HttpServlet,javax.servlet.http.HttpServletRequest,
javax.servlet.http.HttpServletResponse, javax.servlet.http.HttpSession, java.text.SimpleDateFormat, data.*"%>
<ul class="info_blocks">
	<%
	    List<Long> buildingIds = Building.getAllIds();
			for (Long buildingId : buildingIds) {
	    	Building building = Building.get(buildingId);
	    	String labelType = "label label-success";
	    	if (building.current < 5) {
	    	    labelType = "label label-danger";
	    	} else if (building.current < 10) {
	    	    labelType = "label label-warning";
	    	} else if (building.current < 20) {
	    	    labelType = "";
	    	}
	%>
	<li data-buildingid="<%=building.id%>" class="success"><img
		src="<%=building.photo%>" alt="<%=building.name%>"
		class="img-circle pull-left"
		style="width: 64px; height: 64px; margin-right: 8px;"> <strong><%=building.name%></strong>&nbsp;

		<span class="<%=labelType%> pull-right"><%=building.current%> free</span><br>

		<span class="hidden-xs"> <%=building.current%> of <%=building.maximum%>
			PCs are free.
	</span> <span class="visible-xs"> <%=building.current%>/<%=building.maximum%></span></li>
	<%
	    }
	%>
</ul>