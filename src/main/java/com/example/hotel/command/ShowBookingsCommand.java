package com.example.hotel.command;

import com.example.hotel.entity.Booking;
import com.example.hotel.entity.User;
import com.example.hotel.exception.ServiceException;
import com.example.hotel.message.MessageHandler;
import com.example.hotel.service.CommonService;

import com.example.hotel.constants.PageConstants;
import com.example.hotel.constants.RequestConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowBookingsCommand implements Command {
    private CommonService commonService;

    public ShowBookingsCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult;
        User user = (User) requestContent.getSessionAttribute(RequestConstants.USER);
        Map<String, Object> requestAttributes = new HashMap<>();
        try {
            List<Booking> bookingList = commonService.findBookings(user.getLogin());
            if (bookingList.isEmpty()) {
                requestAttributes.put(RequestConstants.ERROR_FINDING_BOOKINGS, MessageHandler.getMessage("message.no_bookings", (String) requestContent.getSessionAttribute(RequestConstants.LOCALE)));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.USER_MAIN_PAGE, requestAttributes);
            } else {
                requestAttributes.put(RequestConstants.BOOKINGS, bookingList);
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.BOOKINGS_PAGE, requestAttributes);
            }
            return commandResult;
        } catch (ServiceException e) {
            return new DefaultCommand().execute(requestContent);
        }
    }
}
