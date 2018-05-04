package com.honeyedoaksoftware.friendlyfeudbot.command;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public interface Command {

    // Interface for a command to be implemented
    void runCommand(MessageReceivedEvent event, String[] args);
}
