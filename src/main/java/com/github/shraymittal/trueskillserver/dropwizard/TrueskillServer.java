package com.github.shraymittal.trueskillserver.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class TrueskillServer extends Application<TrueskillServerConfiguration> {

  public static void main(String[] args) throws Exception {
    new TrueskillServer().run(args);
  }

  @Override
  public void run(TrueskillServerConfiguration config, Environment environment) {
    final TrueskillServerResource resource = new TrueskillServerResource();
    environment.jersey().register(resource);
  }

}
