package com.example.hotel.command;

import com.example.hotel.constants.PageConstants;

public class ToUserProfileCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.USER_PROFILE_PAGE);
    }
}
