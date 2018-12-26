package com.worldSimulator.world.creatures.animals;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.Animal;
import com.worldSimulator.world.creatures.Creature;

import java.awt.*;

public class Antelope extends Animal {

    private final double ESCAPE_CHANCE = 0.5;

    public Antelope(int strength, int initiative, char symbol, Point position, World world, Color color) {
        super(strength, initiative, symbol, position, world, color);
        range = 2;
    }

    @Override
    public boolean defend(Creature aggressor) {
        if(Math.random() > ESCAPE_CHANCE)
            return false;
        Point newPos = world.getMap().getPosNearby(position, true, 1);
        if(newPos == null)
            return false;
        move(newPos);
        return true;
    }
}
