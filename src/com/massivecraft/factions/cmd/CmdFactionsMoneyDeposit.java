package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.cmd.req.ReqBankCommandsEnabled;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.arg.ARDouble;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;
import com.massivecraft.massivecore.money.Money;
import com.massivecraft.massivecore.util.Txt;

import org.bukkit.ChatColor;

public class CmdFactionsMoneyDeposit extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsMoneyDeposit()
	{
		// Aliases
		this.addAliases("d", "deposit");

		// Args
		this.addRequiredArg("amount");
		this.addOptionalArg("faction", "you");

		// Requirements
		this.addRequirements(ReqHasPerm.get(Perm.MONEY_DEPOSIT.node));
		this.addRequirements(ReqBankCommandsEnabled.get());
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		Double amount = this.arg(0, ARDouble.get());
		
		Faction faction = this.arg(1, ARFaction.get(), msenderFaction);
		
		boolean success = Econ.transferMoney(msender, msender, faction, amount);
		
		if (success && MConf.get().logMoneyTransactions)
		{
			Factions.get().log(ChatColor.stripColor(Txt.parse("%s deposited %s in the faction bank: %s", msender.getName(), Money.format(amount), faction.describeTo(null))));
		}
	}
	
}
