package com.snoopy_wwi_ace.isildurs_bane;

import com.snoopy_wwi_ace.isildurs_bane.channel_handler.IsildursBaneChannelHandler;
import com.snoopy_wwi_ace.isildurs_bane.channel_handler.IsildursBaneDigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class IsildursBaneHandlerServer {
	
	@SubscribeEvent
	public void onClientConnect(ServerConnectionFromClientEvent event) {
		EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
		event.manager.channel().pipeline().addAfter("decoder", "sign_update_listener", new IsildursBaneChannelHandler(player)).addAfter("sign_update_listener", "player_dig_listener", new IsildursBaneDigHandler(player));
	}
	
}
