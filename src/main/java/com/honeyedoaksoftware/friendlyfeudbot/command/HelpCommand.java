package com.honeyedoaksoftware.friendlyfeudbot.command;

import com.honeyedoaksoftware.friendlyfeudbot.repository.ChallengeRepository;
import com.honeyedoaksoftware.friendlyfeudbot.util.BotUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.util.Arrays;

@Log4j
@Component
public class HelpCommand implements Command {

    private ChallengeRepository challengeRepository;

    @Autowired
    public HelpCommand(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public void runCommand(MessageReceivedEvent event, String[] args) {
        IChannel channel = event.getChannel();
        BotUtils.sendMessage(channel, BotUtils.BOT_NAME + " help function not implemented yet. Received the following arguments: " + Arrays.toString(args));
    }
}
