package api;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import data.students.StudentCount;

@Path("/student")
@Api(value = "/student", description = "Retrieves student counts in three buildings at Sheffield University.")
public class ApiStudent {
	Gson gson = new Gson();

	@GET
	@Path("/{building}")
	@ApiOperation(value = "/{building}", notes = "Returns current building count.")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBuilding(@PathParam("building") String building) {
		StudentCount count = StudentCount.getFor(building);
		return gson.toJson(count);
	}

	@GET
	@Path("/update/{building}/{password}/{newcount}")
	@ApiOperation(value = "/update/{building}/{password}/{newcount}", notes = "Updates a building's current students. Requires a password.")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateBuilding(@PathParam("building") String building, @PathParam("password") String password,
			@PathParam("newcount") Long newcount) {
		StudentCount toUpdate = new StudentCount(building, newcount, new Date());
		return gson.toJson(toUpdate.update());
	}
}
