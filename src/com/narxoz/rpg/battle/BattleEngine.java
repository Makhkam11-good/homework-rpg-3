package com.narxoz.rpg.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class BattleEngine {
    private static BattleEngine instance;
    private Random random = new Random(1L);

    private BattleEngine() {
    }

    public static BattleEngine getInstance() {
        if (instance == null) {
            instance = new BattleEngine();
        }
        return instance;
    }

    public BattleEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public void reset() {
        // TODO: reset any battle state if you add it
    }

    public EncounterResult runEncounter(List<Combatant> teamA, List<Combatant> teamB) {
        EncounterResult result = new EncounterResult();
        
        List<Combatant> aliveA = new ArrayList<>(teamA);
        List<Combatant> aliveB = new ArrayList<>(teamB);
        
        int round = 0;
        
        while (!aliveA.isEmpty() && !aliveB.isEmpty()) {
            round++;
            result.addLog("--- Round " + round + " ---");
            
            for (Combatant attacker : new ArrayList<>(aliveA)) {
                if (aliveB.isEmpty()) break;
                
                Combatant target = aliveB.get(0);
                int damage = attacker.getAttackPower();
                target.takeDamage(damage);
                
                result.addLog(attacker.getName() + " attacks " + target.getName() + " for " + damage + " damage!");
                
                if (!target.isAlive()) {
                    result.addLog(target.getName() + " is defeated!");
                    aliveB.remove(target);
                }
            }
            
            if (aliveB.isEmpty()) {
                result.setWinner("Team A");
                break;
            }
            
            for (Combatant attacker : new ArrayList<>(aliveB)) {
                if (aliveA.isEmpty()) break;
                
                Combatant target = aliveA.get(0);
                int damage = attacker.getAttackPower();
                target.takeDamage(damage);
                
                result.addLog(attacker.getName() + " attacks " + target.getName() + " for " + damage + " damage!");
                
                if (!target.isAlive()) {
                    result.addLog(target.getName() + " is defeated!");
                    aliveA.remove(target);
                }
            }
            
            if (aliveA.isEmpty()) {
                result.setWinner("Team B");
                break;
            }
        }
        
        result.setRounds(round);
        return result;
    }
}
