package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import data.DataEntry;
import data.DataRetriever;
import data.WebScraper;
import data.jsonIn.OverallData;
import databaseAccess.DatabaseLoginInfo;

@Path("/scraper")
@Api(value = "/scraper", description = "Scrapes data from the Sheffield website")
public class Scraper {
    Gson gson = new Gson();
    String kimonoUrl = "https://www.kimonolabs.com/api/azqh7yb0?apikey=65444f9af606f40552af9d9c90886781";

    @GET
    @Path("/newest")
    @ApiOperation(value = "/newest", notes = "Gives information about the most recent scrape.")
    @Produces(MediaType.APPLICATION_JSON)
    public String newest() {
	DataRetriever retriever = new DataRetriever();

	return gson.toJson(retriever.getNewestScrape());
    }

    @GET
    @Path("/forceScrape/{password}")
    @ApiOperation(value = "/forceScrape/{password}", notes = "Forces a scrape. Requires a password.")
    @Produces(MediaType.APPLICATION_JSON)
    public String forceScrape(@PathParam("password") String password) {

	if (DatabaseLoginInfo.apiPassword.equals(password)) {
	    WebScraper scraper = new WebScraper();
	    String json = scraper.getJson(kimonoUrl);
	    OverallData data = scraper.getData(json);
	    DataEntry dataEntry = new DataEntry();

	    if (dataEntry.enter(data)) {
		return gson.toJson("Scrape completed.");
	    } else {
		return gson.toJson("Scrape failed.");
	    }
	} else {
	    return gson.toJson("Wrong password.");
	}
    }
}
