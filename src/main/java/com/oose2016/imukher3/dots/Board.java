//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2016.imukher3.dots;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class Board {

    private Line[][] horizontalLines;
    private Line[][] verticalLines;
    private Box[][] boxes;


    public Board() {

        this.horizontalLines = new Line[5][4];
        this.verticalLines = new Line[4][5];
        this.boxes = new Box[4][4];

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 4; y++) {
                Line line = new Line(x,y);
                this.horizontalLines[x][y] = line;
            }
        }

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 5; y++) {
                Line line = new Line(x,y);
                this.verticalLines[x][y] = line;
            }
        }

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Box b = new Box(x,y);
                this.boxes[x][y] = b;
            }
        }

    }

    public Line[][] getHLines() {
        return this.horizontalLines;
    }

    public Line[][] getVLines() {
        return this.verticalLines;
    }

    public Box[][] getBoxes() {
        return this.boxes;
    }

    public Line getHLine(int x, int y) {
        return this.horizontalLines[x][y];
    }

    public Line getVLine(int x, int y) {
        return this.verticalLines[x][y];
    }

    public Map<String,Object> boardResponse() {
        Map<String,Object> response = new HashMap<String,Object>();
        List<Map<String,Object>> hLineList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> vLineList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> boxList = new ArrayList<Map<String,Object>>();
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 4; y++) {
                hLineList.add(this.horizontalLines[x][y].lineResponse());
            }
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 5; y++) {
                vLineList.add(this.verticalLines[x][y].lineResponse());
            }
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                boxList.add(this.boxes[x][y].boxResponse());
            }
        }
        response.put("horizontalLines", hLineList);
        response.put("verticalLines", vLineList);
        response.put("boxes", boxList);
        return response;
    }

    public int hMove(String playerType, int row, int col) {
        int numBoxed = 0;
        boolean boxed = false;

        if(!this.horizontalLines[row][col].isFilled()) {
            this.horizontalLines[row][col].fill();
            if ( row == 4 ) {
                boxed = this.boxes[row-1][col].setOwner(playerType, this.horizontalLines, this.verticalLines);
                if (boxed) {
                    numBoxed++;
                }
            }
            else if ( row == 0 ) {
                boxed = this.boxes[row][col].setOwner(playerType, this.horizontalLines, this.verticalLines);
                if (boxed) {
                    numBoxed++;
                }

            }
            else {
                boolean boxedRow1 = this.boxes[row-1][col].setOwner(playerType, this.horizontalLines, this.verticalLines);
                if (boxedRow1) {
                    numBoxed++;
                }
                boolean boxedRow2 = this.boxes[row][col].setOwner(playerType, this.horizontalLines, this.verticalLines);
                if (boxedRow2) {
                    numBoxed++;
                }
            }
        }
        else {
            // Throw LineFilledException
        }
        return numBoxed;
    }

    public int vMove(String playerType, int row, int col) {
        int numBoxed = 0;
        boolean boxed = false;
        
        if(!this.verticalLines[row][col].isFilled()) {
            this.verticalLines[row][col].fill();
            if ( col == 4 ) {
                boxed = this.boxes[row][col-1].setOwner(playerType, this.horizontalLines, this.verticalLines);
                if (boxed) {
                    numBoxed++;
                }
            }
            else if ( col == 0 ) {
                boxed = this.boxes[row][col].setOwner(playerType, this.horizontalLines, this.verticalLines);
                if (boxed) {
                    numBoxed++;
                }
            }
            else {
                boolean boxedCol1 = this.boxes[row][col-1].setOwner(playerType, this.horizontalLines, this.verticalLines);
                if (boxedCol1) {
                    numBoxed++;
                }
                boolean boxedCol2 = this.boxes[row][col].setOwner(playerType, this.horizontalLines, this.verticalLines);
                if (boxedCol2) {
                    numBoxed++;
                }
            }
        }
        else {
            // Throw LineFilledException
        }
        return numBoxed;       
    }
}
