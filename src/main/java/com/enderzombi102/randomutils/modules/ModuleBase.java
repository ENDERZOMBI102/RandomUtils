package com.enderzombi102.randomutils.modules;

import com.enderzombi102.randomutils.LogHelper;
import com.enderzombi102.randomutils.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class ModuleBase {

	protected Listener listener;
	
	public void start() {
		Bukkit.getPluginManager().registerEvents( this.listener, RandomUtils.instance );
		LogHelper.Info("Loaded module " + getClass().getSimpleName() + "!");
	}

	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}

}
