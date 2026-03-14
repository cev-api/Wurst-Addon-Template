package com.example.addon.hacks;

import net.wurstclient.hack.Hack;
import net.wurstclient.settings.CheckboxSetting;
import net.wurstclient.util.ChatUtils;

public final class HackExample extends Hack
{
	private final CheckboxSetting announceToggle = new CheckboxSetting(
		"Announce toggle",
		"Print a chat message when this hack is toggled.",
		true);
	
	public HackExample()
	{
		super("TemplateHack", "An example hack from the Wurst addon template.",
			false);
		setCategory("Template");
		addSetting(announceToggle);
	}
	
	@Override
	protected void onEnable()
	{
		if(announceToggle.isChecked())
			ChatUtils.message("[TemplateHack] Enabled.");
	}
	
	@Override
	protected void onDisable()
	{
		if(announceToggle.isChecked())
			ChatUtils.message("[TemplateHack] Disabled.");
	}
}
