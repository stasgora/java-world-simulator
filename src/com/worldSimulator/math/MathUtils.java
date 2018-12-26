package com.worldSimulator.math;

public class MathUtils {

    public static int clamp(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }

}
