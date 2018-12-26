package com.worldSimulator.map;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.creatures.Creature;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    private final int MAX_TRY_NUM = 30;

    private Creature[][] map;
    private MapType mapType;
    private Point size;
    private Random random;

    public Map(Point size, MapType mapType) {
        reset(size, mapType);

        random = new Random();
    }

    public Point getEmptyRandPos() {
        Point position = new Point(random.nextInt(size.x), random.nextInt(size.y));
        while (map[position.x][position.y] != null) {
            position.x = random.nextInt(size.x);
            position.y = random.nextInt(size.y);
        }
        if(mapType == MapType.SQUARE)
            return position;
        else if(mapType == MapType.HEXAGONAL)
            return toHexCoord(position);
        return null;
    }

    public Point getBirthPos( Point one, Point two ) {
        Point birthPos = getBirthPosInternal(one, two);
        int limit = MAX_TRY_NUM;
        while (get(birthPos) != null) {
            birthPos = getBirthPosInternal(one, two);
            if(limit-- <= 0)
                break;
        }
        if(get(birthPos) != null)
            return null;
        return birthPos;
    }

    private Point getBirthPosInternal( Point one, Point two ) {
        return getPosNearby(random.nextInt(2) == 0 ? one : two, 1);
    }

    public Point getPosNearby (Point position, boolean empty, int range, int maxStrength) {
        Point pos = getPosNearby(position, range);
        int count = MAX_TRY_NUM;
        while (pos.equals(position) || (get(pos) != null && (empty || get(pos).getStrength() > maxStrength))) {
            pos = getPosNearby(position, range);
            if (count-- <= 0)
                break;
        }
        if (empty && get(pos) != null)
            return null;
        return pos;
    }

    private Point getPosNearby(Point position, int range) {
        if(mapType == MapType.SQUARE) {
            return clampPos(new Point(position.x + random.nextInt(1 + range * 2) - range, position.y + random.nextInt(1 + range * 2) - range));
        } else if(mapType == MapType.HEXAGONAL) {
            int x = random.nextInt(1 + range * 2) - range;
            int y = random.nextInt(1 + range * 2 - Math.abs(x)) - Math.min(range + x, range);
            return clampPos(new Point(position.x + x, position.y + y));
        }
        return null;
    }

    public Point getPosNearby (Point position, boolean empty, int range) {
        return getPosNearby(position, empty, range, Integer.MAX_VALUE);
    }

    public Point getPosNearby (Point position, boolean empty) {
        return getPosNearby(position, empty, 1, Integer.MAX_VALUE);
    }

    public ArrayList<Creature> getNeighbours(Point position) {
        ArrayList<Creature> list = new ArrayList<Creature>();
        Creature neighbour;
        Point pos;
        if(mapType == MapType.SQUARE) {
            for (int i = -1; i <= 1; i++)
                for (int j =  -1; j <= 1; j++) {
                    pos = new Point(position.x + i, position.y + j);
                    if((i == 0 && j == 0) || !positionFits(pos))
                        continue;
                    neighbour = get(pos);
                    if(neighbour != null)
                        list.add(neighbour);
                }
        } else if(mapType == MapType.HEXAGONAL) {
            for (int i = -1; i <= 1; i++) {
                int y = (i == -1 ? 0 : -1);
                pos = new Point(position.x + i, position.y + y);
                if(!positionFits(pos))
                    continue;
                neighbour = get(pos);
                if(neighbour != null)
                    list.add(neighbour);

                y = (i == 1 ? 0 : 1);
                pos = new Point(position.x + i, position.y + y);
                if(!positionFits(pos))
                    continue;
                neighbour = get(pos);
                if(neighbour != null)
                    list.add(neighbour);
            }
        }
        return list;
    }

    private Creature[] getColumn(Point position) {
        if(mapType == MapType.SQUARE)
            return map[position.x];
        else if(mapType == MapType.HEXAGONAL)
            return map[position.x + position.y / 2];
        return null;
    }

    public Point fromHexCoord(Point position) {
        position.x += position.y / 2;
        return position;
    }

    public Point toHexCoord(Point position) {
        position.x -= position.y / 2;
        return position;
    }

    public Creature get(Point position) {
        return getColumn(position)[position.y];
    }

    public void put(Creature creature, Point position) {
        getColumn(position)[position.y] = creature;
    }

    public void move(Point oldPos, Point newPos) {
        getColumn(newPos)[newPos.y] = getColumn(oldPos)[oldPos.y];
        remove(oldPos);
    }

    public void remove(Point position) {
        getColumn(position)[position.y] = null;
    }

    public void reset(Point size, MapType mapType) {
        this.mapType = mapType;
        this.size = size;
        map = new Creature[size.x][];
        for (int i = 0; i < size.x; i++)
            map[i] = new Creature[size.y];
    }

    public Point clampPos(Point position) {
        if(mapType == MapType.SQUARE)
            return position.clamp(new Point(size).substract(1));
        else if(mapType == MapType.HEXAGONAL)
            return toHexCoord(fromHexCoord(position).clamp(new Point(size).substract(1)));
        return null;
    }

    public boolean positionFits(Point position) {
        Point pos = new Point(position);
        if(mapType == MapType.HEXAGONAL)
            pos = fromHexCoord(pos);
        return pos.x >= 0 && pos.y >= 0 && pos.x < size.x && pos.y < size.y;
    }

    public MapType getMapType() {
        return mapType;
    }

    public Point getSize() {
        return size;
    }

}
