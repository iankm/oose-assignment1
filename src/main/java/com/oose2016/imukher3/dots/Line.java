//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2016.imukher3.dots;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class Line {

    private int row;
    private int col;
    private boolean filled;

    public Line(int r, int c) {
        this.row = r;
        this.col = c;
        this.filled = false;
    }
    
    public int getRow() {
        return this.row;
    }
    
    public int getCol() {
        return this.col;
    }

    public boolean isFilled() {
        return this.filled;
    }

    public void fill() {
        this.filled = true;
    }

    public Map<String,Object> lineResponse() {
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("row", this.row);
        response.put("col", this.col);
        response.put("filled", this.filled);
        return response;
    }

}
