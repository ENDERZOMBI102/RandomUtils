package com.enderzombi102.randomutils;

import org.bukkit.Bukkit;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class LogHelper {
	
	private static final Logger logger = LogManager.getLogManager().getLogger("RandomUtils");
	
	public static void Info(String txt) {
		LogHelper.logger.info(txt);
	}
	
	public static void Warn(String txt) {
		LogHelper.logger.warning(txt);
	}
	
	public static void Error(String txt) {
		LogHelper.logger.severe(txt);
	}
}
