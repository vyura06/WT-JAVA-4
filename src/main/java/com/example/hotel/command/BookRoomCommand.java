package com.example.hotel.command;

import com.example.hotel.entity.Room;
import com.example.hotel.entity.User;
import com.example.hotel.exception.ServiceException;
import com.example.hotel.message.MessageHandler;
import com.example.hotel.service.UserService;

import com.example.hotel.constants.PageConstants;
import com.example.hotel.constants.RequestConstants;

import java.util.HashMap;
import java.util.Map;

public class BookRoomCommand implements Command {
    private UserService userService;

    public BookRoomCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult;
        User user = (User) requestContent.getSessionAttribute(RequestConstants.USER);
        String login = user.getLogin();
        String arrival = requestContent.getRequestParameter(RequestConstants.ARRIVAL)[0];
        String departure = requestContent.getRequestParameter(RequestConstants.DEPARTURE)[0];
        Room room = new Room();
        room.setNumber(Integer.parseInt(requestContent.getRequestParameter(RequestConstants.ROOM)[0]));
        int guestsNumber = Integer.parseInt(requestContent.getRequestParameter(RequestConstants.NUMBER_OF_GUESTS)[0]);
        String guestsNames = requestContent.getRequestParameter(RequestConstants.GUESTS_NAMES)[0];

        try {
            userService.addBooking(login, arrival, departure, room, guestsNumber, guestsNames);
        } catch (ServiceException e) {
            return new DefaultCommand().execute(requestContent);
        }
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(RequestConstants.SUCCESSFUL_BOOKING, MessageHandler.getMessage("message.successful_book", (String) requestContent.getSessionAttribute(RequestConstants.LOCALE)));
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.USER_MAIN_PAGE, attributes);
        return commandResult;
    }
}
