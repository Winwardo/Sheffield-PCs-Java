package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import data.DataRetriever;

@Path("/scraper")
@Api(value = "/scraper", description = "Scrapes data from the Sheffield website")
public class Scraper {
    Gson gson = new Gson();

    @GET
    @Path("/newest")
    @ApiOperation(value = "/newest", notes = "Gives information about the most recent scrape.")
    @Produces(MediaType.APPLICATION_JSON)
    public String newest() {
	DataRetriever retriever = new DataRetriever();

	return gson.toJson(retriever.getNewestScrape());
    }

}
