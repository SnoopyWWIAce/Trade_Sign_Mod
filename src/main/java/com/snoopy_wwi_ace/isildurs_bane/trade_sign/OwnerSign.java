package com.snoopy_wwi_ace.isildurs_bane.trade_sign;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@Deprecated
public class OwnerSign extends TradeSign {

	public OwnerSign(String[] signIn, EntityPlayer player) {
		super(signIn, player);
	}

	/**
	 * Handles sign input for the sale line. Input is handled one
	 * of three ways:
	 * 		1) if the held {@link ItemStack} matches the {@link ItemStack} for
	 * 			sale, the held {@link ItemStack} is added to the sign.
	 * 		2) if the held {@link ItemStack} is not {@value null} and no {@link Item}s
	 * 			remain for sale, the held {@link ItemStack} is set to the sale value
	 * 			and added to the sign.
	 * 		3) Otherwise {@link Item}s are removed from the sign if possible.
	 */
	@Override
	public void rightClick(EntityPlayer player) {
		manipTradeLine(player, sale);
	}

	/**
	 * Handles sign input for the currency line. Input is handled one
	 * of three ways:
	 * 		1) if the held {@link ItemStack} matches the {@link ItemStack} for
	 * 			sale, the held {@link ItemStack} is added to the sign.
	 * 		2) if the held {@link ItemStack} is not {@value null} and no {@link Item}s
	 * 			remain for sale, the held {@link ItemStack} is set to the sale value
	 * 			and added to the sign.
	 * 		3) Otherwise {@link Item}s are removed from the sign if possible.
	 */
	@Override
	public void leftClick(EntityPlayer player) {
		manipTradeLine(player, money);
	}
	
	private void manipTradeLine(EntityPlayer player, TradeLine line) {
		if(shouldAddToSign(player, line)) {
			line.addItems(player.getHeldItem().stackSize);
			//playerControl.consumeItems(player, player.getHeldItem().getItem(), player.getHeldItem().stackSize);
		} else if(shouldChangeSignValue(player, line)) {
			line.setItemStack(player.getHeldItem());
			line.addItems(player.getHeldItem().stackSize);
			playerControl.consumeHeldItem(player);
		} else if(shouldRemoveFromSign(player, line)) {
			ArrayList<ItemStack> stacks = line.consumeItems(line.getAmount());
			playerControl.addToInventory(player, stacks);
		}
	}
	
	private boolean shouldAddToSign(EntityPlayer player, TradeLine line) {
		return player.getHeldItem() != null &&
				player.getHeldItem().getItem() == line.getItemStack().getItem();
	}
	
	private boolean shouldChangeSignValue(EntityPlayer player, TradeLine line) {
		return player.getHeldItem() != null &&
				line.getStored() == 0;
	}
	
	private boolean shouldRemoveFromSign(EntityPlayer player, TradeLine line) {
		return line.getStored() > 0;
	}

}
