package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import data.WebScraper;
import data.jsonIn.OverallData;

@Path("/meta")
@Api(value = "/meta", description = "General info about the system, user")
public class Meta {
    @GET
    @Path("/helloworld")
    @ApiOperation(value = "/helloworld", notes = "Returns `Hello world.`")
    @Produces(MediaType.APPLICATION_JSON)
    public String helloworld() {
	return "{data: \"Hello world.\"}";
    }

    @GET
    @Path("/info")
    @ApiOperation(value = "/info", notes = "Returns info about Sheffield PCs.")
    @Produces(MediaType.APPLICATION_JSON)
    public String info() {
	return "{version: \"0.0.2\"}";
    }

    @GET
    @Path("/jsontest")
    @ApiOperation(value = "/jsontest", notes = "JSON test.")
    @Produces(MediaType.APPLICATION_JSON)
    public String jsontest() {
	WebScraper scraper = new WebScraper();
	String json = scraper
		.getJson("https://www.kimonolabs.com/api/azqh7yb0?apikey=65444f9af606f40552af9d9c90886781");
	OverallData data = scraper.getData(json);

	return scraper.getGson().toJson(data);
    }
}
