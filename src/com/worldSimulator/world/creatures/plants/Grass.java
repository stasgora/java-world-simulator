package com.worldSimulator.world.creatures.plants;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.Plant;

import java.awt.*;

public class Grass extends Plant {

    public Grass(int strength, char symbol, Point position, World world, Color color) {
        super(strength, symbol, position, world, color);
    }

}
