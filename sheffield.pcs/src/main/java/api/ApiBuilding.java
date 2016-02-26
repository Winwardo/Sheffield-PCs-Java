package api;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import data.pcs.Building;

@Path("/building")
@Api(value = "/building", description = "Retrieves information about the various buildings at Sheffield.")
public class ApiBuilding {
    Gson gson = new Gson();

    @GET
    @Path("/all")
    @ApiOperation(value = "/newest", notes = "Gives information about the most recent scrape.")
    @Produces(MediaType.APPLICATION_JSON)
    public String newest() {
	return gson.toJson(Building.getAll());
    }

    @GET
    @Path("/best/{latitude}/{longitude}/{minimum}")
    @ApiOperation(value = "/best/{latitude}/{longitude}/{minimum}", notes = "Displays the best matched building with n free PCs.")
    @Produces(MediaType.APPLICATION_JSON)
    public String newest(@PathParam("latitude") double latitude,
	    @PathParam("longitude") double longitude,
	    @PathParam("minimum") int minimum) {
	return gson.toJson(Building.getClosest(latitude, longitude, minimum));
    }

    @GET
    @Path("/get/nvd3/{buildingId}")
    @ApiOperation(value = "/get/nvd3/{buildingId}", notes = "Gets the last 48 hours' worth of scrapes for this building, suitable for nvd3.")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBuildingNvd3(@PathParam("buildingId") long buildingId) {
	Map<String, Object> result = Building.getRecentForNvd3(buildingId);

	return gson.toJson(result);
    }
}
