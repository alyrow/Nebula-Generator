package com.github.alyrow.nebulagenerator.lib;

import com.badlogic.gdx.math.MathUtils;
import squidpony.squidmath.FoamNoise;
import squidpony.squidmath.Noise;

/** Adapted from <a href="http://devmag.org.za/2009/04/25/perlin-noise/">http://devmag.org.za/2009/04/25/perlin-noise/</a>
 * Also adapted for the lib needs.
 * @author badlogic, alyrow */
public class PerlinNoiseGenerator {





    public static float[][] generateWhiteNoise (int width, int height, Noise.Noise2D generator, long seed) {
        float[][] noise = new float[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                noise[x][y] = (float) generator.getNoise(x, y);
            }
        }
        return noise;
    }

    public static float interpolate (float x0, float x1, float alpha) {
        return x0 * (1 - alpha) + alpha * x1;
    }

    public static float[][] generateSmoothNoise (float[][] baseNoise, int octave) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;
        float[][] smoothNoise = new float[width][height];

        int samplePeriod = 1 << octave; // calculates 2 ^ k
        float sampleFrequency = 1.0f / samplePeriod;
        for (int i = 0; i < width; i++) {
            int sample_i0 = (i / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod) % width; // wrap around
            float horizontal_blend = (i - sample_i0) * sampleFrequency;

            for (int j = 0; j < height; j++) {
                int sample_j0 = (j / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod) % height; // wrap around
                float vertical_blend = (j - sample_j0) * sampleFrequency;
                float top = interpolate(baseNoise[sample_i0][sample_j0], baseNoise[sample_i1][sample_j0], horizontal_blend);
                float bottom = interpolate(baseNoise[sample_i0][sample_j1], baseNoise[sample_i1][sample_j1], horizontal_blend);
                smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
            }
        }

        return smoothNoise;
    }

    public static float[][] generatePerlinNoise (float[][] baseNoise, int octaveCount) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;
        float[][][] smoothNoise = new float[octaveCount][][]; // an array of 2D arrays containing
        float persistance = 0.7f;

        for (int i = 0; i < octaveCount; i++) {
            smoothNoise[i] = generateSmoothNoise(baseNoise, i);
        }

        float[][] perlinNoise = new float[width][height]; // an array of floats initialised to 0

        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistance;
            totalAmplitude += amplitude;

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                perlinNoise[i][j] /= totalAmplitude;

                //perlinNoise[i][j] = 1-perlinNoise[i][j];

            }
        }

        return perlinNoise;
    }

    public static float[][] generatePerlinNoise (int width, int height, int octaveCount, Noise.Noise2D generator, long seed) {
        float[][] baseNoise = generateWhiteNoise(width, height, generator, seed);
        return generatePerlinNoise(baseNoise, octaveCount);
    }


}