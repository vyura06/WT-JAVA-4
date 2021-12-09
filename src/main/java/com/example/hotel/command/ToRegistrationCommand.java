package com.example.hotel.command;

import com.example.hotel.constants.PageConstants;

public class ToRegistrationCommand implements Command {
    @Override
    public CommandResult execute(RequestContent requestContent) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.REGISTRATION_PAGE);
    }
}
