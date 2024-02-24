package mtcg.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mtcg.model.User;
import mtcg.repository.UserRepository;
import mtcg.service.UserService;

public class UserController {
    private UserService userService;
    private UserRepository userRepo = new UserRepository();
    private User user;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserController() {
    }


    public UserController(UserService userService) {
        this.userService = userService;
    }
    //Login User
    public Response loginUser(Request request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();
          //  User user;

            try {
                user = objectMapper.readValue(requestBody, User.class);
            } catch (JsonParseException e) {
                // If parsing fails, return a Bad Request response
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid JSON format \n");
            }

            if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Username or password empty \n");
            }

            // Check if the user already exists
            if (userRepo.userLogged(user)) {
                return new Response(HttpStatus.OK, ContentType.JSON, "User logged in successfully \n");
            } else
                return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "User does not exist \n");


        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid request body");
        }

    }


    public Response registerUser(Request request) {

        try {
            // Parse the JSON body from the request
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();

            User user = objectMapper.readValue(requestBody, User.class);

            // Validate the request body
            if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {

                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "username or password wrong \n");
            }
            // Check if the user already exists
            if (userRepo.userExists(user)) {
                return new Response(HttpStatus.CONFLICT, ContentType.JSON, "User with same username already registered \n");
            }

            else {
                // Save user to the database
                userRepo.saveUser(user);
                return new Response(HttpStatus.CREATED, ContentType.JSON, "User successfully created \n");
            }


        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            e.printStackTrace();
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid request body \n");
        }

    }

    public Response editUserData(String username, Request request) {
        User user = userRepo.findByUsername(username);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();

            User updatedUser = objectMapper.readValue(requestBody, User.class);

           // System.out.println("updateduser bio: "+updatedUser.getUsername());

            user.setBio(updatedUser.getBio());
            user.setImage(updatedUser.getImage());
            user.setChangename(updatedUser.getChangename());

            //System.out.println("username: "+ user.getUsername() + ", " + user.getBio()+ ", "+ user.getImage() +", " + user.getChangename());

            //update it in db
            userRepo.updateUserData(user);

            return new Response(HttpStatus.OK, ContentType.JSON, "User Data updated! \n" ); //+objectMapper.writeValueAsString(user) + " \n");

        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            e.printStackTrace();
        }

        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid request body \n");
    }


    public Response getUserData(String username) {

        User user = userRepo.findByUsername(username);
        //System.out.println("username: "+ user.getUsername() + ", " + user.getCoins()+ ", "+ user.getElo() +", " + user.getChangename());

        if (user != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String userJson;

            try {
                userJson = objectMapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

          //  System.out.println("username: "+ user.getUsername() + ", " + user.getCoins()+ ", "+ user.getElo() +", " + user.getChangename());

            return new Response(HttpStatus.OK, ContentType.JSON, userJson + "\n");
        } else {
            return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "User not found\n");
        }
    }

    public Response getUserStats(String username) {
        User user = userRepo.findByUsername(username);

        System.out.println("username: "+ user.getUsername() + ", " + user.getCoins()+ ", "+ user.getElo() +", " + user.getChangename());

        if (user != null) {
           User newUser= new User(user.getUsername(), user.getCoins(), user.getElo());

            ObjectMapper objectMapper = new ObjectMapper();
            String userJson;

            try {
                userJson = objectMapper.writeValueAsString(newUser);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


            System.out.println("username: "+ newUser.getUsername() + ", " + newUser.getCoins()+ ", "+ newUser.getElo() +", " + newUser.getChangename());
                // Return the user data in JSON format
            return new Response(HttpStatus.OK, ContentType.JSON, userJson + "\n");



        }

        return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "User not found\n");
    }
}
