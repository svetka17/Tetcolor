package com.tetcolor;

public class Assets {
	// public static Pixmap newgame;
	// public static Pixmap logo;
	// public static Pixmap fine;
	// public static Pixmap numb;
	// public static Pixmap buttons;
	public static Sound drop1;
	public static Music kraftwerk_popcorn;

	public static void load(GLGame game) {
		// background = new Texture(game, "background.png");
		// backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);
		// bobHit = new TextureRegion(items, 128, 128, 32, 32);
		// squirrelFly = new Animation(0.2f,
		// new TextureRegion(items, 0, 160, 32, 32),
		// new TextureRegion(items, 32, 160, 32, 32));
		drop1 = game.getAudio().newSound("drop1.ogg");
		kraftwerk_popcorn = game.getAudio().newMusic("kraftwerk_popcorn.ogg");
		kraftwerk_popcorn.setLooping(true);
		kraftwerk_popcorn.setVolume(0.5f);
		if (Settings.soundEnabled)
			kraftwerk_popcorn.play();
	}

	public static void reload() {
		// background.reload();
		if (Settings.soundEnabled)
			kraftwerk_popcorn.play();
	}
	
	public static void playSound(Sound sound) {
		if(Settings.soundEnabled)
		sound.play(1);
		}

}