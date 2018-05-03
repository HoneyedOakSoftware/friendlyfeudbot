package com.honeyedoaksoftware.friendlyfeudbot.command;

import com.honeyedoaksoftware.friendlyfeudbot.Model.Challenge;
import com.honeyedoaksoftware.friendlyfeudbot.repository.ChallengeRepository;
import com.honeyedoaksoftware.friendlyfeudbot.util.BotUtils;
import org.apache.commons.lang3.StringUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IIDLinkedObject;
import sx.blah.discord.handle.obj.IUser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChallengeCommand implements Command {

    private ChallengeRepository challengeRepository;

    public ChallengeCommand(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public void runCommand(MessageReceivedEvent event, String[] args) {
        IChannel channel = event.getChannel();
        BotUtils.sendMessage(channel, "Challenge declared! " + BotUtils.BOT_NAME + " received the following arguments: " + Arrays.toString(args));

        if (event.getAuthor().isBot()) {
            BotUtils.sendMessage(channel, "Darn Tootin! How did a bot issue a challenge? Mis me with that gay shit!!");
        }

        long challengerUserId = event.getAuthor().getLongID();

        boolean defenderError = false;
        if (args.length < 1) {
            defenderError = true;
        }
        Long defenderUserId = BotUtils.getIdFromText(args[0]);

        if (Objects.isNull(defenderUserId)) {
            defenderError = true;
        }

        if (defenderError) {
            BotUtils.sendMessage(channel, "<@" + challengerUserId + "> You need to declare a defender @ mention someone else");
            return;
        }


        if (challengerUserId == defenderUserId) {
            BotUtils.sendMessage(channel, "<@" + challengerUserId + "> Challenging yourself are you? 1v1 someone else!");
            return;
        }


        Long refereeUserId = null;
        if (args.length > 1) {
            refereeUserId = BotUtils.getIdFromText(args[1]);
        }

        if (refereeUserId == challengerUserId || refereeUserId == defenderUserId) {
            BotUtils.sendMessage(channel, "<@" + challengerUserId + "> The referee should be independent, do not pick either party as the referee. (a referee is not mandatory)");
            return;
        }

        long guildId = event.getGuild().getLongID();

        List<Long> mentionedBots = event.getMessage().getMentions().stream().filter(IUser::isBot).map(IIDLinkedObject::getLongID).collect(Collectors.toList());

        if (mentionedBots.contains(defenderUserId)) {
            BotUtils.sendMessage(channel, "<@" + challengerUserId + "> Do you honestly expect a bot to accept a challenge? please pick a human adversary!!");
            return;
        }

        if (refereeUserId != null && mentionedBots.contains(refereeUserId)) {
            BotUtils.sendMessage(channel, "<@" + challengerUserId + "> How do you expect a bot to referee a challenge? please pick a human witness!!");
            return;
        }

        String challengeText = Arrays.stream(args).filter(arg -> !arg.matches("<@\\d*>")).collect(Collectors.joining(" "));

        if (StringUtils.isBlank(challengeText)) {
            BotUtils.sendMessage(channel, "<@" + challengerUserId + "> just shouting names won't do, add a challenge description");
            return;
        }

        Challenge challenge = new Challenge(null, guildId, challengerUserId, defenderUserId, refereeUserId, challengeText);

        BotUtils.sendMessage(channel, "creating new challenge: " + challenge.toString());

        challengeRepository.save(challenge);
    }
}
