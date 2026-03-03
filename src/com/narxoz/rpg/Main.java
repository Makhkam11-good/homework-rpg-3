package com.narxoz.rpg;

import com.narxoz.rpg.adapter.EnemyCombatantAdapter;
import com.narxoz.rpg.adapter.HeroCombatantAdapter;
import com.narxoz.rpg.battle.BattleEngine;
import com.narxoz.rpg.battle.Combatant;
import com.narxoz.rpg.battle.EncounterResult;
import com.narxoz.rpg.enemy.Goblin;
import com.narxoz.rpg.hero.Mage;
import com.narxoz.rpg.hero.Warrior;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== RPG Battle Engine Demo ===\n");

        // TODO: Create heroes and enemies
        Warrior warrior1 = new Warrior("Arthas");
        Warrior warrior2 = new Warrior("Thrall");
        Mage mage1 = new Mage("Jaina");
        Mage mage2 = new Mage("Medivh");
        Goblin goblin1 = new Goblin();
        Goblin goblin2 = new Goblin();
        Goblin goblin3 = new Goblin();

        // TODO: Wrap with adapters
        List<Combatant> heroes = new ArrayList<>();
        heroes.add(new HeroCombatantAdapter(warrior1));
        heroes.add(new HeroCombatantAdapter(warrior2));
        heroes.add(new HeroCombatantAdapter(mage1));
        heroes.add(new HeroCombatantAdapter(mage2));

        List<Combatant> enemies = new ArrayList<>();
        enemies.add(new EnemyCombatantAdapter(goblin1));
        enemies.add(new EnemyCombatantAdapter(goblin2));
        enemies.add(new EnemyCombatantAdapter(goblin3));

        // TODO: Demonstrate Singleton behavior
        BattleEngine engineA = BattleEngine.getInstance();
        BattleEngine engineB = BattleEngine.getInstance();
        System.out.println("Same instance? " + (engineA == engineB));
        System.out.println();

        // TODO: Run battle and print summary
        engineA.setRandomSeed(42L);
        EncounterResult result = engineA.runEncounter(heroes, enemies);

        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());
        for (String line : result.getBattleLog()) {
            System.out.println(line);
        }

        System.out.println("\n=== Edge Case Test: Hero Death Scenario ===\n");

        Warrior weakWarrior = new Warrior("Arthas");
        Goblin strongGoblin1 = new Goblin();
        Goblin strongGoblin2 = new Goblin();

        List<Combatant> singleHero = new ArrayList<>();
        singleHero.add(new HeroCombatantAdapter(weakWarrior));

        List<Combatant> multipleEnemies = new ArrayList<>();
        multipleEnemies.add(new EnemyCombatantAdapter(strongGoblin1));
        multipleEnemies.add(new EnemyCombatantAdapter(strongGoblin2));

        EncounterResult result2 = engineA.runEncounter(singleHero, multipleEnemies);

        System.out.println("Winner: " + result2.getWinner());
        System.out.println("Rounds: " + result2.getRounds());
        for (String line : result2.getBattleLog()) {
            System.out.println(line);
        }

        System.out.println("\n=== Demo Complete ===");
    }
}
