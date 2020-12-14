package com.enderzombi102.randomutils.modules.veinminer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.Material;

public class MiningAgent {
	private ItemStack blockStack = null;
	private ItemStack origTool = null; // Original tool the player was holding (must be the same to continue)
	private List<Location> mined = new ArrayList<Location>();
	private List<Location> scheduled = new ArrayList<Location>();
	private final Player player;
	private final Location origin;

	private Block block;
	private int meta;

	private boolean subtypes = true; // Ignore metadata

	public MiningAgent(Player player, Location origin, Block block, int meta)
	{
		this.player = player;
		this.origin = origin;

		this.block = block;
		this.meta = meta;

		if(m_createStack != null)
		{
			try
			{
				blockStack = (ItemStack)m_createStack.invoke(block, meta);
			} catch(Exception e){}
		}

		this.subtypes = blockStack == null? true : !blockStack.getHasSubtypes();

		ItemStack held = player.getHeldItem();
		origTool = held == null? null : held.getItem();

		for(int i = -1; i <= 1; i++)
		{
			for(int j = -1; j <= 1; j++)
			{
				for(int k = -1; k <= 1; k++)
				{
					appendBlock(origin.offset(i, j, k));
				}
			}
		}
	}

	/**
	 * Returns true if the miner is no longer valid or has completed
	 */
	public boolean tickMiner()
	{
		if(origin == null || player == null || !player.isEntityAlive() || mined.size() >= ExcavationSettings.mineLimit)
		{
			return true;
		}

		for(int n = 0; scheduled.size() > 0; n++)
		{
			if(n >= ExcavationSettings.mineSpeed || mined.size() >= ExcavationSettings.mineLimit)
			{
				break;
			}

			ItemStack heldStack = player.getHeldItem();
			Item heldItem = heldStack == null? null : heldStack.getItem();

			if(heldItem != origTool)
			{
				// Original tool has been swapped or broken
				return true;
			} else if(!hasEnergy(player))
			{
				return true;
			}

			Location pos = scheduled.remove(0);

			if(pos == null)
			{
				continue;
			} else if(player.getDistance(pos.getX(), pos.getY(), pos.getZ()) > ExcavationSettings.mineRange)
			{
				mined.add(pos);
				continue;
			}

			Block bloc = player.getWorld().getBlockAt( new Location( player.getWorld(), pos.getX(), pos.getY(), pos.getZ() ) );

			boolean flag = bloc == block && (subtypes || m == meta);

			if(!flag && blockStack != null)
			{
				ItemStack stack = null;

				try
				{
					stack = new ItemStack(bloc, m);
				} catch(Exception e){}

				if(stack != null && stack.getItem() == blockStack.getItem() && stack.getItemDamage() == blockStack.getItemDamage())
				{
					flag = true;
				}
			}

			if(flag)
			{
				if(!(ExcavationSettings.ignoreTools || b.canHarvestBlock(player, m)))
				{
					mined.add(pos);
					continue;
				} else if(player.theItemInWorldManager.tryHarvestBlock(pos.getX(), pos.getY(), pos.getZ()))
				{
					if(player.capabilities.isCreativeMode)
					{
						player.setExhaustion(0.1F);
					}

					for(int i = -1; i <= 1; i++)
					{
						for(int j = -1; j <= 1; j++)
						{
							for(int k = -1; k <= 1; k++)
							{
								appendBlock(pos.clone().add(i, j, k));
							}
						}
					}
				}
				mined.add(pos);
			}
		}

		return scheduled.size() <= 0 || mined.size() >= ExcavationSettings.mineLimit;
	}

	/**
	 * Appends a block position to the miners current pass
	 */
	public void appendBlock(Location pos)
	{
		if( pos == null || mined.contains(pos) || scheduled.contains(pos) ) {
			return;
		} else if( player.getLocation().distance( pos ) > 128 ) {
			return;
		}
		scheduled.add(pos);
	}

	private boolean hasEnergy(Player player) {
		return player.getFoodLevel() > 0;
	}

}