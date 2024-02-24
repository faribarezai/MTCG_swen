import httpserver.server.Server;
import httpserver.utils.Router;
import mtcg.dal.DatabaseManager;
import mtcg.model.Card;
import mtcg.model.User;
import mtcg.service.CardService;
import mtcg.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.SocketHandler;


public class Main {
    public static void main(String[] args) {
        System.out.println("Lets play.... Monster Trading Card Game!");

        //Router router= new Router();

        try {
            // start server
            Server server = new Server();
            server.start();

            UserService userService = new UserService();
            CardService cardService= new CardService();

        } catch (Exception e) {
            // Handle initialization failures
            e.printStackTrace();
        }
    }
}

