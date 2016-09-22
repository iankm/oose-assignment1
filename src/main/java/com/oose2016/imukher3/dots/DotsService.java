//-------------------------------------------------------------------------------------------------------------//
// Ian Mukherjee
// Assignment 1
// OOSE
// Due Wednesday, September 21, 2016
// DotsSerivce.java
//-------------------------------------------------------------------------------------------------------------//


package com.oose2016.imukher3.dots;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class DotsService {

  private final Logger logger = LoggerFactory.getLogger(DotsService.class);
  private HashMap<String,Game> datasource;
  private int gameCount = 0;

  /**
   * Construct the model with a pre-defined datasource. The current implementation
   * also ensures that the DB schema is created if necessary.
   *
   * @param dataSource
   */
  public DotsService() {
    datasource = new HashMap<String,Game>();
  }

  public Map<String,Object> createGame(String playerType) {
    String gameNum = Integer.toString(gameCount);
    gameCount++;
    Game game = new Game(gameNum,playerType);
    logger.error(String.format("Create Game ID: %s", game.getGameId()));
    datasource.put(gameNum,game);
    Map<String,Object> response = new HashMap<String,Object>();
    response.put("gameId", gameNum);
    response.put("playerId", game.getPlayer1().getPlayerId());
    response.put("playerType", playerType);
    return response;
  }

  public Map<String,Object> joinGame(String gameId) throws DotsServiceException {
    
    if(!datasource.containsKey(gameId)) {
      throw new DotsServiceException("404");
    }
    else {
      Game game = datasource.get(gameId);
      if(game.getPlayer2() != null) {
        throw new DotsServiceException("410");
      }
      else {
        logger.error(String.format("Join Game ID: %s", gameId));
        game.joinGame();
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("gameId",gameId);
        response.put("playerId",game.getPlayer2().getPlayerId());
        response.put("playerType",game.getPlayer2().getPlayerType());
        return response;
      }
    }
  }

  public void makeHorizontalMove(String gameId, String playerId, int row, int col) throws DotsServiceException {
    if(!datasource.containsKey(gameId)) {
      throw new DotsServiceException("404");
    }
    else {
      Game game = datasource.get(gameId);
      if(!(playerId.equals("0") || playerId.equals("1"))) {
        throw new DotsServiceException("404");
      }
      else {
        if(game.getWhoseTurn().equals(game.getPlayer(playerId).getPlayerType())) {
          if (row > 4 || col > 3 || row < 0 || col < 0 || game.getBoard().getHLine(row, col).isFilled()) {
            throw new DotsServiceException("422");
          }
          else {
            game.hMove(playerId, row, col);
          }
        }
        else {
          throw new DotsServiceException("422");
        }
      }
    }
  }

  public void makeVerticalMove(String gameId, String playerId, int row, int col) throws DotsServiceException {
    if(!datasource.containsKey(gameId)) {
      throw new DotsServiceException("404");
    }
    else {
      Game game = datasource.get(gameId);
      if(!(playerId.equals("0") || playerId.equals("1"))) {
        throw new DotsServiceException("404");
      }
      else {
        if(game.getWhoseTurn().equals(game.getPlayer(playerId).getPlayerType())) {
          if (row > 3 || col > 4 || row < 0 || col < 0 || game.getBoard().getVLine(row, col).isFilled()) {
            throw new DotsServiceException("422");
          }
          else {
            game.vMove(playerId, row, col);
          }
        }
        else {
          throw new DotsServiceException("422");
        }
      }
    }
  }

  public Map<String,Object> getBoard(String gameId) throws DotsServiceException {
    if(!datasource.containsKey(gameId)) {
      throw new DotsServiceException("404");
    }
    else {
      Game game = datasource.get(gameId);
      //logger.error(String.format("Board Game ID: %s", gameId));
      return game.getBoard().boardResponse();
    }
  }

  public Map<String,Object> getState(String gameId) throws DotsServiceException {
    if(!datasource.containsKey(gameId)) {
      throw new DotsServiceException("404");
    }
    else {
      Game game = datasource.get(gameId);
      Map<String, Object> response = new HashMap<String,Object>();
      boolean finished = game.isFinished();
      if(finished) {
        game.setFinished();
      }
      response.put("redScore", game.getRedScore());
      response.put("blueScore", game.getBlueScore());
      response.put("state", game.getState());
      response.put("whoseTurn", game.getWhoseTurn());
      //logger.error(String.format("whoseTurn: %s", game.getWhoseTurn()));
      return response;
    }
  }

  /*////////////////////////
  Exception Class
  ////////////////////////*/

  public static class DotsServiceException extends Exception {

    private String message = null;

    public DotsServiceException(String message) {
      super(message);
      this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }
  }



}

