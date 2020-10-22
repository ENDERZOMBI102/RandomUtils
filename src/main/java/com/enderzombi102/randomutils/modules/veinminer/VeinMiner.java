package com.enderzombi102.randomutils.modules.veinminer;

import com.enderzombi102.randomutils.modules.ModuleBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.UUID;

public class VeinMiner extends ModuleBase {

	private HashMap<UUID, VeinMinerSettings> settings = new HashMap<>();
	private VeinMinerListener listener;

	public VeinMiner() {
		this.listener = new VeinMinerListener();
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}

	private class VeinMinerListener implements Listener {
		@EventHandler
		public void onBlockBreak(BlockBreakEvent evt) {

		}
	}
}
