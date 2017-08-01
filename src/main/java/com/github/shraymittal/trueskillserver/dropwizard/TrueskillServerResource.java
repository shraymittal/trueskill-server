package com.github.shraymittal.trueskillserver.dropwizard;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/trueskill")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class TrueskillServerResource {

  @GET
  @Path("/test")
  public Object test() {
    return "Hello world!";
  }

}