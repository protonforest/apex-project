package com.apex.pointsystem.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PointsConfig {

    @Value("${rewards.points.overonehundred}")
    public Integer rewardPointsOverOneHundred;

    public Integer getRewardPointsOverOneHundred () {
        return rewardPointsOverOneHundred;
    }
}
