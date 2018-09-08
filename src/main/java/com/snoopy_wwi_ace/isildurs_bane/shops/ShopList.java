package com.snoopy_wwi_ace.isildurs_bane.shops;

import java.util.ArrayList;
import java.util.UUID;

import com.snoopy_wwi_ace.isildurs_bane.IsildursBane;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class ShopList extends WorldSavedData {
	
	public static final String KEY = IsildursBane.MODID + ":shops";
	public static final String LIST_KEY = "list";
	public static final int COMPOUND_TYPE = 10;
	
	private ArrayList<ShopData> shops = new ArrayList();
	
	public ShopList() {
		super(KEY);
	}

	public ShopList(String key) {
		super(key);
	}
	
	public static ShopList get(EntityPlayer player) {
		MapStorage storage = player.worldObj.mapStorage; //Use mapStorage for data that's consistent between dimensions, otherwise perWorldStorage
		ShopList shops = (ShopList) storage.loadData(ShopList.class, KEY);
		if(shops == null) {
			shops = new ShopList();
			storage.setData(KEY, shops);
		}
		return shops;
	}
	
	public void addShop(ShopData shop) {
		if(shop != null) {
			for(ShopData current : shops)
				if (shop.mapName.compareTo(current.mapName) == 0) {
					return;
				}
		shops.add(shop);
		}
	}
	
	public void removeShop(String key) {
		if(key != null)
			for(int i = 0; i < shops.size(); i ++)
				if(shops.get(i).mapName.compareTo(key) == 0) {
					shops.remove(i);
					return;
				}
	}
	
	public ShopData getShop(String key) {
		if(key != null)
			for(ShopData current : shops)
				if(current.mapName.equals(key)) {
					return current;
				}
		return null;
	}
	
	public ShopData getShop(int x, int y, int z) {
			for(ShopData current : shops)
				if(current.arePosSame(x, y, z)) {
					return current;
				}
		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList(LIST_KEY, COMPOUND_TYPE);
		for(int i = 0; i < list.tagCount(); i ++) {
			NBTTagCompound comp = list.getCompoundTagAt(i);
			ShopData shop = new ShopData(ShopData.getResultKey(comp.getInteger("x"), comp.getInteger("y"), comp.getInteger("z")), UUID.fromString(comp.getString("key")));
			shop.readFromNBT(comp);
			shops.add(shop);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();
		for(ShopData current : shops) {
			NBTTagCompound shopCompound = new NBTTagCompound();
			current.writeToNBT(shopCompound);
			list.appendTag(shopCompound);
		}
		compound.setTag(LIST_KEY, list);
	}
	
	public void print() {
		for(ShopData current : shops)
			current.print();
	}

}
