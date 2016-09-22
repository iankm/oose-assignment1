//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2016.imukher3.dots;

public class Player {

    private String playerType;
    private String playerId;


    public Player(String pType, String pid) {
        this.playerType = pType;
        this.playerId = pid;
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public String getPlayerType() {
        return this.playerType;
    }

    public int hMove(int x, int y, Board board) {
        int boxedNum = board.hMove(this.playerType, x, y);
        return boxedNum;
    }

    public int vMove(int x, int y, Board board) {
        int boxedNum = board.vMove(this.playerType, x, y);
        return boxedNum;
    }

}
