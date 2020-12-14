package com.enderzombi102.randomutils.modules.veinminer;

import com.enderzombi102.randomutils.modules.ModuleBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class VeinMiner extends ModuleBase {

	public static HashMap<UUID, VeinMinerSettings> settings = new HashMap<>();

	public VeinMiner() {
		listener = new VeinMinerListener();
	}

	private void findConnected(Material type, Block block, Player player, VeinMinerSettings settings) {
		if ( settings.getMinedBlocks() > settings.getMaxBlocks() ) return;
		// player sneaking?
		if (! player.isSneaking() ) return;
		// is the limit reached?
		// if ( settings.getMinedBlocks() > settings.getMaxBlocks() ) return;
		// is it the right type?
		if ( block.getType() != type) {
			sendmsg(block.getLocation() + " its not a valid block");
			return;
		}
		// already have it?
		if ( settings.getBlocksFound().contains( block.getLocation() ) ) {
			sendmsg(block.getLocation() + " has been already counted");
			return;
		}
		// save the block
		settings.getBlocksFound().add( block.getLocation() );
		settings.setMinedBlocks( settings.getMinedBlocks() + 1 );
		// cycle in the neighbours
		for ( BlockFace face : BlockFace.values()  ) {
			// RECURSION
			findConnected(type, block.getRelative(face), player, settings);
		}
	}

	private class VeinMinerListener implements Listener {
		@EventHandler
		public void onBlockBreak(BlockBreakEvent evt) {
			Player player = evt.getPlayer();
			if ( player.isSneaking() ) {
				if ( settings.getOrDefault( player.getUniqueId(), new VeinMinerSettings() ).isActive() ) {
					Bukkit.broadcastMessage(evt.getBlock().toString());
					findConnected( evt.getBlock().getType(), evt.getBlock(), player, settings.get( player.getUniqueId() ) );
					List<ItemStack> drops = new ArrayList<>();
					for ( Location block : settings.get( player.getUniqueId() ).getBlocksFound() ) {
						drops.addAll( block.getBlock().getDrops( player.getInventory().getItemInMainHand() ) );

					}
					for ( Location block : settings.get( player.getUniqueId() ).getBlocksFound() ) {
						block.getBlock().setType(Material.AIR);
					}
					for (ItemStack itemStack : drops) {
						player.getWorld().dropItem( player.getLocation(), itemStack );
					}
					settings.get( player.getUniqueId() ).getBlocksFound().clear();
					settings.get( player.getUniqueId() ).setMinedBlocks(0);
				}
				evt.setDropItems(false);
			}
		}
	}


	public static void sendmsg(String txt) {
		Bukkit.broadcastMessage(txt);
	}
}
