package controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import mtcg.controller.UserController;
import mtcg.dal.UnitOfWork;
import mtcg.model.User;
import mtcg.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    List<User> allUsers= new ArrayList<>();
    private UserController userController;
   // private UnitOfWork unitOfWork= new UnitOfWork();
    @Mock
    private UserRepository userRepository;
    @Mock
    private ObjectMapper objectMapper;
    private List<User> alluser= new ArrayList<>();

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.initMocks(this);
        userController = new UserController(userRepository);
    }


    @Test
    void testRegisterUsernameEmpty() throws JsonProcessingException {
        String username = "";
        String password = "newPassword";
        int coins=20;
        int elo= 100;

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);
        mockUser.setCoins(coins);
        mockUser.setElo(elo);
        allUsers.add(mockUser);

        Request registerRequest = new Request();
        registerRequest.setMethod(Method.POST);
        registerRequest.setPathname("/users");

        // Set the Content-Type header to JSON
        registerRequest.getHeaderMap().setHeader("Content-Type", "application/json");

        // Convert the request to JSON
        String jsonPayload = String.format("{\"username\":\"%s\", \"password\":\"%s\", \"coins\":20, \"elo\":80}", username, password, coins, elo);
        registerRequest.setBody(jsonPayload);

        // Mock the behavior of ObjectMapper and UserRepository
        when(objectMapper.readValue(jsonPayload, User.class)).thenReturn(mockUser);
        when(userRepository.userExists(mockUser)).thenReturn(true);

        // Call userController
        Response response = userController.registerUser(registerRequest);

        assertEquals("Bad Request", response.getMessage());
        assertTrue(response.getContent().contains("username or password wrong"));

    }

    @Test
    void testRegistrationSuccess() throws JsonProcessingException {
        String username = "newTester";
        String password = "newPassword";
        int coins=20;
        int elo= 100;

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);
        mockUser.setCoins(coins);
        mockUser.setElo(elo);

        allUsers.add(mockUser);

        Request registerRequest = new Request();
        registerRequest.setMethod(Method.POST);
        registerRequest.setPathname("/users");

        registerRequest.getHeaderMap().setHeader("Content-Type", "application/json");

        String jsonPayload = String.format("{\"username\":\"%s\", \"password\":\"%s\", \"coins\":20, \"elo\":80}", username, password, coins, elo);
        registerRequest.setBody(jsonPayload);

        ObjectMapper objectMapperMock = mock(ObjectMapper.class);

        when(objectMapperMock.readValue(anyString(), eq(User.class)))
                .thenThrow(new JsonParseException(null, "Intentional parsing failure"));

        Response response = userController.registerUser(registerRequest);

        assertEquals("CREATED", response.getMessage());
        assertTrue(response.getContent().contains("User successfully created"));

    }

    @Test
    void testLoginUserDoesNotExist() throws JsonProcessingException {
        String username = "testUser";
        String password = "testPassword";

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        Request loginRequest = new Request();
        loginRequest.setMethod(Method.POST);
        loginRequest.setPathname("/sessions");


        loginRequest.getHeaderMap().setHeader("Content-Type", "application/json");

        String jsonPayload = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);
        loginRequest.setBody(jsonPayload);

        when(objectMapper.readValue(jsonPayload, User.class)).thenReturn(mockUser);
        when(userRepository.userLogged(mockUser)).thenReturn(true);


        Response response = userController.loginUser(loginRequest);

        assertEquals("Not Found", response.getMessage());
        assertTrue(response.getContent().contains("User does not exist \n"));
    }


    @Test
    void testLoginUserNameEmpty() throws JsonProcessingException {
        String username = "";
        String password = "newPassword";

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        allUsers.add(mockUser);

        Request loginRequest = new Request();
        loginRequest.setMethod(Method.POST);
        loginRequest.setPathname("/sessions");

        loginRequest.getHeaderMap().setHeader("Content-Type", "application/json");

        String jsonPayload = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);
        loginRequest.setBody(jsonPayload);

        when(objectMapper.readValue(jsonPayload, User.class)).thenReturn(mockUser);
        when(userRepository.userLogged(mockUser)).thenReturn(true);

        Response response = userController.loginUser(loginRequest);

        assertEquals("Bad Request", response.getMessage());
        assertTrue(response.getContent().contains("Username or password empty \n"));

    }


    @Test
    void testLoginParsingFailed() throws JsonProcessingException {
        String username = "oldUser";
        String password = "oldPassword";

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);
        allUsers.add(mockUser);

        Request loginRequest = new Request();
        loginRequest.setMethod(Method.POST);
        loginRequest.setPathname("/sessions");

        // Set the Content-Type header to JSON
        loginRequest.getHeaderMap().setHeader("Content-Type", "application/json");

        // Convert the request payload to JSON
        String jsonPayload = String.format("[{\"username\":\"%s\", \"password\":\"%s\"}]", username, password);
        loginRequest.setBody(jsonPayload);

        ObjectMapper objectMapperMock = mock(ObjectMapper.class);

        // Set up the behavior to throw an exception when readValue is called
        when(objectMapperMock.readValue(anyString(), eq(User.class)))
                .thenThrow(new JsonParseException(null, "Intentional parsing failure"));
        when(userRepository.userLogged(mockUser)).thenReturn(true);


        Response response = userController.loginUser(loginRequest);

        assertEquals("Bad Request", response.getMessage());
        assertTrue(response.getContent().contains("Invalid request body"));

    }



}
