package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.event.EventFactionsHomeChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;
import com.massivecraft.massivecore.cmd.req.ReqIsPlayer;
import com.massivecraft.massivecore.ps.PS;

public class CmdFactionsSethome extends FactionsCommandHome
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsSethome()
	{
		// Aliases
		this.addAliases("sethome");

		// Args
		this.addOptionalArg("faction", "you");

		// Requirements
		this.addRequirements(ReqHasPerm.get(Perm.SETHOME.node));
		this.addRequirements(ReqIsPlayer.get());
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		Faction faction = this.arg(0, ARFaction.get(), msenderFaction);
		
		PS newHome = PS.valueOf(me.getLocation());
		
		// MPerm
		if ( ! MPerm.getPermSethome().has(msender, faction, true)) return;
		
		// Verify
		if (!msender.isUsingAdminMode() && !faction.isValidHome(newHome))
		{
			msender.msg("<b>Sorry, your faction home can only be set inside your own claimed territory.");
			return;
		}
		
		// Event
		EventFactionsHomeChange event = new EventFactionsHomeChange(sender, faction, newHome);
		event.run();
		if (event.isCancelled()) return;
		newHome = event.getNewHome();

		// Apply
		faction.setHome(newHome);
		
		// Inform
		faction.msg("%s<i> set the home for your faction. You can now use:", msender.describeTo(msenderFaction, true));
		faction.sendMessage(Factions.get().getOuterCmdFactions().cmdFactionsHome.getUseageTemplate());
		if (faction != msenderFaction)
		{
			msender.msg("<i>You have set the home for " + faction.getName(msender) + "<i>.");
		}
	}
	
}
