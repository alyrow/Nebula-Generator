package com.github.alyrow.nebulagenerator.lib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 * Class for generating multiple nebula in one nebula
 * @author alyrow
 */
public class NebulasGenerator {
    public static float gamma = 2.2f;

    Pixmap pix;
    Array<NebulaGenerator> generators;
    int width, height;

    public NebulasGenerator(int width, int height) {
        pix = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        generators = new Array<>();
        this.width = width;
        this.height = height;
    }

    public void addGenerator(NebulaGenerator generator) {generators.add(generator);}
    public void removeGenerator(NebulaGenerator generator) {generators.removeValue(generator, true);}

    public Pixmap generatePixmapNebulas() {
        pix.setColor(0,0,0,0);
        pix.fill();
        for (NebulaGenerator generator : generators) {
            generator.generatePixmapNebula(pix);
        }
        return pix;
    }

    public Texture generateTextureNebulas() {
        return new Texture(generatePixmapNebulas());
    }

    public Pixmap generatePixmapNebulasBlendedWithAPixmap(Pixmap source) {
        return Blend(source, generatePixmapNebulas());
    }

    public Texture generateTextureNebulasBlendedWithAPixmap(Pixmap source) {
        return new Texture(generatePixmapNebulasBlendedWithAPixmap(source));
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

    public Pixmap generatePixmapNebulasBlendedWithAPixmapGammaCorrection(Pixmap source) {
        return BlendGammaCorrection(source, generatePixmapNebulas());
    }

    public Texture generateTextureNebulasBlendedWithAPixmapGammaCorrection(Pixmap source) {
        return new Texture(generatePixmapNebulasBlendedWithAPixmapGammaCorrection(source));
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
}
