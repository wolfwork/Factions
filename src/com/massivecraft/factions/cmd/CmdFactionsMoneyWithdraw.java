package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
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


public class CmdFactionsMoneyWithdraw extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsMoneyWithdraw()
	{
		// Aliases
		this.addAliases("w", "withdraw");

		// Args
		this.addRequiredArg("amount");
		this.addOptionalArg("faction", "you");

		// Requirements
		this.addRequirements(ReqHasPerm.get(Perm.MONEY_WITHDRAW.node));
		this.addRequirements(ReqBankCommandsEnabled.get());
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		Double amount = this.arg(0, ARDouble.get());
		Faction from = this.arg(1, ARFaction.get(), msenderFaction);
		
		MPlayer to = msender;
		
		boolean success = Econ.transferMoney(msender, from, to, amount);

		if (success && MConf.get().logMoneyTransactions)
		{
			Factions.get().log(ChatColor.stripColor(Txt.parse("%s withdrew %s from the faction bank: %s", msender.getName(), Money.format(amount), from.describeTo(null))));
		}
	}
	
}
