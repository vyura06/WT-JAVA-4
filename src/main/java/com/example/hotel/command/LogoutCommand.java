package com.example.hotel.command;

import com.example.hotel.constants.PageConstants;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstants.INDEX_PAGE);
    }
}
