package com.worldSimulator.world.creatures.animals;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.Animal;
import com.worldSimulator.world.creatures.Creature;

import java.awt.*;

public class Turtle extends Animal {

    private final int DEFENDS_BELOW = 5;
    private final double MOVE_CHANCE = 0.25;

    public Turtle(int strength, int initiative, char symbol, Point position, World world, Color color) {
        super(strength, initiative, symbol, position, world, color);
    }

    @Override
    protected Point getMovePos() {
        if (Math.random() > MOVE_CHANCE)
            return null;
        return super.getMovePos();
    }

    @Override
    public boolean defend(Creature aggressor) {
        return aggressor.getStrength() < DEFENDS_BELOW;
    }

}
