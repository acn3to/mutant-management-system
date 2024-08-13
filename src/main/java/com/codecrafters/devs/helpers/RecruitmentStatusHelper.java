package com.codecrafters.devs.helpers;

import com.codecrafters.devs.models.Mutant;
import com.codecrafters.devs.models.RecruitmentStatus;

public class RecruitmentStatusHelper {

    public static RecruitmentStatus calculateRecruitmentStatus(Mutant mutant, int enemiesDefeated) {
        int aliensDefeated = (int) Math.round(enemiesDefeated * 0.268);
        int demonsDefeated = (int) Math.round(enemiesDefeated * 0.432);
        boolean isEligibleForEspada = aliensDefeated > 20;

        mutant.setEnemiesDefeated(enemiesDefeated);

        return new RecruitmentStatus(null, mutant, isEligibleForEspada, enemiesDefeated, aliensDefeated, demonsDefeated);
    }
}
