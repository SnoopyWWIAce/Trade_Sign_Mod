package com.snoopy_wwi_ace.isildurs_bane.trade_sign;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@Deprecated
public class BuyerSign extends TradeSign {

	public BuyerSign(String[] signIn, EntityPlayer player) {
		super(signIn, player);
	}

	@Override
	public void rightClick(EntityPlayer player) {
		/*if(canBuy(player)) {
			ArrayList<ItemStack> result = sale.consumeItems(sale.getAmount());
			money.addItems(sale.getAmount());
			playerControl.consumeItems(player, money.getItemStack().getItem(), sale.getAmount());
			playerControl.addToInventory(player, result);
		}*/
	}

	@Override
	public void leftClick(EntityPlayer player) {
	}
	
	/*private boolean canBuy(EntityPlayer player) {
		return playerControl.hasEnough(player, money.getItemStack().getItem(), money.getAmount()) &&
				sale.getStored() > 0 &&
				sale.getStored() >= sale.getAmount();
	}*/

}
