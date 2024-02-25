package mtcg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.controller.BattleController;
import mtcg.model.Battle;
import mtcg.model.Card;
import mtcg.model.User;
import mtcg.repository.UserRepository;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class BattleService implements Service {
    private BattleController battleController = new BattleController();
    private UserRepository userRepository= new UserRepository();

    public BattleService() {}

    @Override
    public Response handleRequest(Request request) {
        String route = request.getServiceRoute();

        String auth = request.getAuthorizationHeader();
        System.out.println("route: " + route);
        System.out.println("auth: " + auth);


        if ("/battles".equals(route) && request.getMethod() == Method.POST) {
            // Handle battle logic
            System.out.println("Start a battle: ");
          //  System.out.println("auth: " + auth);
            if (Objects.equals(auth, "Authorization: Bearer kienboec-mtcgToken") ||
                    Objects.equals(auth, "Authorization: Bearer altenhof-mtcgToken")) {

                String username = extractUsernameFromAuthorizationHeader(auth);
                if (username == null) {
                    System.out.println("username is null");
                    return new Response(
                            HttpStatus.UNAUTHORIZED,
                            ContentType.JSON,
                            "{Access token missing or invalid username \n}"
                    );
                }

                //createUser from username
                User user= createUserFromUsername(username);
                List<Card> userdeck= user.getDeck();

                System.out.println("- Player: " + user.getUsername());
                System.out.println("- Players deck: " );
                if(userdeck.isEmpty())
                System.out.println("Deck is Empty");

                for(Card cc: userdeck) {
                    System.out.println("cardId "+ cc.getCardId() + " , "+cc.getUserId() +" , " + cc.getName()+ " ,  "+
                            cc.getDamage() + " , "+ cc.getElement() + " , " + cc.getCardType() + "\n");
                }



                return battleController.handleBattle(request);
            }



        }
        return new Response(HttpStatus.OK, ContentType.JSON, "handle Battle process successful \n");


    }

    private String extractUsernameFromAuthorizationHeader(String authHeader) {
        // Assuming the format is "Authorization: Bearer username-mtcgToken"
        String[] parts = authHeader.split("\\s+");

        if (parts.length == 3 && parts[0].equals("Authorization:") && parts[1].startsWith("Bearer")) {
            String token = parts[2].trim();

            int dashIndex = token.indexOf('-');
            if (dashIndex != -1) {
                return token.substring(0, dashIndex);
            }
        }
        return null;
    }

    private User createUserFromUsername(String username) {
        // look for user in db if exists
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println ("User does not exist!");
        }
        return user;
    }

}

