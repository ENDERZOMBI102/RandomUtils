package com.enderzombi102.randomutils.commands;

import com.enderzombi102.randomutils.modules.treecapitator.TreeCapitator;
import com.enderzombi102.randomutils.modules.treecapitator.TreeCapitatorSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandTreeCapitator implements TabExecutor {

	public static List<String> getAliases() {
		ArrayList<String> aliases = new ArrayList<>();
		aliases.add("tc");
		return aliases;
	}


	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if ( sender instanceof Player) {
			UUID player = ( (Player) sender ).getUniqueId();
			TreeCapitator.settings.putIfAbsent( player, new TreeCapitatorSettings() );
			TreeCapitatorSettings settings = TreeCapitator.settings.get(player);
			settings.setActive(! settings.isActive() );
			TreeCapitator.settings.put( player, settings );
		}
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		return new ArrayList<>();
	}
}
