package com.example.hotel.command;

import java.util.HashMap;
import java.util.Map;

import com.example.hotel.constants.RequestConstants;

public class LanguageCommand implements Command {


    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult;
        String page = requestContent.getRequestParameter(RequestConstants.PAGE)[0];
        String locale = requestContent.getRequestParameter(RequestConstants.LOCALE)[0];

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(RequestConstants.LOCALE, locale);
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put(RequestConstants.LOCALE, locale);
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, page, attributes, sessionAttributes);
        return commandResult;
    }
}
