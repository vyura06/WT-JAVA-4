package com.example.hotel.command;

import com.example.hotel.entity.Room;
import com.example.hotel.exception.ServiceException;
import com.example.hotel.message.MessageHandler;
import com.example.hotel.service.CommonService;

import com.example.hotel.constants.PageConstants;
import com.example.hotel.constants.RequestConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckAvailableRoomsCommand implements Command {
    private CommonService commonService;

    public CheckAvailableRoomsCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult;
        String arrivalDate = requestContent.getRequestParameter(RequestConstants.ARRIVAL)[0];
        String departureDate = requestContent.getRequestParameter(RequestConstants.DEPARTURE)[0];
        try {
            Map<String, Object> requestAttributes = new HashMap<>();
            Map<String, Object> sessionAttributes = new HashMap<>();
            List<Room> roomList = commonService.findAvailableRooms(arrivalDate, departureDate);
            if (roomList.isEmpty()) {
                requestAttributes.put(RequestConstants.NO_AVAILABLE_ROOMS, MessageHandler.getMessage("message.no_available_rooms", (String) requestContent.getSessionAttribute(RequestConstants.LOCALE)));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.USER_MAIN_PAGE, requestAttributes);
            } else {
                requestAttributes.put(RequestConstants.ROOMS, roomList);
                sessionAttributes.put(RequestConstants.ARRIVAL, arrivalDate);
                sessionAttributes.put(RequestConstants.DEPARTURE, departureDate);
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.AVAILABLE_ROOMS, requestAttributes, sessionAttributes);
            }
            return commandResult;
        } catch (ServiceException e) {
            return new DefaultCommand().execute(requestContent);
        }
    }
}
