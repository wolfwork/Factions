package com.massivecraft.factions.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.massivecraft.factions.entity.UPlayer;

/**
 * This event is fired when PVP is disallowed between players due to any rules in Factions.
 * Canceling this event allows the PVP in spite of this and stops text messages from being sent.
 * 
 * Note that the defender field always is set but the attacker can be null.
 * Some other plugins seem to fire EntityDamageByEntityEvent without an attacker.
 */
public class FactionsEventPvpDisallowed extends FactionsEventAbstract
{
	// -------------------------------------------- //
	// REQUIRED EVENT CODE
	// -------------------------------------------- //
	
	private static final HandlerList handlers = new HandlerList();
	@Override public HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final Player attacker;
	public Player getAttacker() { return this.attacker; }
	public UPlayer getUAttacker() { return this.attacker == null ? null : UPlayer.get(this.attacker); }
	
	private final Player defender;
	public Player getDefender() { return this.defender; }
	public UPlayer getUDefender() { return this.defender == null ? null : UPlayer.get(this.defender); }
	
	private final EntityDamageByEntityEvent event;
	public EntityDamageByEntityEvent getEvent() { return this.event; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public FactionsEventPvpDisallowed(Player attacker, Player defender, EntityDamageByEntityEvent event)
	{
		this.attacker = attacker;
		this.defender = defender;
		this.event = event;
	}

}
