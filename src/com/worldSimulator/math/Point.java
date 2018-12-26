package com.worldSimulator.math;

public class Point {

    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0, 0);
    }

    public Point(Point point) {
        x = point.x;
        y = point.y;
    }

    public Point add(int value) {
        x += value;
        y += value;
        return this;
    }

    public Point substract(int value) {
        x -= value;
        y -= value;
        return this;
    }

    public Point multiply(int value) {
        x *= value;
        y *= value;
        return this;
    }

    public Point divide(int value) {
        x /= value;
        y /= value;
        return this;
    }

    public Point clamp(int min, int max) {
        x = MathUtils.clamp(x, min, max);
        y = MathUtils.clamp(y, min, max);
        return this;
    }

    public Point add(Point point) {
        x += point.x;
        y += point.y;
        return this;
    }

    public Point substract(Point point) {
        x -= point.x;
        y -= point.y;
        return this;
    }

    public Point multiply(Point point) {
        x *= point.x;
        y *= point.y;
        return this;
    }

    public Point divide(Point point) {
        x /= point.x;
        y /= point.y;
        return this;
    }

    public Point clamp(Point min, Point max) {
        x = MathUtils.clamp(x, min.x, max.x);
        y = MathUtils.clamp(y, min.y, max.y);
        return this;
    }

    public Point clamp(Point max) {
        return clamp(new Point(), max);
    }

    public Point min(Point point) {
        return new Point(Math.min(x, point.x), Math.min(y, point.y));
    }

    public Point max(Point point) {
        return new Point(Math.max(x, point.x), Math.max(y, point.y));
    }

    public boolean equals(Point point) {
        return x == point.x && y == point.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
