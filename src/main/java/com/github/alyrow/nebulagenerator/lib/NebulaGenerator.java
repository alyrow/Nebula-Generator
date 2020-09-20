package com.github.alyrow.nebulagenerator.lib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import squidpony.squidmath.FastNoise;
import squidpony.squidmath.FlawedPointHash;

/** Main class of the lib. Most of the work is done here
 * @author alyrow */
public class NebulaGenerator {
    public static float gamma = 2.2f;

    int seed = 10000;
    int width, height;
    Color color;
    Vector2 offset;
    int octave = 6;
    float alpha = 1;
    FastNoise generator;
    //long startTime;

    public NebulaGenerator(int width, int height, NoiseType type, long seed, int octave, Color color, float alpha, Vector2 offset) {
        this.width = width;
        this.height = height;
        this.seed = (int)(seed ^ seed >>> 32);
        this.octave = Math.max(1, octave);
        if (type == NoiseType.DEFAULT) generator = new FastNoise(this.seed, 1 / 750f, FastNoise.SIMPLEX_FRACTAL, this.octave);
        else if (type == NoiseType.VALUE) generator = new FastNoise(this.seed, 2 / 750f, FastNoise.VALUE_FRACTAL, this.octave);
        else if (type == NoiseType.WHITE_NOISE) generator = new FastNoise(this.seed, 1, FastNoise.WHITE_NOISE);
        else if(type == NoiseType.FOAM_NOISE) generator = new FastNoise(this.seed, 2.5f / 750f, FastNoise.FOAM_FRACTAL, this.octave);
        else if(type == NoiseType.PERLIN_NOISE) generator = new FastNoise(this.seed, 1 / 750f, FastNoise.PERLIN_FRACTAL, this.octave);
        else if(type == NoiseType.HONEY_NOISE) generator = new FastNoise(this.seed, 1 / 750f, FastNoise.HONEY_FRACTAL, this.octave);
        else if(type == NoiseType.GLITCH_NOISE){
            generator = new FastNoise(this.seed, 1 / 750f, FastNoise.CUBIC_FRACTAL, this.octave);
            generator.setPointHash(new FlawedPointHash.CubeHash(seed, Math.max(width, height)));
        }
        this.color = color;
        this.alpha = alpha;
        this.offset = offset;
        //startTime = TimeUtils.millis();
    }



    public Pixmap generatePixmapNebula(Pixmap pix) {
        pix.setBlending(Pixmap.Blending.SourceOver);
        Color cln = new Color(1, 1, 1, 0).sub(color), temp = new Color();
        //float time = TimeUtils.timeSinceMillis(startTime) * 0.01f;
        for (int x=0; x<this.width; x++) {
            for (int y=0; y<this.height; y++) {
//                float n = this.generator.getConfiguredNoise(x, y);
                float n = this.generator.getConfiguredNoise(x+offset.x, y+offset.y/*, time*/);
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
        Color s = new Color(), n = new Color();
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                s.set(source.getPixel(x, y));
                n.set(destination.getPixel(x, y));

                float sR = n.r, sG = n.g, sB = n.b, sA = n.a,
                        dR = s.r, dG = s.g, dB = s.b, dA = s.a;

                //E= α*S + (1-α)*D
                // sA*sR + (1-sA)*dR
                // (sR*sA+ dR*dA*(1-sA))/(sA+dA*(1-sA)) //a=sA+dA*(1-sA)
                // Math.pow(Math.pow(sR, gamma)*sA+Math.pow(dR, gamma)*(1-sA), (1/gamma))

                destination.setColor(sA*sR + (1-sA)*dR, sA*sG + (1-sA)*dG, sA*sB + (1-sA)*dB, sA+dA*(1-sA));
                destination.drawPixel(x, y);
            }
        }
        return destination;
    }

    public Pixmap generatePixmapNebulaBlendedWithAPixmapGammaCorrection(Pixmap source) {
        return BlendGammaCorrection(source, generatePixmapNebula());
    }

    public Texture generateTextureNebulaBlendedWithAPixmapGammaCorrection(Pixmap source) {
        return new Texture(generatePixmapNebulaBlendedWithAPixmapGammaCorrection(source));
    }

    private Pixmap BlendGammaCorrection(Pixmap source, Pixmap destination) {
        Color s = new Color(), n = new Color();
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                s.set(source.getPixel(x, y));
                n.set(destination.getPixel(x, y));

                float sR = n.r, sG = n.g, sB = n.b, sA = n.a,
                        dR = s.r, dG = s.g, dB = s.b, dA = s.a;

                //E= α*S + (1-α)*D
                // sA*sR + (1-sA)*dR
                // (sR*sA+ dR*dA*(1-sA))/(sA+dA*(1-sA)) //a=sA+dA*(1-sA)
                // Math.pow(Math.pow(sR, gamma)*sA+Math.pow(dR, gamma)*(1-sA), (1/gamma))

                destination.setColor((float) Math.pow(Math.pow(sR, gamma)*sA+Math.pow(dR, gamma)*(1-sA), (1/gamma)), (float) Math.pow(Math.pow(sG, gamma)*sA+Math.pow(dG, gamma)*(1-sA), (1/gamma)), (float) Math.pow(Math.pow(sB, gamma)*sA+Math.pow(dB, gamma)*(1-sA), (1/gamma)), 1);
                destination.drawPixel(x, y);
            }
        }
        return destination;
    }


    public enum NoiseType {DEFAULT, VALUE, WHITE_NOISE, FOAM_NOISE, PERLIN_NOISE, HONEY_NOISE, GLITCH_NOISE};
}
