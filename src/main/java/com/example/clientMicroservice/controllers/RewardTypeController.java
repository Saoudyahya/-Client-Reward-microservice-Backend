package com.example.clientMicroservice.controllers;

import com.example.clientMicroservice.models.RewardType;
import com.example.clientMicroservice.service.RewardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reward-types")
public class RewardTypeController {

    @Autowired
    private RewardTypeService rewardTypeService;

    @GetMapping
    public List<RewardType> getAllRewardTypes() {
        return rewardTypeService.getAllRewardTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RewardType> getRewardTypeById(@PathVariable Long id) {
        Optional<RewardType> rewardType = rewardTypeService.getRewardTypeById(id);
        return rewardType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public RewardType createRewardType(@RequestBody RewardType rewardType) {
        return rewardTypeService.saveRewardType(rewardType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RewardType> updateRewardType(@PathVariable Long id, @RequestBody RewardType updatedRewardType) {
        RewardType updated = rewardTypeService.updateRewardType(id, updatedRewardType);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRewardType(@PathVariable Long id) {
        rewardTypeService.deleteRewardType(id);
        return ResponseEntity.noContent().build();
    }
}