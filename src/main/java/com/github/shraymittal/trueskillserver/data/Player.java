package com.github.shraymittal.trueskillserver.data;

import com.github.shraymittal.trueskillserver.calc.Rating;
import com.github.shraymittal.trueskillserver.dropwizard.TrueskillServer;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class Player implements Serializable {

  @Getter
  private String name;

  private Map<String, Rating> ratingMap;

  public Player(String name) {
    this.name = name;
    ratingMap = new HashMap<>();
    for(String game : TrueskillServer.getData().getGames()) {
      ratingMap.put(game, new Rating());
    }
  }

  public Rating getRating(String game) {
    return ratingMap.get(game);
  }

}
