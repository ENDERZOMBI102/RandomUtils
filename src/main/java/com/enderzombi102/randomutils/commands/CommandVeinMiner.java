package com.enderzombi102.randomutils.commands;

import com.enderzombi102.randomutils.modules.veinminer.VeinMiner;
import com.enderzombi102.randomutils.modules.veinminer.VeinMinerSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandVeinMiner implements TabExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if ( sender instanceof Player) {
			UUID player = ( (Player) sender ).getUniqueId();
			VeinMiner.settings.putIfAbsent( player, new VeinMinerSettings() );
			VeinMinerSettings settings = VeinMiner.settings.get(player);
			settings.setActive(! settings.isActive() );
			VeinMiner.settings.put( player, settings );
		}
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		return new ArrayList<>();
	}
}
