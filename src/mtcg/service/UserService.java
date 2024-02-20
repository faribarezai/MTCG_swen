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
        String uEndpoint=  "/users/" +user.getUsername();

        if ("/users".equals(route) && request.getMethod() == Method.POST) {
            return userController.registerUser(request);
        }

        if ("/sessions".equals(route) && request.getMethod() == Method.POST) {
            return userController.loginUser(request);
        }
        //curl -i -X GET http://localhost:10001/users/kienboec --header "Authorization: Bearer kienboec-mtcgToken"
        if (uEndpoint.equals(route) && request.getMethod() == Method.GET) {
            return userController.loginUser(request);
        }



        // Handle other routes and methods as needed
        return new Response(HttpStatus.OK, ContentType.JSON, "handle User process successful");
    }
}
