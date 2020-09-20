package com.github.alyrow.nebulagenerator.lib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;

/** Translation animation for nebulae
 * @author alyrow */
public class AnimatedTranslationNebulaGenerator extends NebulaGenerator {
    Vector2 translation;
    Vector2 position = new Vector2(0,0);

    public AnimatedTranslationNebulaGenerator(int width, int height, NoiseType type, long seed, int octave, Color color, float alpha, Vector2 offset) {
        super(width, height, type, seed, octave, color, alpha, offset);
        translation = new Vector2(0, 0);
    }

    public AnimatedTranslationNebulaGenerator(int width, int height, NoiseType type, long seed, int octave, Color color, float alpha, Vector2 offset, float vx, float vy) {
        super(width, height, type, seed, octave, color, alpha, offset);
        translation = new Vector2(vx, vy);
    }

    @Override
    public Pixmap generatePixmapNebula(Pixmap pix) {
        pix.setBlending(Pixmap.Blending.SourceOver);
        Color cln = new Color(1, 1, 1, 0).sub(color), temp = new Color();
        for (int x=0; x<this.width; x++) {
            for (int y=0; y<this.height; y++) {
                float n = this.generator.getConfiguredNoise(x+position.x+offset.x, y+position.y+offset.y);
                float div = 0.5f;
                float r = (cln.r+n)*div;
                float g = (cln.g+n)*div;
                float b = (cln.b+n)*div;

                pix.setColor(temp.set(r,g,b,0f).sub(cln).add(0.2f, 0.2f, 0.2f, (n*n*n)/1f*alpha));
                pix.drawPixel(x, y);
            }
        }
        pix.setBlending(Pixmap.Blending.None);
        position.x += translation.x;
        position.y += translation.y;
        return pix;
    }

    public void setTranslation(float vx, float vy) {
        translation.x = vx;
        translation.y = vy;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }
}
