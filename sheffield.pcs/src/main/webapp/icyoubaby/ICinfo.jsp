<%@ page import="java.util.*, data.students.*"%>
<%
StudentCount count = StudentCount.getFor("ic");
long current_count = count.current_students;



%>
<div style="text-align: center;">
	<h2>
		There are currently<br>
		<strong style="font-size: 4em;" id="students_Diamond"><%=current_count%></strong><br>
		students in the IC.
	</h2>
	<% if (current_count < 500) { %>
	You will not have trouble finding a space to study in.
	<% } else if (current_count < 750) { %>
	You should be able to find a space to study in.
	<% } else if (current_count < 900) { %>
	You might have trouble finding a space to study in.
	<% } else { %>
	Don't expect to easily find a space to study in.
	<% } %>
	<hr>
	This data is provided by CiCS, accurate as of <%=count.date%>.<br>
	Want more useful information about uni spaces? Head over to <a href="http://shefunipcs.info">ShefUniPCs.info</a> for a more detailed breakdown.
</div>