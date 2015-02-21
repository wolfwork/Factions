package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.arg.ARMPlayer;
import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.cmd.req.ReqBankCommandsEnabled;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.arg.ARDouble;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;
import com.massivecraft.massivecore.money.Money;
import com.massivecraft.massivecore.util.Txt;

import org.bukkit.ChatColor;


public class CmdFactionsMoneyTransferPf extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsMoneyTransferPf()
	{
		// Aliases
		this.addAliases("pf");

		// Args
		this.addRequiredArg("amount");
		this.addRequiredArg("player");
		this.addRequiredArg("faction");

		// Requirements
		this.addRequirements(ReqHasPerm.get(Perm.MONEY_P2F.node));
		this.addRequirements(ReqBankCommandsEnabled.get());
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		Double amount = this.arg(0, ARDouble.get());
		MPlayer from = this.arg(1, ARMPlayer.getAny());
		Faction to = this.arg(2, ARFaction.get());
		
		boolean success = Econ.transferMoney(msender, from, to, amount);

		if (success && MConf.get().logMoneyTransactions)
		{
			Factions.get().log(ChatColor.stripColor(Txt.parse("%s transferred %s from the player \"%s\" to the faction \"%s\"", msender.getName(), Money.format(amount), from.describeTo(null), to.describeTo(null))));
		}
	}
	
}
