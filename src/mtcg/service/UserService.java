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
                "/users/altenhof".equals(route) && request.getMethod() == Method.GET) {

            String username= extractUsername(route);
            System.out.println("Username: " + username);
            //Authorization: Bearer kienboec-mtcgToken
            String expectedAuthFormat= "Authorization: Bearer " + username + "-mtcgToken";

            // check if username is in authToken
            //if(auth != null && auth.contains(username)){
                if(auth!=null && auth.equals(expectedAuthFormat)){
            return userController.getUserData(username);

            }
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Wrong Authentication Token! \n");
        }

        if("/users/kienboec".equals(route) && request.getMethod() == Method.PUT ||
                "/users/altenhof".equals(route) && request.getMethod() == Method.PUT) {
            String username= extractUsername(route);
            System.out.println("Username: " + username);

            String expectedAuthFormat= "Authorization: Bearer " + username + "-mtcgToken";
            if(auth!=null && auth.equals(expectedAuthFormat)) {
                return userController.editUserData(username, request);
            }

            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Wrong Authentication Token! \n");
        }

        // when all IFs fail
        return new Response(HttpStatus.OK, ContentType.JSON, "handle User process successful \n");
    }



    public String extractUsername(String route) {
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

}
