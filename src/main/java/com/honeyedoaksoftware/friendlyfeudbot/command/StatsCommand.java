package com.honeyedoaksoftware.friendlyfeudbot.command;

import com.honeyedoaksoftware.friendlyfeudbot.Model.Challenge;
import com.honeyedoaksoftware.friendlyfeudbot.repository.ChallengeRepository;
import com.honeyedoaksoftware.friendlyfeudbot.util.BotUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.util.*;
import java.util.stream.Collectors;

@Log4j
@Component
public class StatsCommand implements Command {

    public static final String COMMAND_TEXT = "stats";

    private ChallengeRepository challengeRepository;

    @Autowired
    public StatsCommand(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public void runCommand(MessageReceivedEvent event, String[] args) {
        IChannel channel = event.getChannel();
        long guildId = event.getGuild().getLongID();

        log.debug("Stats requested! " + BotUtils.BOT_NAME + " received the following arguments: " + Arrays.toString(args));


        if (args.length > 0 && !args[0].matches("<@\\d*>")) {
            BotUtils.sendMessage(channel, "You need to pass the user as first argument, or no argument at all.");
            return;
        }

        if (args.length == 0) {
            handleGuildStats(guildId);
            return;
        }

        BotUtils.getIdFromText(args[0]).ifPresent(id -> handleUserStats(guildId, id));

        log.trace("stats request fulfilled");
    }

    private void handleUserStats(long guildId, long userId) {
        List<Challenge> challengeList = challengeRepository.findApplicableTo(guildId, userId);

        long wins = challengeList.stream().filter(challenge -> Objects.nonNull(challenge.getWinnerUserId()) && userId == challenge.getWinnerUserId()).count();
        int total = challengeList.size();

        Map<Long, List<Challenge>> asChallengerPerOponent = challengeList.stream().filter(challenge -> challenge.getChallengerUserId() == userId).collect(Collectors.groupingBy(Challenge::getDefenderUserId));
        Map<Long, List<Challenge>> asDefenderPerOponent = challengeList.stream().filter(challenge -> challenge.getDefenderUserId() == userId).collect(Collectors.groupingBy(Challenge::getChallengerUserId));
        List<Challenge> asReferee = challengeList.stream().filter(c -> Objects.nonNull(c.getRefereeUserId()) && userId == c.getRefereeUserId()).collect(Collectors.toList());

        Map<Long, List<Challenge>> allChallenges = new HashMap<>(asChallengerPerOponent);
        asDefenderPerOponent.forEach((key, value) -> allChallenges.merge(key, value, (v1, v2) -> {
            v1.addAll(v2);
            return v1;
        }));

    }

    private void handleGuildStats(long guildId) {
    }
}
