package com.worldSimulator.world;

import com.worldSimulator.world.creatures.Creature;
import com.worldSimulator.world.creatures.Human;
import com.worldSimulator.world.creatures.plants.Guarana;

import javax.swing.*;

public class Comentator {

    private JTextArea logArea;

    public Comentator(JTextArea logArea) {
        this.logArea = logArea;
    }

    public void reportDeath(Creature lives, Creature dies) {
        logArea.append(lives.getClass().getSimpleName() + " at " + lives.getPosition().toString() + " killed " + dies.getClass().getSimpleName() + " at " + dies.getPosition().toString() + "\n");
    }

    public void reportBirth(Creature born) {
        logArea.append(born.getClass().getSimpleName() + " was born at " + born.getPosition().toString() + "\n");
    }

    public void reportAbilityUse(Human human) {
        logArea.append("Human drank his magic potion. Strength = " + String.valueOf(human.getStrength()) + "\n");
    }

    public void reportEatingGuarana(Creature creature, Guarana guarana) {
        logArea.append(creature.getClass().getSimpleName() + " at " + creature.getPosition().toString() + " ate Guarana at "
                + guarana.getPosition().toString() + ". Strength = " + String.valueOf(creature.getStrength()) + "\n");
    }

    public void reportFileSavingError() {
        logArea.append("Could not save world state.\n");
    }

    public void reportFileReadingError() {
        logArea.append("Could not read world state.\n");
    }

    public void clearLog() {
        logArea.setText("");
    }

}
