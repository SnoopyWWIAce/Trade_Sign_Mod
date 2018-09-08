package com.snoopy_wwi_ace.isildurs_bane.tile_entity_trade;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

@Deprecated
public class TileEntityTrade extends TileEntitySign implements IMerchant, IInventory {

	ItemStack stacks[] = new ItemStack[27];

	public TileEntityTrade(int xIn, int yIn, int zIn) {
		this.xCoord = xIn;
		this.yCoord = yIn;
		this.zCoord = zIn;
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return stacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int removed) {
		ItemStack stack = stacks[slot];
		stack.stackSize -= removed; // TODO check for overflow
		return stacks[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = stacks[slot];
		stacks[slot] = null;
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		stacks[slot] = stack;
	}

	@Override
	public String getInventoryName() {
		return "trade sign";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		//copied from TileEntityChest
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}

	@Override
	public void setCustomer(EntityPlayer p_70932_1_) {}

	@Override
	public EntityPlayer getCustomer() {
		return null;
	}

	@Override
	public MerchantRecipeList getRecipes(EntityPlayer p_70934_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecipes(MerchantRecipeList p_70930_1_) {
		// TODO Auto-generated method stub

	}

	@Override
	public void useRecipe(MerchantRecipe p_70933_1_) {
		// TODO Auto-generated method stub

	}

	@Override
	public void func_110297_a_(ItemStack p_110297_1_) {
		//Don't actually know what this is used for. EntityVillager uses it to deal with talking.
	}

	@Override
	public void markDirty() {
		// TODO Auto-generated method stub
		
	}

}
