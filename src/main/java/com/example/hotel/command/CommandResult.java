package com.example.hotel.command;

import java.util.HashMap;
import java.util.Map;

public class CommandResult {
    public enum ResponseType {
        FORWARD,
        REDIRECT
    }

    private final ResponseType responseType;
    private final String page;
    private final Map<String, Object> attributes;
    private final Map<String, Object> sessionAttributes;

    public CommandResult(ResponseType responseType, String page) {
        this(responseType, page, new HashMap<>(), new HashMap<>());
    }

    public CommandResult(ResponseType responseType, String page, Map<String, Object> attributes) {
        this(responseType, page, attributes, new HashMap<>());
    }

    public CommandResult(ResponseType responseType, String page, Map<String, Object> attributes,
                         Map<String, Object> sessionAttributes) {
        this.responseType = responseType;
        this.page = page;
        this.attributes = attributes;
        this.sessionAttributes = sessionAttributes;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getPage() {
        return page;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }
}
