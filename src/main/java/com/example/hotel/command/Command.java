package com.example.hotel.command;

public interface Command {
    CommandResult execute(RequestContent requestContent);
}
