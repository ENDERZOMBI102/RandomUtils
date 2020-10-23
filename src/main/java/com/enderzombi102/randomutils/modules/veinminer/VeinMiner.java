package com.enderzombi102.randomutils.modules.veinminer;

import com.enderzombi102.randomutils.modules.ModuleBase;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class VeinMiner extends ModuleBase {

	public static HashMap<UUID, VeinMinerSettings> settings = new HashMap<>();
	private final VeinMinerListener listener;

	public VeinMiner() {
		this.listener = new VeinMinerListener();
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
	}

	private void breakConnected(Block block, ItemStack tool) {
		for ( BlockFace face : BlockFace.values() ) {
			Block blockFound = block.getRelative(face);
			if ( blockFound.getType() == block.getType() ) {
				breakConnected(blockFound, tool);
				block.breakNaturally(tool);
			}
		}
	}

	private class VeinMinerListener implements Listener {
		@EventHandler
		public void onBlockBreak(BlockBreakEvent evt) {
			Player player = evt.getPlayer();
			if ( player.isSneaking() ) {
				if ( settings.getOrDefault( player.getUniqueId(), new VeinMinerSettings() ).isActive() ) {
					breakConnected( evt.getBlock(), player.getInventory().getItemInMainHand() );
				}
			}
		}
	}
}
