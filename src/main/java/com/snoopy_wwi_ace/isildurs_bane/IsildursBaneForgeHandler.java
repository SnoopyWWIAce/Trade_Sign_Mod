package com.snoopy_wwi_ace.isildurs_bane;

import java.util.ArrayList;
import java.util.UUID;

import com.snoopy_wwi_ace.isildurs_bane.control.PlayerInventoryControl;
import com.snoopy_wwi_ace.isildurs_bane.control.TradeHandler;
import com.snoopy_wwi_ace.isildurs_bane.shops.ShopData;
import com.snoopy_wwi_ace.isildurs_bane.shops.ShopList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockSign;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.WorldEvent;

public class IsildursBaneForgeHandler {
	
	boolean hasLoaded = false;
	
	@SubscribeEvent
	public void onSignDestroyed(BreakEvent event) {
		if(event.block instanceof BlockSign) {
			TileEntitySign sign = (TileEntitySign)event.world.getTileEntity(event.x, event.y, event.z);
			String[] signText = sign.signText;
			PlayerInventoryControl control = new PlayerInventoryControl();
			ShopList list = ShopList.get(event.getPlayer());
			if(list != null) {
				ShopData shop = list.getShop(event.x, event.y, event.z);
				if(shop != null)
					if(event.getPlayer() != null)
						control.addToInventory(event.getPlayer(), shop.getItems());
					else {
						ArrayList<ItemStack> stacks = shop.getItems();
						for(ItemStack current : stacks)
							event.world.spawnEntityInWorld(new EntityItem(event.world, event.x, event.y, event.z, current));
					}
				list.removeShop(ShopData.getResultKey(event.x, event.y, event.z));
				list.markDirty();
			}
		}
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		MapStorage storage = event.world.mapStorage;
		ShopList shop = (ShopList) storage.loadData(ShopList.class, ShopList.KEY);
		if(shop == null) {
			shop = new ShopList();
			storage.setData(ShopList.KEY, shop);
		}
	}
	
	private UUID getPlayerUUID(World world, String name) {
		return world.getPlayerEntityByName(name).getPersistentID();
	}

}
