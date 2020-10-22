package com.enderzombi102.randomutils.modules.veinminer;

import com.enderzombi102.randomutils.modules.ModuleSettings;
import lombok.Setter;

@Setter
@lombok.Getter
public class VeinMinerSettings implements ModuleSettings {

	private boolean active = false;
	private int maxBlocks = 30;

}
