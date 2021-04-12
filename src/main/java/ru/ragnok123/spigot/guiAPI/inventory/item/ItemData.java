package ru.ragnok123.spigot.guiAPI.inventory.item;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemData {
	
	public String customName = "Item";
	public int count = 0;
	public Material id = Material.AIR;
	public short damage = 0;
	public String[] lores = {};
	
	public ItemData(Material id) {
		this(id,1);
	}
	public ItemData(Material id, int count) {
		this(id,count,(short) 0);
	}
	public ItemData(Material id, int count, short damage) {
		this(id,count,damage,"Name");
	}
	public ItemData(Material id,int count, short damage, String customName) {
		this(id,count,damage,customName, new String[] {});
	}
	public ItemData(Material id, int count, short damage, String customName, String[] lores) {
		this.id = id; 
		this.damage = damage;
		this.count = count;
		this.customName = customName;
		this.lores = lores;
	}
	
	public ItemStack build() {
		ItemStack item = new ItemStack(id, count,damage);
		ItemMeta meta = item.getItemMeta();
		if(!customName.equals("Name")) {
			meta.setDisplayName(customName);
		}
		if(lores.length > 0) {
			meta.setLore(Arrays.asList(lores));
		}
		return item;
	}
	
	public static ItemData fromItem(ItemStack item) {
		String[] lores = (String[]) item.getItemMeta().getLore().toArray();
		return new ItemData(item.getType(), item.getAmount(), item.getDurability(), item.getItemMeta().getDisplayName(), lores);
	}
	

}
