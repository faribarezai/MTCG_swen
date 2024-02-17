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
    public UserService() {
        this.userController=new UserController();
    }

    @Override
    public Response handleRequest(Request request) {
        String route = request.getServiceRoute();

        if ("/users".equals(route) && request.getMethod() == Method.POST) {
            return userController.registerUser(request);
        }

        if ("/sessions".equals(route) && request.getMethod() == Method.POST) {
            return userController.loginUser(request);
        }
        // Handle other routes and methods as needed
        return new Response(HttpStatus.OK, ContentType.JSON, "handle User process successful");
    }
}
