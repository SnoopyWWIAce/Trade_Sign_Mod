package com.snoopy_wwi_ace.isildurs_bane.channel_handler;

import com.snoopy_wwi_ace.isildurs_bane.control.SignControl;
import com.snoopy_wwi_ace.isildurs_bane.control.TradeHandler;
import com.snoopy_wwi_ace.isildurs_bane.shops.ShopData;
import com.snoopy_wwi_ace.isildurs_bane.shops.ShopList;
import com.snoopy_wwi_ace.isildurs_bane.tile_entity_trade.TileEntityTrade;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.client.C12PacketUpdateSign;

public class IsildursBaneChannelHandler extends SimpleChannelInboundHandler<C12PacketUpdateSign> {
	
	private EntityPlayerMP player;

	public IsildursBaneChannelHandler(EntityPlayerMP playerIn) {
		super(false);
		player = playerIn;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, C12PacketUpdateSign msg) throws Exception {
		if(msg.func_149589_f()[0].equalsIgnoreCase("[trade]")) {
			ShopList list = ShopList.get(player);
			if(list != null) {
				list.addShop(new ShopData(ShopData.getResultKey(msg.func_149588_c(), msg.func_149586_d(), msg.func_149585_e()), player.getPersistentID()));
				/*if(!isListed(msg.func_149589_f(), player.getCommandSenderName()))
					msg = new C12PacketUpdateSign(msg.func_149588_c(), msg.func_149586_d(), msg.func_149585_e(), new String[] {"[trade]", player.getCommandSenderName(), msg.func_149589_f()[2], msg.func_149589_f()[3]});*/
				list.markDirty();
			}
		}
		ctx.fireChannelRead(msg);
	}
	
	private boolean isListed(String[] sign, String player) {
		return sign[1].compareTo(player) == 0 
				|| sign[2].compareTo(player) == 0 
				|| sign[3].compareTo(player) == 0;
	}

}
