package com.example.hotel.command;

import java.util.HashMap;
import java.util.Map;

import com.example.hotel.constants.PageConstants;
import com.example.hotel.constants.RequestConstants;

public class ToBookingRoomCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        String room = requestContent.getRequestParameter(RequestConstants.ROOM)[0];
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(RequestConstants.ROOM, room);
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.BOOK_ROOM_PAGE, attributes);
    }
}
