package com.example.hotel.command;

import com.example.hotel.entity.User;
import com.example.hotel.exception.ServiceException;
import com.example.hotel.message.MessageHandler;
import com.example.hotel.service.UserService;
import com.example.hotel.validator.LoginValidator;
import com.example.hotel.validator.PasswordValidator;

import com.example.hotel.constants.PageConstants;
import com.example.hotel.constants.RequestConstants;

import java.util.HashMap;
import java.util.Map;

public class LoginCommand implements Command {

    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        User user;
        CommandResult commandResult;
        String login = requestContent.getRequestParameter(RequestConstants.LOGIN)[0];
        String password = requestContent.getRequestParameter(RequestConstants.PASSWORD)[0];
        Map<String, Object> requestAttributes = new HashMap<>();
        Map<String, Object> users = new HashMap<>();
        LoginValidator loginValidator = new LoginValidator();
        PasswordValidator passwordValidator = new PasswordValidator();
        if (loginValidator.isValidated(login) && passwordValidator.isValidated(password)) {
            try {
                if (userService.login(login, password).isEmpty()) {
                    users.put(RequestConstants.ERROR_LOGIN_PASS, MessageHandler.getMessage("warning.login_password", (String) requestContent.getSessionAttribute(RequestConstants.LOCALE)));
                    commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.LOGIN_PAGE, requestAttributes, users);
                } else {
                    user = userService.login(login, password).get(0);
                    if (!user.isAdmin()) {
                        users.put(RequestConstants.USER, user);
                        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.USER_MAIN_PAGE, requestAttributes, users);
                    } else {
                        user = userService.login(login, password).get(0);
                        users.put(RequestConstants.USER, user);
                        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.ADMIN_MAIN_PAGE, requestAttributes, users);
                    }
                }
            } catch (ServiceException e) {
                return new DefaultCommand().execute(requestContent);
            }
        } else {
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.LOGIN_PAGE);
        }
        return commandResult;
    }
}
