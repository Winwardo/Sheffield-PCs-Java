package api;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import data.jsonin.OverallData;
import data.pcs.Building;
import data.pcs.DataEntry;
import data.pcs.DataRetriever;
import data.pcs.WebScraper;
import databaseaccess.DatabaseLoginInfo;

@Path("/scraper")
@Api(value = "/scraper", description = "Scrapes data from the Sheffield website")
public class ApiScraper {
	Gson gson = new Gson();
	String kimonoUrl = "https://api.import.io/store/connector/7010127d-4d5c-41a9-b0bb-152717007d21/_query?input=webpage/url:https%3A%2F%2Fwww.sheffield.ac.uk%2Fcics%2Ffindapc%2F&&_apikey=770b7000634b4e3684fa246823e634ce39bb537d7705631dd77d4a916fffe1afd8490dea5941a11daa7a3f1ada9d62e7e0635ed92fe88faab34fc708c859d3afdb3f3c92bd5f55d1b4fbdf15f78cc046";

	@GET
	@Path("/newest")
	@ApiOperation(value = "/newest", notes = "Gives information about the most recent scrape.")
	@Produces(MediaType.APPLICATION_JSON)
	public String newest() {
		DataRetriever retriever = new DataRetriever();

		return gson.toJson(retriever.getNewestScrape());
	}

	@GET
	@Path("/get/nvd3/{buildingId}")
	@ApiOperation(value = "/get/nvd3/{buildingId}", notes = "Gets the last 48 hours' worth of scrapes for this building, suitable for nvd3.")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBuildingNvd3(@PathParam("buildingId") long buildingId) {
		Map<String, Object> result = Building.getRecentForNvd3(buildingId);

		return gson.toJson(result);
	}

	@GET
	@Path("/get/nvd3_many/{buildingIds}")
	@ApiOperation(value = "/get/nvd3_many/{buildingIds}", notes = "Gets the last 48 hours' worth of scrapes for the given building ids (comma delimited), suitable for nvd3.")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBuildingNvd3(@PathParam("buildingIds") String buildingIds) {
		List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();

		String[] ids;

		if (buildingIds == null || "".equals(buildingIds)) {
			ids = new String[] { "14" }; // Default to IC level 1
		} else {
			ids = buildingIds.split(",");
		}
		for (String id : ids) {
			try {
				long buildingId = Long.parseLong(id);
				Map<String, Object> datum = Building.getRecentForNvd3(buildingId);

				// Remove empty data
				if (datum.get("key") != null) {
					result.add(datum);
				}
			} catch (Exception e) {
				// Skip
			}
		}

		return gson.toJson(result);
	}

	@GET
	@Path("/get/raw/{buildingId}")
	@ApiOperation(value = "/get/raw/{buildingId}", notes = "Gets the last 48 hours' worth of scrapes for this building.")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBuilding(@PathParam("buildingId") long buildingId) {
		Building building = Building.getRecent(buildingId);

		return gson.toJson(building);
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
				Building.invalidateCache();
				Building.buildingsInAllMajorPlaces();

				return gson.toJson("Scrape completed.");
			} else {
				return gson.toJson("Scrape failed.");
			}
		} else {
			return gson.toJson("Wrong password.");
		}
	}

	@GET
	@Path("/ic/buildings")
	@ApiOperation(value = "/ic/buildings", notes = "Returns recent information about the IC.")
	@Produces(MediaType.APPLICATION_JSON)
	public String buildingsIC() {
		return gson.toJson(Building.buildingsInTheIC());
	}

	@GET
	@Path("/newest/time")
	@ApiOperation(value = "/newest/time", notes = "Returns the time of the most recent scrape as Unix timestamp.")
	@Produces(MediaType.APPLICATION_JSON)
	public String getNewestTime() {
		WebScraper scraper = new WebScraper();
		return gson.toJson(scraper.mostRecentScrapeTime().getTime());
	}

	@GET
	@Path("/ic/current")
	@ApiOperation(value = "/ic/current", notes = "Returns how many spaces are currently available in the IC.")
	@Produces(MediaType.APPLICATION_JSON)
	public String spacesIC() {
		return gson.toJson(Building.spacesInTheIC());
	}

	@GET
	@Path("/mappin/buildings")
	@ApiOperation(value = "/mappin/buildings", notes = "Returns recent information about Mappin.")
	@Produces(MediaType.APPLICATION_JSON)
	public String buildingsMappin() {
		return gson.toJson(Building.buildingsInMappin());
	}

	@GET
	@Path("/mappin/current")
	@ApiOperation(value = "/mappin/current", notes = "Returns how many spaces are currently available in Mappin.")
	@Produces(MediaType.APPLICATION_JSON)
	public String spacesMappin() {
		return gson.toJson(Building.spacesInMappin());
	}

	@GET
	@Path("/western/buildings")
	@ApiOperation(value = "/western/buildings", notes = "Returns recent information about Western Bank.")
	@Produces(MediaType.APPLICATION_JSON)
	public String buildingsWestern() {
		return gson.toJson(Building.buildingsInWesternBank());
	}

	@GET
	@Path("/western/current")
	@ApiOperation(value = "/western/current", notes = "Returns how many spaces are currently available in Western Bank.")
	@Produces(MediaType.APPLICATION_JSON)
	public String spacesWestern() {
		return gson.toJson(Building.spacesInWesternBank());
	}
}
