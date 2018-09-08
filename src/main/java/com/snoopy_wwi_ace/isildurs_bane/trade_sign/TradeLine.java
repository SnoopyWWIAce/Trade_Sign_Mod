package com.snoopy_wwi_ace.isildurs_bane.trade_sign;

import java.util.ArrayList;
import java.util.StringTokenizer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Deprecated
public class TradeLine {

	private static final String DELIMITER = " : ";
	
	private ItemStack stack;
	private int amount, stored;
	private EnumFormat color;
	
	public TradeLine(String line) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(line);
			stack = getStackFromString(tokenizer.nextToken());
			amount = Integer.parseInt(tokenizer.nextToken());
		} catch (Exception e) {
			stack = null;
			amount = stored = 0;
		}
		
	}
	
	public void setItemStack(ItemStack stackIn) {
		stack = stackIn;
		amount = stackIn.stackSize;
	}
	
	public ItemStack getItemStack() {
		return stack;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getStored() {
		return stored;
	}
	
	public ArrayList<ItemStack> consumeItems(int num) {
		ArrayList<ItemStack> result = new ArrayList();
		while(num > 0 && stored > 0) {
			int quantity = cap(amount, num, stored);
			num -= quantity;
			stored -= quantity;
			//add as many stacks as necessary to the result list
			int numStacks = quantity / stack.getMaxStackSize();
			for(int i = 0; i < numStacks; i ++)
				result.add(new ItemStack(stack.getItem(), stack.getMaxStackSize(), stack.getItemDamage()));
			result.add(new ItemStack(stack.getItem(), quantity % stack.getMaxStackSize(), stack.getItemDamage()));
		}
		return result;
	}
	
	public void addItems(int numItems) {
		stored += numItems;
	}
	
	private int cap(int base, int...nums) {
		int result = base;
		for(int current : nums)
			if(result > current)
				result = current;
		return result;
	}
	
	public String getLine() {
		String result = "";
		if(stack == null)
			result = "0";
		else {
			result += Item.getIdFromItem(stack.getItem());
			if(stack.getHasSubtypes())
				result += ":" + stack.getItemDamage();
		}
		result += " " + amount + DELIMITER + stored;
		return result;
	}
	
	private ItemStack getStackFromString(String line) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(line, ":");
			Item item = Item.getItemById(Integer.parseInt(tokenizer.nextToken()));
			ItemStack result = new ItemStack(item);
			if(tokenizer.hasMoreTokens())
				result.setItemDamage(Integer.parseInt(tokenizer.nextToken()));
			return result;
		} catch (Exception e) {
			return null;
		}
	}

}
