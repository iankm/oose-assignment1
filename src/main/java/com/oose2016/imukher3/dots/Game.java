//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2016.imukher3.dots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {

    private String gameId;
    private Player player1;
    private Player player2;
    private String state;
    private String whoseTurn;
    private Board board;
    private int playerOneScore;
    private int playerTwoScore;
    private static final Logger logger = LoggerFactory.getLogger(Game.class);


    public Game(String id, String playerType) {
        this.gameId = id;
        this.player1 = new Player(playerType,"0");
        this.state = "WAITING_TO_START";
        this.board = new Board();
        this.whoseTurn = "RED";
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
    }

    public void joinGame() {
        if(this.player1.getPlayerType().equals("RED")) {
            this.player2 = new Player("BLUE","1");
        }
        else {
            this.player2 = new Player("RED","1");
        }
        state = "IN_PROGRESS";
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer(String playerId) {
        if(playerId.equals("0")) {
            return this.player1;
        }
        else {
            return this.player2;
        }
    }

    public int getScore1() {
        return this.playerOneScore;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public int getScore2() {
        return this.playerTwoScore;
    }
    
    public String getGameId() {
        return this.gameId;
    }

    public String getState() {
        return this.state;
    }

    public Board getBoard() {
        return this.board;
    }

    public String getWhoseTurn() {
        return this.whoseTurn;
    }

    public void setFinished() {
        this.state = "FINISHED";
        this.whoseTurn =  "FINISHED";
    }

    public int getRedScore() {
        if(this.player1.getPlayerType().equals("RED")) {
            return this.playerOneScore;
        }
        else {
            return this.playerTwoScore;
        }
    }

    public int getBlueScore() {
        if(this.player1.getPlayerType().equals("BLUE")) {
            return this.playerOneScore;
        }
        else {
            return this.playerTwoScore;
        }
    }

    public boolean isFinished() {
        Box[][] b = this.board.getBoxes();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (!b[x][y].isOwned()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void hMove(String playerId, int row, int col) {
        int boxedNum = 0;
        if (playerId.equals("0")) {
            if(this.player1.getPlayerType().equals(this.whoseTurn)) {
                boxedNum = this.player1.hMove(row,col,this.board);
                this.playerOneScore += boxedNum;

            }
            else {
                logger.error("Player 1 tried to h on player 2's turn");
                // throw WrongPlayerException
            }
        }
        else {
            if(this.player2.getPlayerType().equals(this.whoseTurn)) {
                boxedNum = this.player2.hMove(row,col,this.board);
                this.playerTwoScore += boxedNum;
            }
            else {
                logger.error("Player 2 tried to h on player 1's turn");   
            }
        }
        if (boxedNum > 0) {
            this.whoseTurn = this.whoseTurn;
        }
        else {
            if(this.whoseTurn == "RED") {
                this.whoseTurn = "BLUE";
                logger.error(String.format("whoseTurn: %s", this.whoseTurn));
            }
            else {
                this.whoseTurn = "RED";
                logger.error(String.format("whoseTurn: %s", this.whoseTurn));
            }
        }
    }

    public void vMove(String playerId, int row, int col) {
        int boxedNum = 0;
        if (playerId.equals("0")) {
            if(this.player1.getPlayerType().equals(this.whoseTurn)) {
                boxedNum = this.player1.vMove(row,col,this.board);
                this.playerOneScore += boxedNum;
            }
            else {
                logger.error("Player 1 tried to v on player 2's turn");
                // throw WrongPlayerException
            }
        }
        else {
            if(this.player2.getPlayerType().equals(this.whoseTurn)) {
                boxedNum = this.player2.vMove(row,col,this.board);
                this.playerTwoScore += boxedNum;
            }
            else {
                logger.error("Player 2 tried to v on player 1's turn");
                // throw WrongPlayerException
            }
        }
        if (boxedNum > 0) {
            this.whoseTurn = this.whoseTurn;

        }
        else {
            if(this.whoseTurn == "RED") {
                this.whoseTurn = "BLUE";
                logger.error(String.format("whoseTurn: %s", this.whoseTurn));
            }
            else {
                this.whoseTurn = "RED";
                logger.error(String.format("whoseTurn: %s", this.whoseTurn));
            }
        }
    }
}
