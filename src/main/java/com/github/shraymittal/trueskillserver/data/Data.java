package com.github.shraymittal.trueskillserver.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import jskills.GameInfo;
import jskills.Player;
import jskills.Rating;
import lombok.Getter;

public class Data implements Serializable {

  @Getter
  private Map<String, GameInfo> games;

  @Getter
  private Map<Player<String>, Map<String, Rating>> players;

  public Data() {
    games = new HashMap<>();
    players = new HashMap<>();
  }

  public void addGame(String game) {
    games.put(game, GameInfo.getDefaultGameInfo());
  }

}
