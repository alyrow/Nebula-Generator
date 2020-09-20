# Nebula-Generator
A nebula generator writted with libgdx

Ensure that you have jitpack dependencies source in your `build.gradle`:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Then, add this dependencie:
```gradle
dependencies {
	implementation 'com.github.alyrow:Nebula-Generator:v1.1.0'
}
```

## Basic usages
A generator is defined by:
```java
NebulaGenerator generator = new NebulaGenerator(width, height, noise_type, seed, octave,
                color, alpha, offset);
```
Where `width` and `height` are the size of the nebula, `noise_type`, `seed` and `octave` are settings wich define how the nebula looks. `color` defines the color of the nebula and `alpha` the level of alpha of the nebula. `offset` defines the displacement of the nebula.

After that you can generate the nebula to a pixmap or a texture with:
```java
generator.generatePixmapNebula()
// Or
generator.generateTextureNebula()
```

If you want to combine multiple nebula, use `NebulasGenerator`:
```java
NebulasGenerator nebulasGenerator = new NebulasGenerator(width, height);
nebulasGenerator.addGenerator(generator1);
nebulasGenerator.addGenerator(generator2);
```
Where `width` and `height` are the size of nebulas and `generator1` and `generator2` are `NebulaGenerator`.

After that you can generate nebulas to a pixmap or a texture with:
```java
nebulasGenerator.generatePixmapNebulas()
// Or
nebulasGenerator.generateTextureNebulas()
```

You can also combine your nebula with another pixmap for exemple with stars, with or without gamma correction:
#### With `NebulaGenerator`:
##### Without gamma correction:
```java
generator.generatePixmapNebulaBlendedWithAPixmap(your_pixmap_ex_stars);
// Or
generator.generateTextureNebulaBlendedWithAPixmap(your_pixmap_ex_stars);
```
##### With gamma correction:
```java
generator.generatePixmapNebulaBlendedWithAPixmapGammaCorrection(your_pixmap_ex_stars);
// Or
generator.generateTextureNebulaBlendedWithAPixmapGammaCorrection(your_pixmap_ex_stars);
```
#### With `NebulasGenerator`:
##### Without gamma correction:
```java
nebulasGenerator.generatePixmapNebulasBlendedWithAPixmap(your_pixmap_ex_stars);
// Or
nebulasGenerator.generateTextureNebulasBlendedWithAPixmap(your_pixmap_ex_stars);
```
##### With gamma correction:
```java
nebulasGenerator.generatePixmapNebulasBlendedWithAPixmapGammaCorrection(your_pixmap_ex_stars);
// Or
nebulasGenerator.generateTextureNebulasBlendedWithAPixmapGammaCorrection(your_pixmap_ex_stars);
```

## Screenshots
#### Multiple nebulas blended with a star background:
##### Without gamma correction and alpha = 1
```java
NebulaGenerator generator1 = new NebulaGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), DEFAULT, 12955341, 8,
                new Color(0.1f,0.1f,1f,1), 1f, new Vector2(0,180));

NebulaGenerator generator2 = new NebulaGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), HONEY_NOISE, 78912345, 9,
                new Color(0.8f,0.4f,0.6f,1), 1f, new Vector2(0,0));

NebulasGenerator nebulasGenerator = new NebulasGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
nebulasGenerator.addGenerator(generator1);
nebulasGenerator.addGenerator(generator2);

Pixmap nebula = nebulasGenerator.generatePixmapNebulasBlendedWithAPixmap(new Pixmap(Gdx.files.absolute("path to stars background")));
````
![](https://github.com/alyrow/Nebula-Generator/raw/master/screenshots/Without%20gamma%20correction%20and%20alpha%20%3D%201.png)
##### Without gamma correction and alpha = 3
```java
NebulaGenerator generator1 = new NebulaGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), DEFAULT, 12955341, 8,
                new Color(0.1f,0.1f,1f,1), 3f, new Vector2(0,180));

NebulaGenerator generator2 = new NebulaGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), HONEY_NOISE, 78912345, 9,
                new Color(0.8f,0.4f,0.6f,1), 3f, new Vector2(0,0));

NebulasGenerator nebulasGenerator = new NebulasGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
nebulasGenerator.addGenerator(generator1);
nebulasGenerator.addGenerator(generator2);

Pixmap nebula = nebulasGenerator.generatePixmapNebulasBlendedWithAPixmap(new Pixmap(Gdx.files.absolute("path to stars background")));
````
![](https://github.com/alyrow/Nebula-Generator/raw/master/screenshots/Without%20gamma%20correction%20and%20alpha%20%3D%203.png)
##### With gamma correction and alpha = 1
```java
NebulaGenerator generator1 = new NebulaGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), DEFAULT, 12955341, 8,
                new Color(0.1f,0.1f,1f,1), 1f, new Vector2(0,180));

NebulaGenerator generator2 = new NebulaGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), HONEY_NOISE, 78912345, 9,
                new Color(0.8f,0.4f,0.6f,1), 1f, new Vector2(0,0));

NebulasGenerator nebulasGenerator = new NebulasGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
nebulasGenerator.addGenerator(generator1);
nebulasGenerator.addGenerator(generator2);

Pixmap nebula = nebulasGenerator.generatePixmapNebulasBlendedWithAPixmapGammaCorrection(new Pixmap(Gdx.files.absolute("path to stars background")));
````
![](https://github.com/alyrow/Nebula-Generator/raw/master/screenshots/With%20gamma%20correction%20and%20alpha%20%3D%201.png)
##### With gamma correction and alpha = 3
```java
NebulaGenerator generator1 = new NebulaGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), DEFAULT, 12955341, 8,
                new Color(0.1f,0.1f,1f,1), 3f, new Vector2(0,180));

NebulaGenerator generator2 = new NebulaGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), HONEY_NOISE, 78912345, 9,
                new Color(0.8f,0.4f,0.6f,1), 3f, new Vector2(0,0));

NebulasGenerator nebulasGenerator = new NebulasGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
nebulasGenerator.addGenerator(generator1);
nebulasGenerator.addGenerator(generator2);

Pixmap nebula = nebulasGenerator.generatePixmapNebulasBlendedWithAPixmapGammaCorrection(new Pixmap(Gdx.files.absolute("path to stars background")));
````
![](https://github.com/alyrow/Nebula-Generator/raw/master/screenshots/With%20gamma%20correction%20and%20alpha%20%3D%203.png)
#### Fog:
Yes nebula can be used for other things than nebulas!
Not perfect but a good start.
```java
NebulaGenerator generator1 = new NebulaGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), DEFAULT, 12955341, 8,
                new Color(0.8f,0.8f,0.8f,1), 1f, new Vector2(0,-380));

NebulaGenerator generator2 = new NebulaGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), HONEY_NOISE, 78912345, 9,
                new Color(0.8f,0.8f,0.8f,1), 1f, new Vector2(0,-1000));

NebulasGenerator nebulasGenerator = new NebulasGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
nebulasGenerator.addGenerator(generator1);
nebulasGenerator.addGenerator(generator2);

Pixmap nebula = nebulasGenerator.generatePixmapNebulasBlendedWithAPixmap(new Pixmap(Gdx.files.absolute("path to forest.png")));
```
##### Orgininal image:
![](https://github.com/alyrow/Nebula-Generator/raw/master/screenshots/forest.png)
##### With fog:
![](https://github.com/alyrow/Nebula-Generator/raw/master/screenshots/fog.png)
