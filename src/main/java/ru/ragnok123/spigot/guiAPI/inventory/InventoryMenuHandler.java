package ru.ragnok123.spigot.guiAPI.inventory;

import lombok.NonNull;

import java.util.*;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryMenuHandler implements Listener{
	
	public static HashMap<String, InventoryMenu> menus = new HashMap<String, InventoryMenu>();
	public static HashMap<UUID, InventoryMenu> pmenus = new HashMap<UUID, InventoryMenu>();
	
	public static InventoryMenu getMenuById(@NonNull String id) {
		if(menus.containsKey(id)) {
			return menus.get(id);
		}
		return null;
	}
	
	public static InventoryMenu getMenuByPlayer(@NonNull UUID uuid) {
		if(pmenus.containsKey(uuid)) {
			return pmenus.get(uuid);
		}
		return null;
	}
	
	public static void registerNewMenu(String id, InventoryMenu menu) {
		if(!menus.containsKey(id)) {
			menus.put(id,menu);
		}
	}
	
	public static void unregisterMenu(String id) {
		if(menus.containsKey(id)) {
			menus.remove(id);
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		if(getMenuByPlayer(player.getUniqueId()) != null && inv.getHolder() instanceof InventoryMenu) {
			InventoryMenu menu = getMenuByPlayer(player.getUniqueId());
			int slot = event.getSlot();
			if(menu.onlyRead()) {
				event.setCancelled(true);
			}
			if(menu.getCurrentCategory().getItemClick(slot) != null) {
				menu.getCurrentCategory().getItemClick(slot).init(player, menu.getCurrentCategory().getItemData(slot), event);
				menu.getCurrentCategory().getItemClick(slot).onClick(player,event.getCurrentItem());
			}	
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		Inventory inv = event.getInventory();
		if(getMenuByPlayer(player.getUniqueId()) != null) {
			InventoryMenu menu = getMenuByPlayer(player.getUniqueId());
			menu.destroy(player);
		}
	}
	
}
