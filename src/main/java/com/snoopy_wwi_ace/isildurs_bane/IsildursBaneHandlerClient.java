package com.snoopy_wwi_ace.isildurs_bane;

import com.snoopy_wwi_ace.isildurs_bane.channel_handler.IsildursBaneChannelHandler;
import com.snoopy_wwi_ace.isildurs_bane.channel_handler.IsildursBaneDigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

public class IsildursBaneHandlerClient {

	@SubscribeEvent
	public void onClientConnect(ServerConnectionFromClientEvent event) {
		EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
		event.manager.channel().pipeline().addFirst("sign_update_listener", new IsildursBaneChannelHandler(player)).addBefore("sign_update_listener", "player_dig_listener", new IsildursBaneDigHandler(player));
	}
	
}
