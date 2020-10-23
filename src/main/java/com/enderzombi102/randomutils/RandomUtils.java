package com.enderzombi102.randomutils;

import com.enderzombi102.randomutils.commands.CommandTreeCapitator;
import com.enderzombi102.randomutils.commands.CommandVeinMiner;
import com.enderzombi102.randomutils.modules.ModuleBase;
import com.enderzombi102.randomutils.modules.treecapitator.TreeCapitator;
import com.enderzombi102.randomutils.modules.veinminer.VeinMiner;
import com.google.common.collect.Lists;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class RandomUtils extends JavaPlugin {

	public static RandomUtils instance;
	private ArrayList<ModuleBase> modules = new ArrayList<>();

	public RandomUtils() {
		modules.add( new VeinMiner() );
		modules.add( new TreeCapitator() );
	}


	@Override
	public void onEnable() {
		instance = this;
		LogHelper.Info("Loading RandomUtils!");
		this.getCommand("veinminer").setExecutor( new CommandVeinMiner() );
		this.getCommand("treecapitator").setExecutor( new CommandTreeCapitator() );
		this.getCommand("treecapitator").setAliases( CommandTreeCapitator.getAliases() );
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
