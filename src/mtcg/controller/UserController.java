package mtcg.controller;

import mtcg.model.User;
import mtcg.service.UserService;

public class UserController{
    private final UserService userService;

    public UserController(UserService userService, UserService userService1) {
        this.userService = userService;
    }

    public void handleUserUpdate(User user) {
        userService.updateUser(user);
    }
}
