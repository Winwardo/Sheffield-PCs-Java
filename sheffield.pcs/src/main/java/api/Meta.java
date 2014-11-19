package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

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
	return "{version: \"0.0.1\"}";
    }
}
