package com.honeyedoaksoftware.friendlyfeudbot.repository;

import com.honeyedoaksoftware.friendlyfeudbot.Model.Challenge;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends CrudRepository<Challenge, Long> {

    List<Challenge> findByGuildIdAndChallengerUserId(long guildId, long challengerUserId);

    List<Challenge> findByGuildIdAndDefenderUserId(long guildId, long defenderUserId);

    List<Challenge> findByGuildIdAndChallengerUserIdAndDefenderUserId(long guildId, long challengerUserId, long defenderUserId);

    Optional<Challenge> findByGuildIdAndChallengeCode(long guildId, String challengeCode);

    List<Challenge> findByGuildIdAndRefereeUserId(long guildId, long refereeUserId);

    List<Challenge> findByGuildId(long guildId);

    default List<Challenge> findApplicableTo(long guildId, long userId) {
        List<Challenge> result = findByGuildIdAndChallengerUserId(guildId, userId);
        result.addAll(findByGuildIdAndDefenderUserId(guildId, userId));
        result.addAll(findByGuildIdAndRefereeUserId(guildId, userId));

        return result;
    }

    int countByGuildIdAndChallengeCode(long guildId, String challengeCode);
}