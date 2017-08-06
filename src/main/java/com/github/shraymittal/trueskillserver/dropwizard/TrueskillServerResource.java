package com.github.shraymittal.trueskillserver.dropwizard;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/trueskill")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class TrueskillServerResource {

  @GET
  @Path("/test")
  public Object test() {
    TrueskillServer.writeData();
    return "Hello world!";
  }

  @GET
  @Path("/game/add")
  public Object addGame(@QueryParam("name") String name) {
    if (name != null) {
      TrueskillServer.getData().addGame(name);
      TrueskillServer.writeData();
      return TrueskillServer.getData().getGames();
    }
    return "Needs name parameter";
  }

  @GET
  @Path("/game/view")
  public Object viewGames() {
    return TrueskillServer.getData().getGames();
  }

}