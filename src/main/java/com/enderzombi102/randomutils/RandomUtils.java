package com.enderzombi102.randomutils;

import org.bukkit.plugin.java.JavaPlugin;

public final class RandomUtils extends JavaPlugin {

	public static RandomUtils instance;

	@Override
	public void onEnable() {
		instance = this;

	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
}
