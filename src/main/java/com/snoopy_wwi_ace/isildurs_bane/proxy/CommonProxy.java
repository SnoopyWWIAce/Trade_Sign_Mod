package com.snoopy_wwi_ace.isildurs_bane.proxy;

import com.snoopy_wwi_ace.isildurs_bane.IsildursBaneForgeHandler;
import com.snoopy_wwi_ace.isildurs_bane.IsildursBaneHandlerServer;
import com.snoopy_wwi_ace.isildurs_bane.tile_entity_trade.TileEntityTrade;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
	}

	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new IsildursBaneForgeHandler());
		GameRegistry.registerTileEntity(TileEntityTrade.class, "tile_entity_trade");
	}

	public void postInit(FMLPostInitializationEvent e) {
	}

}