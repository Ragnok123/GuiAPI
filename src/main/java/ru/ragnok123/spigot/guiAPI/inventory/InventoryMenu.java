package ru.ragnok123.spigot.guiAPI.inventory;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import lombok.NonNull;
import ru.ragnok123.spigot.guiAPI.inventory.item.ItemData;

public class InventoryMenu implements InventoryHolder{

	private InventoryCategory category = null;
	private HashMap<String, InventoryCategory> categories = new HashMap<String, InventoryCategory>();
	private InventoryCategory currentCategory = null;
	private HashMap<UUID,Inventory> inventories = new HashMap<UUID,Inventory>();
	private String name = "Menu";
	private boolean read = true;
	private int defaultRowCount = 3;
	
	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }
	
	public void setRowSize(int count) {
		this.defaultRowCount = count;
	}
	
	public int getRowCount() {
		return this.defaultRowCount;
	}
	
	public Inventory getInventory(UUID uuid) {
		if(inventories.containsKey(uuid)) {
			return inventories.get(uuid);
		}
		return null;
	}
	
	public void show(@NonNull Player player) {
		Inventory inv = createInventory();
		player.openInventory(inv);
		inventories.put(player.getUniqueId(), inv);
		InventoryMenuHandler.pmenus.put(player.getUniqueId(),this);
		openMainCategory(player);
	}
	
	private Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(this, getRowCount() * 9, getName());
		return inventory;
	}
	

	
	public void destroy(@NonNull Player player) {
		inventories.remove(player.getUniqueId());
		InventoryMenuHandler.pmenus.remove(player.getUniqueId());
	}
	
	
	public void setMainCategory(@NonNull InventoryCategory category) {
		category.menu = this;
		this.category = category;
	}
	
	public InventoryCategory getMainCategory() {
		return this.category;
	}
	
	public void addCategory(@NonNull String id, @NonNull InventoryCategory category) {
		if(categories.containsKey(id)) {
			return;
		}
		category.menu = this;
		categories.put(id, category);
	}
	
	public void removeCategory(@NonNull String id) {
		if(!categories.containsKey(id)) {
			return;
		}
		categories.remove(id);
	}
	
	public InventoryCategory getCategory(@NonNull String id) {
		if(categories.containsKey(id)) {
			return categories.get(id);
		}
		return null;
	}
	
	public InventoryCategory getCurrentCategory() {
		return this.currentCategory;
	}
	
	public void openMainCategory(Player player) {
		InventoryCategory category = this.category;
		Inventory inventory = inventories.get(player.getUniqueId());
		inventory.clear();
		for(Map.Entry<Integer,ItemData> entry : category.itemDataMap().entrySet()) {
			int position = entry.getKey();
			ItemData data = entry.getValue();
			inventory.setItem(position, data.build());
		}
		this.currentCategory = category;
	}
	
	public void openCategory(@NonNull String id, Player player) {
		if(categories.containsKey(id)) {
			InventoryCategory category = categories.get(id);
			Inventory inventory = inventories.get(player.getUniqueId());
			inventory.clear();
			for(Map.Entry<Integer,ItemData> entry : category.itemDataMap().entrySet()) {
				int position = entry.getKey();
				ItemData data = entry.getValue();
				inventory.setItem(position, data.build());
			}
			this.currentCategory = category;
		}
	}
	
	public boolean onlyRead() {
		return this.read;
	}
	
	public void setOnlyRead(boolean value) {
		this.read = value;
	}
	@Override
	public Inventory getInventory() {
		return null;
	}
	
}
