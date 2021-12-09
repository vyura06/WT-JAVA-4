package com.example.hotel.command;

import com.example.hotel.entity.User;
import com.example.hotel.exception.ServiceException;
import com.example.hotel.service.UserService;

import com.example.hotel.constants.PageConstants;
import com.example.hotel.constants.RequestConstants;
import com.example.hotel.validator.BirthdayValidator;
import com.example.hotel.validator.EmailValidator;
import com.example.hotel.validator.LoginValidator;
import com.example.hotel.validator.PasswordValidator;

import java.util.HashMap;
import java.util.Map;


public class RegisterCommand implements Command {
    private UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult;
        String login = requestContent.getRequestParameter(RequestConstants.LOGIN)[0];
        String password = requestContent.getRequestParameter(RequestConstants.PASSWORD)[0];
        String email = requestContent.getRequestParameter(RequestConstants.EMAIL)[0];
        String firstName = requestContent.getRequestParameter(RequestConstants.FIRST_NAME)[0];
        String lastName = requestContent.getRequestParameter(RequestConstants.LAST_NAME)[0];
        String phoneNumber = requestContent.getRequestParameter(RequestConstants.PHONE_NUMBER)[0];
        String country = requestContent.getRequestParameter(RequestConstants.COUNTRY)[0];
        String birthday = requestContent.getRequestParameter(RequestConstants.BIRTHDAY)[0];
        User user;

        LoginValidator loginValidator = new LoginValidator();
        PasswordValidator passwordValidator = new PasswordValidator();
        EmailValidator emailValidator = new EmailValidator();
        BirthdayValidator birthdayValidator = new BirthdayValidator();

        if (loginValidator.isValidated(login) && passwordValidator.isValidated(password) && emailValidator.isValidated(email)
                && birthdayValidator.isValidated(birthday)) {
            try {
                user = userService.register(login, password, email, firstName, lastName, phoneNumber, country, birthday, false);
            } catch (ServiceException e) {
                return new DefaultCommand().execute(requestContent);
            }
            Map<String, Object> users = new HashMap<>();
            users.put(RequestConstants.USER, user);
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.LOGIN_PAGE, users);
        } else {
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.LOGIN_PAGE);
        }
        return commandResult;
    }
}
