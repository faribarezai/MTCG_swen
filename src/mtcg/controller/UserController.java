package mtcg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mtcg.model.User;
import mtcg.repository.UserRepository;

public class UserController {
   // private UserService userService;
    private UserRepository userRepo=new UserRepository();
    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserController() {
    }

    //Login User
    public Response loginUser(Request request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();
            User user = objectMapper.readValue(requestBody, User.class);

            if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid request body for login");
            }

            // Check if the user already exists

            if (userRepo.userLogged(user)){
                return new Response(HttpStatus.OK, ContentType.JSON, "User logged in successfully");
            }else
                return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "User does not exist");


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
            System.out.println("Request body in User: " + requestBody);

            User user = objectMapper.readValue(requestBody, User.class);

            System.out.println("Received user registration request: " + user.getUsername() + ", " + user.getPassword() + ", " + user.getCoins() + ", " + user.getElo());

            // Validate the request body
            if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
               // System.out.println("Check if user registration request is valid in registerUSer(): " + user.getUsername() + ", " + user.getPassword());

                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "username or password wrong");
            }
            // Check if the user already exists
            if (userRepo.userExists(user)) {
                //System.out.println(user.getUsername() + " User with same username already registered");
                return new Response(HttpStatus.CONFLICT, ContentType.JSON, "User with same username already registered");
            }
             else {
                // Save user to the database
                userRepo.saveUser(user);
                return new Response(HttpStatus.CREATED, ContentType.JSON, "User successfully created");
            }


        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            e.printStackTrace();
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid request body");
        }

    }
/*
    //User exists for registration?
    public boolean userExists(User user) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM mUser WHERE username = ?")) {
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();


           // int pwscount = resultSet.getInt(2);
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count >0;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return false;
    }


    //check User exists with name +psw for login
    public boolean userExist(User user) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM mUser WHERE username = ? AND password= ?")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count >0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //saved User
    public void saveUser(User user) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO mUser (username, password, coins, elo) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getCoins());
            preparedStatement.setInt(4, user.getElo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 */
}
