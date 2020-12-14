package com.enderzombi102.randomutils.modules.tickrunnable;

import com.enderzombi102.randomutils.RandomUtils;
import com.enderzombi102.randomutils.events.TickEvent;
import com.enderzombi102.randomutils.modules.ModuleBase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Ticker extends ModuleBase {

	private TickRunnable runnable = new TickRunnable();
	private BukkitTask task;
	PluginManager manager = Bukkit.getPluginManager();

	@Override
	public void start() {
		if ( task == null ) {
			task = runnable.runTaskTimer(RandomUtils.instance, 1, 0);
		}
	}

	@Override
	public void stop() {
		task.cancel();
	}


	private class TickRunnable extends BukkitRunnable {

		@Override
		public void run() {
			manager.callEvent( new TickEvent() );
		}

	}

}
