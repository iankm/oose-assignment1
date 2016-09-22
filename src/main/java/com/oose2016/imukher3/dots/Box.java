//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2016.imukher3.dots;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Box {

    private int row;
    private int col;
    private String owner;
    private final Logger logger = LoggerFactory.getLogger(Box.class);


    public Box(int r, int c) {
        this.row = r;
        this.col = c;
        this.owner = "NONE";
    }
    
    public boolean setOwner(String playerType, Line[][] hlines, Line[][] vlines) {
        /*logger.error("Attempting setOwner");
        logger.error(String.format("TopHoriz: (row,col,filled) (%s,%s,%s)", this.row,this.col,hlines[this.row][this.col].isFilled()));
        logger.error(String.format("BottomHoriz: (row,col,filled) (%s,%s,%s)", this.row+1,this.col,hlines[this.row+1][this.col].isFilled()));
        logger.error(String.format("TopVert: hacked: (row,col,filled) (%s,%s,%s)", this.row,this.col,vlines[this.row][this.col].isFilled()));
        logger.error(String.format("BottomVert: hacked: (row,col,filled) (%s,%s,%s)", this.row,this.col+1,vlines[this.row][this.col+1].isFilled()));*/
        if((hlines[this.row][this.col].isFilled())
            && (hlines[this.row+1][this.col].isFilled())
            && (vlines[this.row][this.col].isFilled())
            && (vlines[this.row][this.col+1].isFilled())) {

            this.owner = playerType;
            // logger.error(String.format("setOwner, hacked: owner %s", this.owner));
            return true;

        }
        return false;
    }

    public boolean isOwned() {
        return (!this.owner.equals("NONE"));
    }

    public String getOwner() {
        return this.owner;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public Map<String,Object> boxResponse() {
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("row", this.row);
        response.put("col", this.col);
        response.put("owner", this.owner);
        return response;
    }

}
