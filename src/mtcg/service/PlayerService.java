package mtcg.service;

import httpserver.http.HttpStatus;
import httpserver.http.ContentType;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.dal.UnitOfWork;
import mtcg.model.Player;
import mtcg.repository.PlayerRepository;

import java.sql.Connection;

public class PlayerService implements Service {
    public PlayerService(Connection connection) {
    }
    public PlayerService() {}


    public void updatePlayer(Player player) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            PlayerRepository playerRepository = new PlayerRepository(unitOfWork);
            playerRepository.updatePlayer(player);
            unitOfWork.commitTransaction();
        } catch (Exception e) {
            // Handle exceptions
        }
    }

    @Override
    public Response handleRequest(Request request) {
        String requestBody = request.getBody();
        //String responseData = "Hello, this is the Player Service!";
        return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, requestBody);
    }

}

