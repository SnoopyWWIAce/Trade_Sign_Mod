package com.snoopy_wwi_ace.isildurs_bane.channel_handler;

import java.util.UUID;

import com.snoopy_wwi_ace.isildurs_bane.control.TradeHandler;
import com.snoopy_wwi_ace.isildurs_bane.shops.ShopData;
import com.snoopy_wwi_ace.isildurs_bane.shops.ShopList;
import com.snoopy_wwi_ace.isildurs_bane.tile_entity_trade.TileEntityTrade;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class IsildursBaneDigHandler extends SimpleChannelInboundHandler<C08PacketPlayerBlockPlacement> {
	
	private EntityPlayerMP player;
	
	public IsildursBaneDigHandler(EntityPlayerMP playerIn) {
		super(false);
		player = playerIn;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, C08PacketPlayerBlockPlacement msg) throws Exception {
		ShopList list = ShopList.get(player);
		String[] sign = TradeHandler.getSignText(player.worldObj, msg.func_149576_c(), msg.func_149571_d(), msg.func_149570_e());
		TileEntitySign tile = TradeHandler.getSignAtLocation(player.worldObj, msg.func_149576_c(), msg.func_149571_d(), msg.func_149570_e());
		if(list != null && sign != null && sign[0].equalsIgnoreCase("[trade]") && player.getHeldItem() == null) {
			ShopData shop = list.getShop(msg.func_149576_c(), msg.func_149571_d(), msg.func_149570_e());
			if(shop != null) {
				shop.setCustomer(player);
				if(shop.isOwner(player.getPersistentID()) && !player.isSneaking()) {
					sign[1] = player.getCommandSenderName().substring(0, Math.min(player.getCommandSenderName().length(), 15));
					tile.markDirty();
					player.worldObj.markBlockForUpdate(msg.func_149576_c(), msg.func_149571_d(), msg.func_149570_e());
					player.displayGUIChest(shop);
				} else {
					player.displayGUIMerchant(shop, shop.getInventoryName());
				}
			}
			list.markDirty();
		}
		ctx.fireChannelRead(msg);
	}

}
