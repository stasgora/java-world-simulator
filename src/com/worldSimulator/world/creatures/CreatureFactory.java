package com.worldSimulator.world.creatures;

import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.animals.*;
import com.worldSimulator.world.creatures.plants.*;
import com.worldSimulator.math.Point;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class CreatureFactory {

    private Random random;
    private HashMap<String, Character> nameToSymbol;

    public CreatureFactory() {
        random = new Random();
        nameToSymbol = new HashMap<>();
        nameToSymbol.put("Antelope", 'A');
        nameToSymbol.put("CyberSheep", 'X');
        nameToSymbol.put("Fox", 'L');
        nameToSymbol.put("Sheep", 'O');
        nameToSymbol.put("Turtle", 'Z');
        nameToSymbol.put("Wolf", 'W');
        nameToSymbol.put("Belladonna", 'J');
        nameToSymbol.put("Grass", 'T');
        nameToSymbol.put("Guarana", 'G');
        nameToSymbol.put("Sonchus", 'M');
        nameToSymbol.put("SosnowskyHogweed", 'B');
        nameToSymbol.put("Human", 'C');
    }

    public Creature spawnNew(char symbol, Point position, World world) {
        int rand = -1;
        if(symbol == ' ')
            rand = random.nextInt(11);
        if(symbol == 'A' || rand == 0)
            return new Antelope(4, 4, 'A', position, world, getColor(140, 80, 34));
        else if(symbol == 'X' || rand == 1)
            return new CyberSheep(11, 4, 'X', position, world, getColor(76, 133, 138));
        else if(symbol == 'L' || rand == 2)
            return new Fox(3, 7, 'L', position, world, getColor(216, 88, 25));
        else if(symbol == 'O' || rand == 3)
            return new Sheep(4, 4, 'O', position, world, getColor(213, 212, 206));
        else if(symbol == 'Z' || rand == 4)
            return new Turtle(2, 1, 'Z', position, world, getColor(147, 124, 83));
        else if(symbol == 'W' || rand == 5)
            return new Wolf(9, 5, 'W', position, world, getColor(144, 150, 151));
        else if(symbol == 'J' || rand == 6)
            return new Belladonna(99, 'J', position, world, getColor(22, 62, 95));
        else if(symbol == 'T' || rand == 7)
            return new Grass(0, 'T', position, world, getColor(135, 181, 10));
        else if(symbol == 'G' || rand == 8)
            return new Guarana(0, 'G', position, world, getColor(251, 32, 24));
        else if(symbol == 'M' || rand == 9)
            return new Sonchus(0, 'M', position, world, getColor(238, 218, 69));
        else if(symbol == 'B' || rand == 10)
            return new SosnowskyHogweed(10, 'B', position, world, getColor(67, 95, 58));
        else if(symbol == 'C')
            return new Human(5, 4, 'C', position, world, getColor(244, 216, 188));
        return null;
    }

    public Creature spawnNew(String name, Point position, World world) {
        if(nameToSymbol.containsKey(name))
            return spawnNew(nameToSymbol.get(name), position, world);
        return null;
    }

    private Color getColor(int r, int g, int b) {
        return new Color(r / 255f, g / 255f, b / 255f);
    }

}