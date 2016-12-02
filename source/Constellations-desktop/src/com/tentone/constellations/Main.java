package com.tentone.constellations;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main
{
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		cfg.title = "Constellations";
		cfg.width = 1024;
		cfg.height = 600;
		
		new LwjglApplication(new ConstellationsMain(), cfg);
	}
}