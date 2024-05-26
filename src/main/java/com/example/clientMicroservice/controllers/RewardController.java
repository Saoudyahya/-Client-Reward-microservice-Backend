package com.example.clientMicroservice.controllers;


import com.example.clientMicroservice.Configuration.MQConfig;
import com.example.clientMicroservice.Messages.MessageModel;
import com.example.clientMicroservice.models.Reward;
import com.example.clientMicroservice.service.RewardService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;
    @Autowired
    private RabbitTemplate template;

    @GetMapping
    public List<Reward> getAllRewards() {
        return rewardService.getAllRewards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reward> getRewardById(@PathVariable Long id) {
        Optional<Reward> reward = rewardService.getRewardById(id);
        return reward.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reward createReward(@RequestBody Reward reward) {

        Reward newReward= rewardService.saveReward(reward);
        publishMessage("CREATE", newReward);
        return newReward;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reward> updateReward(@PathVariable Long id, @RequestBody Reward updatedReward) {
        Reward updated = rewardService.updateReward(id, updatedReward);
        publishMessage("UPDATE", updated);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReward(@PathVariable Long id) {
        Optional<Reward> deletedReward = rewardService.getRewardById(id);
        deletedReward.ifPresent(reward -> publishMessage("DELETE",reward));
        rewardService.deleteReward(id);
        return ResponseEntity.noContent().build();
    }

    private void publishMessage(String action, Reward Reward) {
        MessageModel message = new MessageModel();
        message.setAction(action);
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessage(" Reward point : " + Reward.getPointsEarned()+" with id:"+Reward.getId()+"for "+Reward.getClient()+" have been "+ action ); // Set the Message field based on the action
        message.setMassageDate(new Date());
        template.convertAndSend(MQConfig.IMPORT_EXPORT_TOPIC_EXCHANGE, MQConfig.ROUTING_KEY, message);
    }
}