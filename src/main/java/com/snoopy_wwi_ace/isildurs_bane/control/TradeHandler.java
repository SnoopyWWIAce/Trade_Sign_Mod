package com.snoopy_wwi_ace.isildurs_bane.control;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.snoopy_wwi_ace.isildurs_bane.trade_sign.BuyerSign;
import com.snoopy_wwi_ace.isildurs_bane.trade_sign.OwnerSign;
import com.snoopy_wwi_ace.isildurs_bane.trade_sign.TradeSign;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;

@Deprecated
public class TradeHandler {

	private SignControl handler = new SignControl();
	private PlayerInventoryControl invControl = new PlayerInventoryControl();
	private TradeSign sign = null;
	
	public TradeHandler(String[] signIn, EntityPlayer player) {
		if(TradeSign.isValidSign(signIn)) {
			if(TradeSign.isPlayersSign(signIn, player))
				sign = new OwnerSign(signIn, player);
			else
				sign = new BuyerSign(signIn, player);
		}
	}

	public String[] clicked(EntityPlayerMP player, String[] sign, ItemStack stack, int x, int y, int z) {
		if(player.isSneaking())
			this.sign.leftClick(player);
		else
			this.sign.rightClick(player);
		handler.updateSign(player.worldObj, x, y, z, this.sign.getSign());
		return this.sign.getSign();
	}
	
	public String[] getStrings() {
		return sign != null ? sign.getSign() : new String[] {"", "", "", ""};
	}

	/**
	 * Gets the {@link BlockSign} at the specified coordinates.
	 * 
	 * @param world
	 *            {@link World} that is being checked for the {@link BlockSign}.
	 * @param x
	 *            The x coordinate to check.
	 * @param y
	 *            The y coordinate to check.
	 * @param z
	 *            The z coordinate to check.
	 * @return The {@link BlockSign}, or {@value null} if no {@link BlockSign}
	 *         is found.
	 */
	public static TileEntitySign getSignAtLocation(World world, int x, int y, int z) {
		if (world.blockExists(x, y, z)) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntitySign) {
				TileEntitySign sign = (TileEntitySign) tile;
				return sign;
			}
		}
		return null;
	}

	/**
	 * Returns the {@link TileEntitySign}'s text if there is a {@link BlockSign}
	 * at the specified location.
	 * 
	 * @param world
	 *            The {@link World} that the {@link TileEntitySign} is in.
	 * @param x
	 *            The X coordinate
	 * @param y
	 *            The Y coordinate
	 * @param z
	 *            The Z coordinate
	 * @return A String array containing the {@link TileEntitySign}'s text, or
	 *         {@value null} if no {@link TileEntitySign} is found.
	 */
	public static String[] getSignText(World world, int x, int y, int z) {
		if (world.blockExists(x, y, z)) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntitySign) {
				TileEntitySign sign = (TileEntitySign) tile;
				return sign.signText;
			}
		}
		return null;
	}

	public void onBlockBreak(World world, String[] sign, int x, int y, int z) {
		ArrayList<ItemStack> saleStacks = invControl.breakIntoStacks(handler.getSaleItem(sign),
				handler.getRemainingSale(sign));
		ArrayList<ItemStack> profitStacks = invControl.breakIntoStacks(handler.getCurrencyItem(sign),
				handler.getStoredIncome(sign));
		for (ItemStack stack : saleStacks)
			world.spawnEntityInWorld(new EntityItem(world, x, y, z, stack));
		for (ItemStack stack : profitStacks)
			world.spawnEntityInWorld(new EntityItem(world, x, y, z, stack));
	}

}
