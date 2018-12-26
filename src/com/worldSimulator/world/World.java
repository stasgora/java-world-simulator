package com.worldSimulator.world;

import com.worldSimulator.swing.GamePanel;
import com.worldSimulator.world.creatures.Creature;
import com.worldSimulator.world.creatures.CreatureFactory;
import com.worldSimulator.world.creatures.Human;
import com.worldSimulator.map.Map;
import com.worldSimulator.map.MapType;
import com.worldSimulator.math.Point;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class World {

    private static final int SPAWN_QUANT_DIV = 40;

    private final String SAVES_FOLDER = "saves";
    private final String SAVE_FILE_NAME = "save.txt";

    private Map map;

    private ArrayList<Creature> list, dead, born;

    private Human human;
    private Comentator comentator;
    private CreatureFactory factory;
    private GamePanel gamePanel;

    public World(Point size, MapType mapType, GamePanel gamePanel, JTextArea logArea) {
        map = new Map(size, mapType);
        list = new ArrayList<>();
        dead = new ArrayList<>();
        born = new ArrayList<>();
        comentator = new Comentator(logArea);
        factory = new CreatureFactory();
        this.gamePanel = gamePanel;

        prePopulateMap(size);
    }

    public Map getMap() {
        return map;
    }

    public void update() {
        for (Creature creature : list) {
            if(creature.isDead())
                continue;
            creature.action();
        }

        for (Creature creature : dead)
            despawn(creature);
        for (Creature creature : born)
            spawn(creature);
        dead.clear();
        born.clear();
    }

    public void saveWorldState() {
        try {
            PrintWriter writer = new PrintWriter(Paths.get(".", SAVES_FOLDER, SAVE_FILE_NAME).toFile());
            writer.printf("%d %d %d %d%n", list.size(), map.getSize().x, map.getSize().y, map.getMapType() == MapType.SQUARE ? 0 : 1);
            for (Creature creature : list) {
                writer.printf("%c %d %d %d %d %d", creature.getSymbol(true), creature.getPosition().x, creature.getPosition().y, creature.getAge(), creature.getStrength(), creature.getLastMated());
                if(creature instanceof Human)
                    writer.print(" " + String.valueOf(((Human) creature).getAbilityCounter()));
                writer.println();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            comentator.reportFileSavingError();
            e.printStackTrace();
        }
    }

    public void loadWorldState() {
        try {
            Scanner reader = new Scanner(Paths.get(System.getProperty("user.dir"), SAVES_FOLDER, SAVE_FILE_NAME).toFile());
            int creatureNum = reader.nextInt();
            Point newSize = new Point(reader.nextInt(), reader.nextInt());
            map.reset(newSize, reader.nextInt() == 0 ? MapType.SQUARE : MapType.HEXAGONAL);
            list.clear();
            human = null;
            Creature creature;
            for (int i = 0; i < creatureNum; i++) {
                creature = spawn(factory.spawnNew(reader.next().charAt(0), new Point(reader.nextInt(), reader.nextInt()), this));
                creature.setAge(reader.nextInt());
                creature.setStrength(reader.nextInt());
                creature.setLastMated(reader.nextInt());
                if(creature.getSymbol(true) == 'C') {
                    human = (Human) creature;
                    ((Human) creature).setAbilityCounter(reader.nextInt());
                }
            }
            reader.close();
            gamePanel.setup(map.getSize(), map.getMapType());
        } catch (FileNotFoundException e) {
            comentator.reportFileReadingError();
            e.printStackTrace();
        }
    }

    public Point findClosestCreature(char symbol, Point position) {
        int minDist = -1, minIndex = -1, dist;
        Point ref;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSymbol(true) == symbol) {
                ref = list.get(i).getPosition();
                dist = (ref.x - position.x) * (ref.x - position.x) + (ref.y - position.y) * (ref.y - position.y);
                if (minDist == -1 || minDist > dist) {
                    minDist = dist;
                    minIndex = i;
                }
            }
        }
        if(minIndex == -1)
            return null;
	    return list.get(minIndex).getPosition();
    }

    public void reset(Point size, MapType mapType) {
        map.reset(size, mapType);
        list.clear();
        dead.clear();
        born.clear();

        prePopulateMap(size);
    }

    private void prePopulateMap(Point size) {
        int spawnNum = size.x * size.y / SPAWN_QUANT_DIV;
        while (spawnNum-- > 0)
            spawn(factory.spawnNew(' ', map.getEmptyRandPos(), this));
        human = (Human) factory.spawnNew('C', map.getEmptyRandPos(), this);
        spawn(human);
    }

    private Creature spawn(Creature creature) {
        map.put(creature, creature.getPosition());
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getInitiative() < creature.getInitiative()) {
                list.add(i, creature);
                return creature;
            }
        }
        list.add(list.size(), creature);
        return creature;
    }

    private void despawn(Creature creature) {
        if (creature == human)
            human = null;
        list.remove(creature);
    }

    public void kill(Creature lives, Creature dies) {
        if(dies == human)
            human = null;
        map.remove(dies.getPosition());
        dies.die(lives);
        dead.add(dies);
        comentator.reportDeath(lives, dies);
    }

    public void giveBirth(Creature creature, boolean canSpawn) {
        if(map.get(creature.getPosition()) != null)
            return;
        if(creature instanceof Human) {
            if(human != null)
                return;
            else
                this.human = (Human) creature;
        }

        map.put(creature, creature.getPosition());
        comentator.reportBirth(creature);
        if(canSpawn)
            spawn(creature);
        else
            born.add(creature);
    }
    public void giveBirth(Creature creature) {giveBirth(creature, false);}

    public Human getHuman() {
        return human;
    }

    public void useHumanAbility() {
        if(human != null)
            human.useAbility();
    }

    public void setHumanMove(char key) {
        if(human != null)
            human.setNextMove(key);
    }

    public Comentator getComentator() {
        return comentator;
    }

    public CreatureFactory getFactory() {
        return factory;
    }

    public ArrayList<Creature> getList() {
        return list;
    }

}
