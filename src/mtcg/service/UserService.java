package mtcg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.controller.UserController;
import mtcg.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class UserService implements Service {
    private final UserController userController;
    private User user;
    public UserService() {
        this.userController=new UserController();
        this.user=new User();
    }


    @Override
    public Response handleRequest(Request request) {
        String route = request.getServiceRoute();
        String auth= request.getAuthorizationHeader();

        if ("/users".equals(route) && request.getMethod() == Method.POST) {
            return userController.registerUser(request);
        }

        if ("/sessions".equals(route) && request.getMethod() == Method.POST) {
            return userController.loginUser(request);
        }


        //curl -i -X GET http://localhost:10001/users/kienboec --header "Authorization: Bearer kienboec-mtcgToken"
        if ("/users/kienboec".equals(route) && request.getMethod() == Method.GET ||
                "/users/altenhof".equals(route) && request.getMethod() == Method.GET ||
                "/users/someGuy".equals(route) && request.getMethod() == Method.GET ) {

            String username= extractUsernameFromRoute(route);
            System.out.println("Username: " + username);

            //Authorization: Bearer kienboec-mtcgToken
            String expectedAuthFormat= "Authorization: Bearer " + username + "-mtcgToken";

               // if(auth!=null && auth.equals(expectedAuthFormat)){
            if(auth !=null && auth.contains(username)){
            return userController.getUserData(username);
            }

            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Wrong Authentication Token! \n");
        }

        if("/users/kienboec".equals(route) && request.getMethod() == Method.PUT ||
                "/users/altenhof".equals(route) && request.getMethod() == Method.PUT) {
            String username= extractUsernameFromRoute(route);

            //if(auth!=null && auth.equals(expectedAuthFormat)) {
              if(auth !=null && auth.contains(username)){
                return userController.editUserData(username, request);
            }

            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Wrong Authentication Token! \n");
        }

        //curl -X GET http://localhost:10001/stats --header "Authorization: Bearer kienboec-mtcgToken"
        if("/stats".equals(route) && request.getMethod() == Method.GET){
            if(Objects.equals(auth, "Authorization: Bearer kienboec-mtcgToken") ||
                    Objects.equals(auth, "Authorization: Bearer altenhof-mtcgToken")) {
                String username= extractUsernameFromAuthorizationHeader(auth);

                System.out.println("Stats of: " + username);
                return userController.getUserStats(username);

            }
         return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "mismatch Authentication Token! \n");

        }


        //curl -X GET http://localhost:10001/scoreboard --header "Authorization: Bearer kienboec-mtcgToken"
        if("/scoreboard".equals(route) && request.getMethod() == Method.GET){
            if(Objects.equals(auth, "Authorization: Bearer kienboec-mtcgToken")) {
                String username= extractUsernameFromAuthorizationHeader(auth);
                return userController.getScores(username);

            }

            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "mismatch Authentication Token! \n");
        }


        // when all IFs fail
        return new Response(HttpStatus.OK, ContentType.JSON, "handle User process successful \n");
    }



    public String extractUsernameFromRoute(String route) {
        // Assuming route is in the format "/users/{username}"
        String[] parts = route.split("/");

        // Check if the route has the expected format
        if (parts.length == 3 && parts[1].equals("users")) {
            return parts[2];
        } else {
            // Handle the case where the route format is not as expected
            throw new IllegalArgumentException("Invalid route format");
        }
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

}
