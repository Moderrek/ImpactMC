package com.impact.lib.api.noise;

public class WhiteNoise2D extends Noise2D {
    @Override
    public float[][] generate(int width, int height) {
        float[][] tab = new float[width][height];
        for (int x = 0; x < width; x += 1) for (int y = 0; y < height; y += 1) tab[x][y] = (float) Math.random();
        return tab;
    }
}
