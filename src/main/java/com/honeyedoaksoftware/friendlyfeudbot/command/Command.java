package com.honeyedoaksoftware.friendlyfeudbot.command;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public interface Command {

    // Interface for a command to be implemented
    void runCommand(MessageReceivedEvent event, List<String> args);
}
