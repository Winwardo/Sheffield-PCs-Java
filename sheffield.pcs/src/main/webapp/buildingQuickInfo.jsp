<%@ page
	import="java.io.*, java.util.*, java.io.IOException, java.io.PrintWriter, java.sql.Connection,
 java.sql.ResultSet, java.sql.SQLException, java.sql.*, java.util.Arrays, java.util.ArrayList,
javax.servlet.ServletException, javax.servlet.http.HttpServlet,javax.servlet.http.HttpServletRequest,
javax.servlet.http.HttpServletResponse, javax.servlet.http.HttpSession, java.text.SimpleDateFormat, data.*" %>
<ul class="info_blocks">
	<%
	    List<Long> buildingIds = Building.getAllIds();
	for (Long buildingId : buildingIds) {
	    Building building = Building.getCurrent(buildingId);
	%>
	<li data-buildingid="<%=building.id%>" class="success"><%=building.name%> has <%=building.current%> spaces.</li>
	<%
	}  
	%>
</ul>