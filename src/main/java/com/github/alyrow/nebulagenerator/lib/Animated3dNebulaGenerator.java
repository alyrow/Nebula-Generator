package com.github.alyrow.nebulagenerator.lib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;


/** 3d animation for nebulae
 * @author alyrow */
public class Animated3dNebulaGenerator {
    public static class Time extends NebulaGenerator {
        long startTime;

        public Time(int width, int height, NoiseType type, long seed, int octave, Color color, float alpha, Vector2 offset) {
            super(width, height, type, seed, octave, color, alpha, offset);
            startTime = TimeUtils.millis();
        }

        @Override
        public Pixmap generatePixmapNebula(Pixmap pix) {
            pix.setBlending(Pixmap.Blending.SourceOver);
            Color cln = new Color(1, 1, 1, 0).sub(color), temp = new Color();
            float time = TimeUtils.timeSinceMillis(startTime) * 0.01f;
            for (int x=0; x<this.width; x++) {
                for (int y=0; y<this.height; y++) {
//                float n = this.generator.getConfiguredNoise(x, y);
                    float n = this.generator.getConfiguredNoise(x+offset.x, y+offset.y, time);
                    float div = 0.5f;
                    float r = (cln.r+n)*div;
                    float g = (cln.g+n)*div;
                    float b = (cln.b+n)*div;

                    pix.setColor(temp.set(r,g,b,0f).sub(cln).add(0.2f, 0.2f, 0.2f, (n*n*n)/1f*alpha));
                    pix.drawPixel(x, y);
                }
            }
            pix.setBlending(Pixmap.Blending.None);
            return pix;
        }

        public void resetTimer() {
            startTime = TimeUtils.millis();
        }
    }

    public static class Frame extends NebulaGenerator {
        int frame;
        float speed = 10;

        public Frame(int width, int height, NoiseType type, long seed, int octave, Color color, float alpha, Vector2 offset) {
            super(width, height, type, seed, octave, color, alpha, offset);
            frame = 0;
        }

        public Frame(int width, int height, NoiseType type, long seed, int octave, Color color, float alpha, Vector2 offset, float speed) {
            super(width, height, type, seed, octave, color, alpha, offset);
            frame = 0;
            this.speed = speed;
        }

        @Override
        public Pixmap generatePixmapNebula(Pixmap pix) {
            pix.setBlending(Pixmap.Blending.SourceOver);
            Color cln = new Color(1, 1, 1, 0).sub(color), temp = new Color();
            for (int x=0; x<this.width; x++) {
                for (int y=0; y<this.height; y++) {
//                float n = this.generator.getConfiguredNoise(x, y);
                    float n = this.generator.getConfiguredNoise(x+offset.x, y+offset.y, frame*speed);
                    float div = 0.5f;
                    float r = (cln.r+n)*div;
                    float g = (cln.g+n)*div;
                    float b = (cln.b+n)*div;

                    pix.setColor(temp.set(r,g,b,0f).sub(cln).add(0.2f, 0.2f, 0.2f, (n*n*n)/1f*alpha));
                    pix.drawPixel(x, y);
                }
            }
            pix.setBlending(Pixmap.Blending.None);
            frame++;
            return pix;
        }

        public void setFrame(int frame) {
            this.frame = frame;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }
    }
}
