/*
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import mtcg.controller.UserController;
import mtcg.model.User;
import mtcg.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserService userServiceMock;

    @BeforeEach
    void setUp() {
        userServiceMock = mock(UserService.class);
        userController = new UserController(userServiceMock);
    }

    @Test
    void testLoginUser() {
        // Set up test data
        String username = "testUser";
        String password = "testPassword";

        // Create a login request
        Request loginRequest = new Request(Method.POST, "/login", String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password));

        // Mock the service behavior
        when(userController.loginUser(username, password)).thenReturn(user);

        // Call the controller method using the request
        Response response = userController.loginUser(loginRequest);

        // Verify the result
        Assertions.assertEquals(HttpStatus.OK, response.get());
        // Add more assertions based on the expected behavior of your login method
    }

    @Test
    void testRegisterUser() {
        // Set up test data
        String username = "testUser";
        String password = "testPassword";

        // Create a register request
        Request registerRequest = new Request(Method.POST, "/users", String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password));

        // Mock the service behavior
        when(userServiceMock.registerUser(any(User.class))).thenReturn(user);

        // Call the controller method using the request
        Response response = userController.registerUser(registerRequest);

        // Verify the result
        Assertions.assertEquals(HttpStatus.CREATED, response.get());
        // Add more assertions based on the expected behavior of your register method
    }
}
*/