package com.worldSimulator.world.creatures.plants;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.Creature;
import com.worldSimulator.world.creatures.Plant;

import java.awt.*;

public class Belladonna extends Plant {

    public Belladonna(int strength, char symbol, Point position, World world, Color color) {
        super(strength, symbol, position, world, color);
    }

    @Override
    public void die(Creature aggressor) {
        super.die(aggressor);
        world.kill(this, aggressor);
    }

}
