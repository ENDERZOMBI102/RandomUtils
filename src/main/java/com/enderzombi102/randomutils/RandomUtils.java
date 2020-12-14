package com.enderzombi102.randomutils;

import com.enderzombi102.randomutils.modules.ModuleBase;
import com.enderzombi102.randomutils.modules.tickrunnable.Ticker;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class RandomUtils extends JavaPlugin {

	public static RandomUtils instance;
	private final ArrayList<ModuleBase> modules = new ArrayList<>();

	public RandomUtils() {
//		modules.add( new VeinMiner() );
//		modules.add( new TreeCapitator() );
		modules.add( new Ticker() );
	}


	@Override
	public void onEnable() {
		instance = this;
		LogHelper.Info("Loading RandomUtils " + getDescription().getVersion() + "!");
//		this.getCommand("veinminer").setExecutor( new CommandVeinMiner() );
//		this.getCommand("treecapitator").setExecutor( new CommandTreeCapitator() );
//		this.getCommand("treecapitator").setAliases( CommandTreeCapitator.getAliases() );
		for (ModuleBase module : this.modules) {
			module.start();
		}
		LogHelper.Info("Loaded RandomUtils!");
	}

	@Override
	public void onDisable() {
		for (ModuleBase module : this.modules) {
			module.stop();
		}
	}
}
