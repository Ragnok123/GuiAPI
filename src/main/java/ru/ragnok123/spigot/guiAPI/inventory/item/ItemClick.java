package ru.ragnok123.spigot.guiAPI.inventory.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ItemClick {
	
	private Player player = null;
	private ItemData item = null;
	private InventoryClickEvent event = null;
	
	public void init(Player player, ItemData item, InventoryClickEvent event) {
		this.player = player;
		this.item = item;
		this.event = event;
	}
	
	public abstract void onClick(Player player,ItemStack item);
	
}
