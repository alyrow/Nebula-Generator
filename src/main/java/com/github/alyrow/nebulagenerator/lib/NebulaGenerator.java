package com.github.alyrow.nebulagenerator.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import squidpony.squidmath.FastNoise;
import squidpony.squidmath.FoamNoise;
import squidpony.squidmath.Noise;
import squidpony.squidmath.SeededNoise;

/** Main class of the lib. Most of the work is done here
 * @author alyrow */
public class NebulaGenerator {
    public static float gamma = 2.2f;

    long seed = 10000;
    int width, height;
    Color color;
    Vector2 offset;
    Vector2 offset_max;
    int octave = 11;
    float alpha = 1;
    Noise.Noise2D generator;

    public NebulaGenerator(int width, int height, NoiseType type, long seed, int octave, Color color, float alpha, Vector2 offset_max, Vector2 offset) {
        this.width = width;
        this.height = height;
        this.seed = seed;
        if (type == NoiseType.DEFAULT) generator = new SeededNoise(this.seed);
        else if (type == NoiseType.VALUE) generator = new FastNoise((int) this.seed, 1, FastNoise.VALUE);
        else if (type == NoiseType.WHITE_NOISE) generator = new FastNoise((int) this.seed, 1, FastNoise.WHITE_NOISE);
        else if(type == NoiseType.FOAM_NOISE) generator = new FoamNoise(this.seed);
        this.octave = octave;
        this.color = color;
        this.alpha = alpha;
        this.offset_max = offset_max;
        this.offset = offset;
    }



    public Pixmap generatePixmapNebula(Pixmap pix) {
        //pix = new Pixmap(this.width, this.height, Pixmap.Format.RGBA8888);

        Color cln = color.cpy();
        cln = new Color(1, 1, 1, 0).sub(cln);
        cln.a = 1;
        float[][] noise_ = PerlinNoiseGenerator.generatePerlinNoise((int)(this.width + this.offset_max.x), (int)(this.height + this.offset_max.y), octave, this.generator, seed);

        for (int x=0; x<this.width; x++) {
            for (int y=0; y<this.height; y++) {
                float n = noise_[(int) (x+this.offset.x)][(int) (y+this.offset.y)];
                int div = 2;
                float r = (float) ((/*pc.r+*/cln.r+n)/div);
                float g = (float) ((/*pc.g+*/cln.g+n)/div);
                float b = (float) ((/*pc.b+*/cln.b+n)/div);

                pix.setBlending(Pixmap.Blending.SourceOver);
                pix.setColor(new Color(r,g,b,1f).sub(cln).add(0.2f, 0.2f, 0.2f, (n*n*n)/1f*alpha));
                pix.drawPixel(x, y);
                pix.setBlending(Pixmap.Blending.None);
            }
        }
        return pix;
    }

    public Pixmap generatePixmapNebula() {
        return generatePixmapNebula(new Pixmap(this.width, this.height, Pixmap.Format.RGBA8888));
    }

    public Texture generateTextureNebula() {
        return new Texture(generatePixmapNebula());
    }

    public Pixmap generatePixmapNebulaBlendedWithAPixmap(Pixmap source) {
        return Blend(source, generatePixmapNebula());
    }

    public Texture generateTextureNebulaBlendedWithAPixmap(Pixmap source) {
        return new Texture(generatePixmapNebulaBlendedWithAPixmap(source));
    }

    private Pixmap Blend(Pixmap source, Pixmap destination) {
        Pixmap blended = new Pixmap(this.width, this.height, Pixmap.Format.RGBA8888);
        for (int x = 0; x < this.width+1; x++) {
            for (int y = 0; y < this.height+1; y++) {
                Color s = new Color(source.getPixel(x, y));
                Color n = new Color(destination.getPixel(x, y));

                float sR = n.r, sG = n.g, sB = n.b, sA = n.a,
                        dR = s.r, dG = s.g, dB = s.b, dA = s.a;

                //E= α*S + (1-α)*D
                // sA*sR + (1-sA)*dR
                // (sR*sA+ dR*dA*(1-sA))/(sA+dA*(1-sA)) //a=sA+dA*(1-sA)
                // Math.pow(Math.pow(sR, gamma)*sA+Math.pow(dR, gamma)*(1-sA), (1/gamma))

                blended.setColor(sA*sR + (1-sA)*dR, sA*sG + (1-sA)*dG, sA*sB + (1-sA)*dB, sA+dA*(1-sA));
                blended.drawPixel(x, y);
            }
        }
        return blended;
    }

    public Pixmap generatePixmapNebulaBlendedWithAPixmapGammaCorrection(Pixmap source) {
        return BlendGammaCorrection(source, generatePixmapNebula());
    }

    public Texture generateTextureNebulaBlendedWithAPixmapGammaCorrection(Pixmap source) {
        return new Texture(generatePixmapNebulaBlendedWithAPixmapGammaCorrection(source));
    }

    private Pixmap BlendGammaCorrection(Pixmap source, Pixmap destination) {
        Pixmap blended = new Pixmap(this.width, this.height, Pixmap.Format.RGBA8888);
        for (int x = 0; x < this.width+1; x++) {
            for (int y = 0; y < this.height+1; y++) {
                Color s = new Color(source.getPixel(x, y));
                Color n = new Color(destination.getPixel(x, y));

                float sR = n.r, sG = n.g, sB = n.b, sA = n.a,
                        dR = s.r, dG = s.g, dB = s.b, dA = s.a;

                //E= α*S + (1-α)*D
                // sA*sR + (1-sA)*dR
                // (sR*sA+ dR*dA*(1-sA))/(sA+dA*(1-sA)) //a=sA+dA*(1-sA)
                // Math.pow(Math.pow(sR, gamma)*sA+Math.pow(dR, gamma)*(1-sA), (1/gamma))

                blended.setColor((float) Math.pow(Math.pow(sR, gamma)*sA+Math.pow(dR, gamma)*(1-sA), (1/gamma)), (float) Math.pow(Math.pow(sG, gamma)*sA+Math.pow(dG, gamma)*(1-sA), (1/gamma)), (float) Math.pow(Math.pow(sB, gamma)*sA+Math.pow(dB, gamma)*(1-sA), (1/gamma)), 1);
                blended.drawPixel(x, y);
            }
        }
        return blended;
    }


    public enum NoiseType {DEFAULT, VALUE, WHITE_NOISE, FOAM_NOISE};
}
