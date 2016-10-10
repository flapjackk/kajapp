package org.example.resources;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.example.restaurant.Intenzo;

@Path("/restaurant")
public class Restaurants {
	
	@GET
	@Path("/intenzo")
	@Produces("application/json; charset=UTF-8")
	public Response intenzo(){
		Intenzo intenzo = new Intenzo();
		JsonObject body = intenzo.getMenu();
		return Response.ok(body).build();
	}
}
