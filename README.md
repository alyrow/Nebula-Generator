# Nebula-Generator
A nebula generator writted with libgdx

## Basic usages
A generator is defined by:
```java
NebulaGenerator generator = new NebulaGenerator(width, height, noise_type, seed, octave,
                color, alpha, offset_max, offset);
```
Where `width` and `height` are the size of the nebula, `noise_type`, `seed` and `octave` are settings wich define how the nebula looks. `color` define the color of the nebula and `alpha` the level of alpha of the nebula. `offset_max` define the max displacement possible of the nebula and `offset` the displacement of the nebula.

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
