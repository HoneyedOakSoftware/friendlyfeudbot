package com.honeyedoaksoftware.friendlyfeudbot;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.modules.IModule;

public class FriendlyFeudBot implements IModule {

    protected static final String moduleName = "FriendlyFeudBot";
    private static final String moduleVersion = "0.0.1";
    private static final String moduleMinimumVersion = "2.3.0";
    private static final String Author = "Pieter Van Eeckhout";


    protected static IDiscordClient client;


    @Override
    public boolean enable(IDiscordClient client) {
        FriendlyFeudBot.client = client;
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new MessageHandler());
        return true;
    }

    @Override
    public void disable() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getMinimumDiscord4JVersion() {
        return null;
    }
}
