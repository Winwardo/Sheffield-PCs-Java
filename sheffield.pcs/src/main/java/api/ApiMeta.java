package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.config.ConfigFactory;

@Path("/meta")
@Api(value = "/meta", description = "General info about the system, user")
public class ApiMeta {
    static {
	ConfigFactory.config().setBasePath("http://www.shefunipcs.info/api/");
    }

    @GET
    @Path("/info")
    @ApiOperation(value = "/info", notes = "Returns info about Sheffield PCs.")
    @Produces(MediaType.APPLICATION_JSON)
    public String info() {
	return "{version: \"1.0.0\"}";
    }

    @GET
    @Path("/docs/web")
    @ApiOperation(value = "/docs/web", notes = "Sets the Swagger basepath to http://www.shefunipcs.info/api")
    @Produces(MediaType.APPLICATION_JSON)
    public String swaggerWeb() {
	ConfigFactory.config().setBasePath("http://www.shefunipcs.info/api");
	final String result = ConfigFactory.config().getBasePath();

	return new Gson().toJson(result);
    }
}
