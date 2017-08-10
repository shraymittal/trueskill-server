package com.github.shraymittal.trueskillserver.dropwizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import jskills.GameInfo;
import jskills.IPlayer;
import jskills.ITeam;
import jskills.Player;
import jskills.Rating;
import jskills.Team;
import jskills.TrueSkillCalculator;

@Path("/trueskill")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class TrueskillServerResource {

  @GET
  @Path("/game/view")
  public Object viewGames() {
    return TrueskillServer.getData().getGames().keySet();
  }

  @GET
  @Path("/view")
  public Object viewRanks(@QueryParam("game") String game) {
    if(!TrueskillServer.getData().getGames().containsKey(game)) {
      return "No results for that game";
    }
    List<Player<String>> playerList = new ArrayList<>();
    List<Rating> ratingList = new ArrayList<>();
    for(Player<String> p : TrueskillServer.getData().getPlayers().keySet()) {
      if(TrueskillServer.getData().getPlayers().get(p).containsKey(game)) {
        playerList.add(p);
        ratingList.add(TrueskillServer.getData().getPlayers().get(p).get(game));
      }
    }
    Rating temp;
    Player<String> tempP;
    for(int i = 1; i < ratingList.size(); i++) {
      for(int j = i; j > 0; j--) {
        if(ratingList.get(j).getConservativeRating() > ratingList.get(j - 1).getConservativeRating()) {
          temp = ratingList.get(j);
          tempP = playerList.get(j);
          ratingList.set(j, ratingList.get(j - 1));
          playerList.set(j, playerList.get(j - 1));
          ratingList.set(j - 1, temp);
          playerList.set(j - 1, tempP);
        }
      }
    }
    StringBuilder string = new StringBuilder();
    for(int i = 0; i < playerList.size(); i++) {
      string.append(playerList.get(i).getId() + ", " + ratingList.get(i).getConservativeRating() + "\n");
    }
    return string.toString();
  }

  @GET
  @Path("/result")
  public Object makeResult(@QueryParam("result") String result) {
    Scanner scanner = new Scanner(result);
    scanner.useDelimiter(",");
    String game = scanner.next();
    if(!TrueskillServer.getData().getGames().containsKey(game)) {
      TrueskillServer.getData().addGame(game);
    }
    scanner.next();
    String n = scanner.next();
    List<ITeam> teams = new ArrayList<>();
    while (!n.equals("ranks")) {
      Team team = new Team();
      while (!n.equals("t") && !n.equals("ranks")) {
        if (TrueskillServer.getData().getPlayers().containsKey(new Player<String>(n))) {
          if (TrueskillServer.getData().getPlayers().get(new Player<String>(n)).containsKey(game)) {
            team.addPlayer(new Player<String>(n),
                TrueskillServer.getData().getPlayers().get(new Player<String>(n)).get(game));
          } else {
            TrueskillServer.getData().getPlayers().get(new Player<String>(n))
                .put(game, new Rating(25d, 25d / 3d));
            team.addPlayer(new Player<String>(n),
                TrueskillServer.getData().getPlayers().get(new Player<String>(n)).get(game));
          }
        } else {
          TrueskillServer.getData().getPlayers().put(new Player<String>(n), new HashMap<>());
          TrueskillServer.getData().getPlayers().get(new Player<String>(n))
              .put(game, new Rating(25d, 25d / 3d));
          team.addPlayer(new Player<String>(n),
              TrueskillServer.getData().getPlayers().get(new Player<String>(n)).get(game));
        }
        n = scanner.next();
      }
      teams.add(team);
      if(!n.equals("ranks")) {
        n = scanner.next();
      }
    }
    List<Integer> ranks = new ArrayList<>();
    while(scanner.hasNext()) {
      ranks.add(Integer.parseInt(scanner.next()));
    }
    int[] rankArray = new int[ranks.size()];
    for(int i = 0; i < rankArray.length; i++) {
      rankArray[i] = ranks.get(i);
    }
    Map<IPlayer, Rating> ratingMap = TrueSkillCalculator.calculateNewRatings(TrueskillServer.getData().getGames().get(game), teams, rankArray);
    for(IPlayer p : ratingMap.keySet()) {
      TrueskillServer.getData().getPlayers().get(p).put(game, ratingMap.get(p));
    }
    TrueskillServer.writeData();
    return TrueskillServer.getData().getPlayers();
  }

}