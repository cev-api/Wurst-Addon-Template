package com.example.addon;

import com.example.addon.commands.CommandExample;
import com.example.addon.hacks.HackExample;
import com.mojang.logging.LogUtils;
import net.wurstclient.addons.WurstAddon;
import org.slf4j.Logger;

public final class AddonTemplate extends WurstAddon
{
	public static final Logger LOGGER = LogUtils.getLogger();
	
	@Override
	public void onInitialize()
	{
		LOGGER.info("Initializing Wurst Addon Template");
		
		addHack(new HackExample());
		addCommand(new CommandExample());
	}
}
