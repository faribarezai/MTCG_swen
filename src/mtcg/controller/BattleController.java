package mtcg.controller;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;

public class BattleController {

    public Response handleBattle(Request request) {


        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Battle failed \n");
    }
}
