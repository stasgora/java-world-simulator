package com.worldSimulator.world.creatures.plants;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.Plant;

import java.awt.*;

public class Sonchus extends Plant {

    public Sonchus(int strength, char symbol, Point position, World world, Color color) {
        super(strength, symbol, position, world, color);
    }

    @Override
    public void action() {
        super.action();
        spread();
        spread();
    }
}
