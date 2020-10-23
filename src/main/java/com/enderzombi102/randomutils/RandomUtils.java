package com.enderzombi102.randomutils;

import com.enderzombi102.randomutils.commands.CommandVeinMiner;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomUtils extends JavaPlugin {

	public static RandomUtils instance;

	@Override
	public void onEnable() {
		instance = this;
		LogHelper.Info("Loading RandomUtils!");
		this.getCommand("veinminer").setExecutor( new CommandVeinMiner() );
		LogHelper.Info("Loaded RandomUtils!");
	}

	@Override
	public void onDisable() {
		
	}
}
