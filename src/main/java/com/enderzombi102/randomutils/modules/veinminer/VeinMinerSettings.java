package com.enderzombi102.randomutils.modules.veinminer;

import com.enderzombi102.randomutils.modules.ModuleSettings;
import lombok.Setter;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

@Setter
@lombok.Getter
public class VeinMinerSettings implements ModuleSettings {

	private boolean active = false;
	private int maxBlocks = 30;
	private int minedBlocks = 0;
	private List<Block> blocksFound = new ArrayList<>();

}
