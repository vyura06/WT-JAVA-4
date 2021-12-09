package com.example.hotel.command;

import com.example.hotel.entity.Booking;
import com.example.hotel.exception.ServiceException;
import com.example.hotel.message.MessageHandler;
import com.example.hotel.service.CommonService;

import com.example.hotel.constants.PageConstants;
import com.example.hotel.constants.RequestConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDetailCommand implements Command {
    private CommonService commonService;

    public BookingDetailCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult;
        int bookingId = Integer.parseInt(requestContent.getRequestParameter(RequestConstants.BOOKING_ID)[0]);
        Map<String, Object> requestAttributes = new HashMap<>();
        try {
            List<Booking> bookingList = commonService.findBookingById(bookingId);
            if (bookingList.isEmpty()) {
                requestAttributes.put(RequestConstants.ERROR_FINDING_BOOKINGS, MessageHandler.getMessage("message.no_bookings", (String) requestContent.getSessionAttribute(RequestConstants.LOCALE)));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.BOOKINGS_PAGE, requestAttributes);
            } else {
                Booking booking = bookingList.get(0);
                requestAttributes.put(RequestConstants.BOOKING, booking);
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.BOOKING_DETAIL_PAGE, requestAttributes);
            }
            return commandResult;
        } catch (ServiceException e) {
            return new DefaultCommand().execute(requestContent);
        }
    }
}
