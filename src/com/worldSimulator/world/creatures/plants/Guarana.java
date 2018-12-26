package com.worldSimulator.world.creatures.plants;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.Creature;
import com.worldSimulator.world.creatures.Plant;

import java.awt.*;

public class Guarana extends Plant {

    private final int STRENGTH_VALUE_GIVEN = 3;

    public Guarana(int strength, char symbol, Point position, World world, Color color) {
        super(strength, symbol, position, world, color);
    }

    @Override
    public void die(Creature aggressor) {
        super.die(aggressor);
        aggressor.setStrength(aggressor.getStrength() + STRENGTH_VALUE_GIVEN);
        world.getComentator().reportEatingGuarana(aggressor, this);
    }

}
