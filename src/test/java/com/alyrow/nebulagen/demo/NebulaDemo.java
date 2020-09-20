package com.alyrow.nebulagen.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.github.alyrow.nebulagenerator.lib.Animated3dNebulaGenerator;
import com.github.alyrow.nebulagenerator.lib.AnimatedTranslationNebulaGenerator;
import com.github.alyrow.nebulagenerator.lib.NebulaGenerator;
import com.github.alyrow.nebulagenerator.lib.NebulasGenerator;

import static com.github.alyrow.nebulagenerator.lib.NebulaGenerator.NoiseType.*;

public class NebulaDemo extends Game {

	private SpriteBatch spriteBatch;
	private Sprite nebula, overlay;
	private Pixmap pixmap, working;
	private NebulasGenerator nebulasGenerator;
	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		Texture blackStarfield = new Texture(Gdx.files.internal("images/starfield2.png"));
		int width = blackStarfield.getWidth();
		int height = blackStarfield.getHeight();
		nebulasGenerator = new NebulasGenerator(width, height);
		// Blue-Green nebula generator
		Color color = new Color().fromHsv(190f, 0.4f, 1f);
		Animated3dNebulaGenerator.Time greenGenerator = new Animated3dNebulaGenerator.Time(
				width,          // Width
				height,         // Height
				DEFAULT,        // Noise type
				12955341,       // Seed
				8,              // Octave
				color,          // Color
				6f             // Alpha
		);
		nebulasGenerator.addGenerator(greenGenerator);
		// Blue-Purple nebula generator
		color = new Color().fromHsv(260f, 0.5f, 0.8f);
		AnimatedTranslationNebulaGenerator orangeGenerator = new AnimatedTranslationNebulaGenerator(
				width,          // Width
				height,         // Height
				FOAM_NOISE,     // Noise type
				78852342,       // Seed
				4,              // Octave
				color,          // Color
				4.5f             // Alpha
				,5, 0
		);
		nebulasGenerator.addGenerator(orangeGenerator);
		// Red-Purple nebula generator
		color = new Color().fromHsv(330f, 0.6f, 1f);
		NebulaGenerator redGenerator = new NebulaGenerator(
				width,          // Width
				height,         // Height
				HONEY_NOISE,    // Noise type
				78912345,       // Seed
				9,              // Octave
				color,          // Color
				3.5f           // Alpha
		);
		nebulasGenerator.addGenerator(redGenerator);
		// Starfield pixmap
		pixmap = new Pixmap(Gdx.files.internal("images/starfield2.png")); 
		working = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
		// Generate
		Texture nebulaTexture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
		nebula = new Sprite(nebulaTexture);
		working.drawPixmap(pixmap, 0, 0);
		nebula.getTexture().draw(nebulasGenerator.generatePixmapNebulasBlendedWithAPixmapGammaCorrection(working), 0, 0);
		nebula.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Overlay
		overlay = new Sprite(new Texture(Gdx.files.internal("images/gradientOverlay.png")));
		overlay.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		overlay.setColor(Color.NAVY.cpy().lerp(Color.CLEAR, 0.75f));
		overlay.flip(false,true);
	}

	@Override
	public void render() {
		working.drawPixmap(pixmap, 0, 0);
		nebula.getTexture().draw(nebulasGenerator.generatePixmapNebulasBlendedWithAPixmap(working), 0, 0);
		spriteBatch.begin();
		nebula.draw(spriteBatch);
		overlay.draw(spriteBatch);
		spriteBatch.end();
	}
	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("NebulaDemo");
		configuration.setWindowedMode(1024, 768);
		new Lwjgl3Application(new NebulaDemo(), configuration);
	}

}
