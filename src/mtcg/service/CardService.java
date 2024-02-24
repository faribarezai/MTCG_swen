package mtcg.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.controller.CardController;
import mtcg.model.Card;
import mtcg.model.User;
import mtcg.repository.CardRepository;
import mtcg.repository.UserRepository;

import java.sql.Connection;
import java.util.Objects;

import static mtcg.service.PackageService.userRepository;

public class CardService implements Service {

    private CardRepository cardRepository;
    static UserRepository userRepository =  new UserRepository();
    private CardController cardController= new CardController();

    public CardService(CardController cardController) {
        this.cardController = cardController;
    }

    public CardService() {}
    public CardService(Connection con){}


    @Override
    public Response handleRequest(Request request) {
    String route = request.getServiceRoute();
    String auth= request.getAuthorizationHeader();
        System.out.println(auth);
        System.out.println("route: " + route);

    //curl -i -X GET http://localhost:10001/cards --header "Authorization: Bearer kienboec-mtcgToken"
        if ("/cards".equals(route) && request.getMethod() == Method.GET) {
            if (Objects.equals(auth, "Authorization: Bearer admin-mtcgToken") ||
                    Objects.equals(auth, "Authorization: Bearer kienboec-mtcgToken") ||
                    Objects.equals(auth, "Authorization: Bearer altenhof-mtcgToken")) {
                return displayCard(auth, request);
                //return cardController.showAllCards(request);
            }
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No Authentication Token! \n");
        }


        if ("/deck".equals(route) && request.getMethod() == Method.GET ) {
            //curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"

            if(Objects.equals(auth, "Authorization: Bearer kienboec-mtcgToken") ||
                    Objects.equals(auth, "Authorization: Bearer altenhof-mtcgToken")) {
                String username = extractUsernameFromAuthorizationHeader(auth);
                System.out.println("User extracted: " + username);

                User user = createUserFromUsername(username);
                System.out.println("User found for deck: " + user.getUsername());

                try {
                    return cardController.showDeck(user, request);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Authorization failed! \n");

       }


      //  curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken"
       if("/deck".equals(route) && request.getMethod() == Method.PUT) {
           if(Objects.equals(auth, "Authorization: Bearer kienboec-mtcgToken") ||
                   Objects.equals(auth, "Authorization: Bearer altenhof-mtcgToken")) {
               String username = extractUsernameFromAuthorizationHeader(auth); // call other method
               User user = createUserFromUsername(username); // call other method
               try {
                   return cardController.createDeck(user, request);
               } catch (JsonProcessingException e) {
                   throw new RuntimeException(e);
               }
           }
           return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Authorization failed! \n");
       }


       if("/deck?format=plain".equals(route) && request.getMethod() == Method.GET) {
           if(Objects.equals(auth, "Authorization: Bearer kienboec-mtcgToken") ||
                   Objects.equals(auth, "Authorization: Bearer altenhof-mtcgToken")) {
               String username = extractUsernameFromAuthorizationHeader(auth);
               User user = createUserFromUsername(username);
               return cardController.showDeckDiff(user, request);
           }
           return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Authorization failed! \n");
       }


        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Method or Route is incorrect \n");


}

    private Response displayCard(String auth,Request request) {
        String username = extractUsernameFromAuthorizationHeader(auth);
        //System.out.println(" i am in displayCard ");
        if (username != null) {
            User user = createUserFromUsername(username);

            if (user != null) {
               // System.out.println(" i am in displayCardhandle ");
                //show cards when user exists
                return cardController.showAllCards(user, request);
            } else {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "User null \n");
            }
        }
        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Username not extracted \n");
    }

    private String extractUsernameFromAuthorizationHeader(String authHeader) {
        // Assuming the format is "Authorization: Bearer username-mtcgToken"
        String[] parts = authHeader.split("\\s+");

        if (parts.length == 3 && parts[0].equals("Authorization:") && parts[1].startsWith("Bearer")) {
            String token = parts[2].trim();

            // Assuming the username is everything before the first '-'
            int dashIndex = token.indexOf('-');
            if (dashIndex != -1) {
                //   System.out.println("name extracted from authheader: " + token.substring(0, dashIndex));
                return token.substring(0, dashIndex);
            }
        }
        return null;
    }

    private User createUserFromUsername(String username) {
        // Assuming you have an empty constructor in the User class
        // UserRepository userRepository =  new UserRepository();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println ("User does not exist!");
        }
        return user;
    }


}
