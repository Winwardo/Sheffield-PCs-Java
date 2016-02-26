<%@ page import="java.util.*, data.students.*"%>
<div style="text-align: center;">
	<h2>
		There are currently<br>
		<strong style="font-size: 4em;" id="students_Diamond"><%=StudentCount.getFor("ic").current_students%></strong><br>
		students in the IC.
	</h2>
</div>