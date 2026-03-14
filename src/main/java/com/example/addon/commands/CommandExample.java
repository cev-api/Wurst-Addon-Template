package com.example.addon.commands;

import net.wurstclient.command.CmdException;
import net.wurstclient.command.Command;
import net.wurstclient.util.ChatUtils;

public final class CommandExample extends Command
{
	public CommandExample()
	{
		super("example",
			"Example addon command.\nPrints a test message.",
			".example [name]");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length == 0)
		{
			ChatUtils.message("Hello from your Wurst addon.");
			return;
		}
		
		ChatUtils.message("Hello, " + String.join(" ", args) + "!");
	}
}
