//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2016.imukher3.dots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import com.google.gson.Gson;

import static spark.Spark.*;

public class DotsController {

    private static final String API_CONTEXT = "/dots/api";

    private final DotsService dotsService;

    private final Logger logger = LoggerFactory.getLogger(DotsController.class);

    public DotsController(DotsService ds) {
        this.dotsService = ds;
        setupEndpoints();
    }

    private void setupEndpoints() {

        post(API_CONTEXT + "/games", "application/json", (request, response) -> {
            Map result = new Gson().fromJson(request.body(), Map.class);
            String playerType = (String) result.get("playerType");
            if (playerType != null
                    && (playerType.equals("RED")
                        || (playerType.equals("BLUE")))) {
                response.status(201);
                // Create new game and return required data
                return dotsService.createGame(playerType);
            }
            response.status(400);
            return "";
        }, new JsonTransformer());

        put(API_CONTEXT + "/games/:gameId", "application/json", (request, response) -> {
            try {
                response.status(200);
                return this.dotsService.joinGame(request.params(":gameId"));
            }
            catch (DotsService.DotsServiceException dse) {
                if(dse.getMessage().equals("404")) {
                    response.status(404);
                }
                else {
                    response.status(410);
                }
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        post(API_CONTEXT + "/games/:gameId/hmove", "application/json", (request, response)-> {
            try {
                response.status(200);
                //logger.error("The gang\'s all here");
                Map result = new Gson().fromJson(request.body(), Map.class);
                String playerId = (String) result.get("playerId");
                int row = Integer.valueOf((String) result.get("row"));
                // logger.error(String.format("Row : %s", row));
                int col = Integer.valueOf((String) result.get("col"));
                // logger.error(String.format("Col : %s", col));
                dotsService.makeHorizontalMove(request.params(":gameId"),playerId,row,col);
                return Collections.EMPTY_MAP;
            }
            catch (DotsService.DotsServiceException dse) {
                if(dse.getMessage().equals("404")) {
                    response.status(404);
                }
                else {
                    response.status(422);
                }
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        post(API_CONTEXT + "/games/:gameId/vmove", "application/json", (request, response) -> {
            try {
                response.status(200);
                //logger.error("The gang\'s all here");
                Map result = new Gson().fromJson(request.body(), Map.class);
                String playerId = (String) result.get("playerId");
                int row = Integer.valueOf((String) result.get("row"));
                // logger.error(String.format("Row : %s", row));
                int col = Integer.valueOf((String) result.get("col"));
                // logger.error(String.format("Col : %s", col));
                dotsService.makeVerticalMove(request.params(":gameId"),playerId,row,col);
                return Collections.EMPTY_MAP;
            }
            catch (DotsService.DotsServiceException dse) {
                if(dse.getMessage().equals("404")) {
                    response.status(404);
                }
                else {
                    response.status(422);
                }
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        get(API_CONTEXT + "/games/:gameId/board", "application/json", (request, response)-> {
            response.status(200);
            return dotsService.getBoard(request.params(":gameId"));
        }, new JsonTransformer());

        get(API_CONTEXT + "/games/:gameId/state", "application/json", (request, response)-> {
            response.status(200);
            return dotsService.getState(request.params(":gameId"));
        }, new JsonTransformer());

    }
}
