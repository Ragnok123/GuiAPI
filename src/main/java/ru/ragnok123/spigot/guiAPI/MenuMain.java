package ru.ragnok123.spigot.guiAPI;

import org.bukkit.plugin.java.JavaPlugin;

import ru.ragnok123.spigot.guiAPI.inventory.InventoryMenuHandler;

public class MenuMain extends JavaPlugin{
	
	public void onEnable() {
		InventoryMenuHandler handler = new InventoryMenuHandler();
		getServer().getPluginManager().registerEvents(handler,this);
	}
	
}
