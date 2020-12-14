package com.enderzombi102.randomutils.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class ItemUtils {

	public static int getDurability(ItemStack item) {
		return ( (Damageable) item ).getDamage();
	}

	public static void setDurability(ItemStack item, int damage) {
		( (Damageable) item ).setDamage(damage);
	}


}
