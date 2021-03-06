package com.snoopy_wwi_ace.isildurs_bane.proxy;

import com.snoopy_wwi_ace.isildurs_bane.IsildursBaneForgeHandler;
import com.snoopy_wwi_ace.isildurs_bane.IsildursBaneHandlerServer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy extends CommonProxy {

	@Override
	public void init(FMLInitializationEvent e) {
		FMLCommonHandler.instance().bus().register(new IsildursBaneHandlerServer());
		super.init(e);
	}
	
}