package com.enderzombi102.randomutils.modules.treecapitator;

import com.enderzombi102.randomutils.LogHelper;
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

public class TreeCapitator extends ModuleBase {

	public static HashMap<UUID, TreeCapitatorSettings> settings = new HashMap<>();

	public TreeCapitator() {
		listener = new TreeCapitatorListener();
	}

	private void breakConnected(Block block, List<Material> types, ItemStack tool) {
		for ( BlockFace face : BlockFace.values() ) {
			Block blockFound = block.getRelative(face);
			if ( types.contains( blockFound.getType() ) ) {
				Bukkit.broadcastMessage("BREAK");
				breakConnected(blockFound, types, tool);
				block.breakNaturally(tool);
			}
			Bukkit.broadcastMessage("DONE");
		}
	}

	private List<Material> getBlockTypes(Block block) {
		String type = block.getType().getKey().getKey()
				.replace("_log", "")
				.replace("_stem", "")
				.replace("_leaves", "");
		List<Material> types = new ArrayList<>();
		switch (type) {
			case "oak":
				types.add(Material.OAK_LOG);
				types.add(Material.OAK_LEAVES);
				break;
			case "dark_oak":
				types.add(Material.DARK_OAK_LOG);
				types.add(Material.DARK_OAK_LEAVES);
				break;
			case "spruce":
				types.add(Material.SPRUCE_LOG);
				types.add(Material.SPRUCE_LEAVES);
				break;
			case "birch":
				types.add(Material.BIRCH_LOG);
				types.add(Material.BIRCH_LEAVES);
				break;
			case "jungle":
				types.add(Material.JUNGLE_LOG);
				types.add(Material.JUNGLE_LEAVES);
				break;
			case "acacia":
				types.add(Material.ACACIA_LOG);
				types.add(Material.ACACIA_LEAVES);
				break;
			case "crimson":
				types.add(Material.CRIMSON_STEM);
				types.add(Material.NETHER_WART_BLOCK);
				break;
			case "warped":
				types.add(Material.WARPED_STEM);
				types.add(Material.WARPED_WART_BLOCK);
				break;
			default:
				break;
		}
		return types;
	}

	private class TreeCapitatorListener implements Listener {
		@EventHandler
		public void onBlockBreak(BlockBreakEvent evt) {
			Player player = evt.getPlayer();
			if ( player.isSneaking() ) {
				if ( settings.getOrDefault( player.getUniqueId(), new TreeCapitatorSettings() ).isActive() ) {
					List<Material> types = getBlockTypes( evt.getBlock() );
					breakConnected( evt.getBlock(), types, player.getInventory().getItemInMainHand() );
				}
			}
		}
	}
}
