package com.enderzombi102.randomutils.modules.veinminer;

import com.enderzombi102.randomutils.RandomUtils;
import com.enderzombi102.randomutils.modules.ModuleBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class VeinMiner extends ModuleBase {

	public static HashMap<UUID, VeinMinerSettings> settings = new HashMap<>();

	public VeinMiner() {
		listener = new VeinMinerListener();
	}

	private void findConnected(Block block, Player player, VeinMinerSettings settings) {
		if ( settings.getMinedBlocks() > settings.getMaxBlocks() ) return;
		for ( BlockFace face : BlockFace.values()  ) {
			if (! player.isSneaking() ) return;
			// is the limit reached?
			if ( settings.getMinedBlocks() > settings.getMaxBlocks() ) return;
			// save the block
			Block blockFound = block.getRelative(face);
			// is the block the type of what we want?
			if ( block.getType() != blockFound.getType() ) continue;
			// is the block already been checked?
			if ( settings.getBlocksFound().contains( blockFound ) ) continue;
			// add the block to the list
			settings.getBlocksFound().add( blockFound );
			settings.setMinedBlocks( settings.getMinedBlocks() + 1 );
			// RECURSION
			findConnected(block, player, settings);
		}
	}

	private class VeinMinerListener implements Listener {
		@EventHandler
		public void onBlockBreak(BlockBreakEvent evt) {
			Player player = evt.getPlayer();
			if ( player.isSneaking() ) {
				if ( settings.getOrDefault( player.getUniqueId(), new VeinMinerSettings() ).isActive() ) {
					Bukkit.broadcastMessage("DOING");
					Bukkit.broadcastMessage(evt.getBlock().toString());
					findConnected( evt.getBlock(), player, settings.get( player.getUniqueId() ) );
					List<ItemStack> drops = new ArrayList<>();
					for ( Block block : settings.get( player.getUniqueId() ).getBlocksFound() ) {
						drops.addAll( block.getDrops( player.getInventory().getItemInMainHand() ) );
						block.setType(Material.AIR);
					}
					for (ItemStack itemStack : drops) {
						player.getWorld().dropItem( player.getLocation(), itemStack );
					}
					settings.get( player.getUniqueId() ).getBlocksFound().clear();
					settings.get( player.getUniqueId() ).setMinedBlocks(0);
				}
			}
		}
	}
}
