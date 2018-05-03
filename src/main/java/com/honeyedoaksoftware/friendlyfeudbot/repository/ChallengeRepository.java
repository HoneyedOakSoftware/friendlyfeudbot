package com.honeyedoaksoftware.friendlyfeudbot.repository;

import com.honeyedoaksoftware.friendlyfeudbot.Model.Challenge;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChallengeRepository extends CrudRepository<Challenge, Long> {

    List<Challenge> findByChallengerUserId(long challengerUserId);

    List<Challenge> findByChallengerUserIdAndDefenderUserId(long challengerUserId, long defenderUserId);
}
