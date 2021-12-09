package com.example.hotel.command;

import com.example.hotel.exception.ServiceException;
import com.example.hotel.message.MessageHandler;
import com.example.hotel.service.CommonService;

import com.example.hotel.constants.PageConstants;
import com.example.hotel.constants.RequestConstants;

import java.util.HashMap;
import java.util.Map;

public class CancelBookingCommand implements Command {
    private CommonService commonService;

    public CancelBookingCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult;
        int bookingId = Integer.parseInt(requestContent.getRequestParameter(RequestConstants.BOOKING_ID)[0]);
        System.out.println(bookingId);
        try {
            commonService.cancelBooking(bookingId);
        } catch (ServiceException e) {
            return new DefaultCommand().execute(requestContent);
        }

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(RequestConstants.SUCCESSFUL_BOOKING, MessageHandler.getMessage("message.successful_canceling", (String) requestContent.getSessionAttribute(RequestConstants.LOCALE)));
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.BOOKINGS_PAGE, attributes);
        return commandResult;
    }
}
