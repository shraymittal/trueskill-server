package com.github.shraymittal.trueskillserver.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;

public class Data implements Serializable {

  @Getter
  private Set<String> games;

  @Getter
  private Set<Player> players;

  public Data() {
    games = new HashSet<>();
    players = new HashSet<>();
  }

  public void addGame(String game) {
    games.add(game);
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

}
