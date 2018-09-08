package com.snoopy_wwi_ace.isildurs_bane;

import com.snoopy_wwi_ace.isildurs_bane.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(acceptableRemoteVersions = "*", modid = IsildursBane.MODID, version = IsildursBane.VERSION, name = IsildursBane.NAME)
public class IsildursBane {

	public static final String MODID = "trade_signs";
	public static final String VERSION = "0.037b";
	public static final String NAME = "Trade Signs";

	@SidedProxy(clientSide = "com.snoopy_wwi_ace.isildurs_bane.proxy.ClientProxy", serverSide = "com.snoopy_wwi_ace.isildurs_bane.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void load(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public void load(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
