package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.MassiveException;

public abstract class CmdFactionsSetXAll extends CmdFactionsSetX
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsSetXAll(boolean claim)
	{
		// Super
		super(claim);
		
		// Args
		this.addRequiredArg("all|map");
		this.addRequiredArg("faction");
		if (claim)
		{
			this.addRequiredArg("newfaction");
			this.setFactionArgIndex(2);
		}
	}
	
	// -------------------------------------------- //
	// EXTRAS
	// -------------------------------------------- //
	
	public Faction getOldFaction() throws MassiveException
	{
		return this.arg(1, ARFaction.get());
	}
	
}
