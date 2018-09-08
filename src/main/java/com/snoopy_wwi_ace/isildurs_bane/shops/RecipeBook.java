package com.snoopy_wwi_ace.isildurs_bane.shops;

import java.util.Stack;
import java.util.StringTokenizer;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.village.MerchantRecipe;

/**
 * Format:
 * 		Title: "[trade]"
 * 		Page 1: buying1 <id> <size> <damage>
 * 		Page 2: buying2 <id> <size> <damage>
 * 		Page 3: selling <id> <size> <damage>
 * @author Steven
 *
 */
public class RecipeBook {
	
	private static final String TITLE_KEY = "title";
	private static final String PAGE_KEY = "pages";
	private static final String SHOP_TITLE = "[trade]";
	private static final int TAG_LIST_KEY = 8;
	
	private ItemStack selling;
	private ItemStack buying1, buying2;

	public RecipeBook(ItemStack bookIn) {
		NBTTagList tag = bookIn.getTagCompound().getTagList(PAGE_KEY, TAG_LIST_KEY);
		Stack<String> stack = new Stack();
		for(int i = 0; i < tag.tagCount() && i < 3; i ++)
			stack.push(tag.getStringTagAt(i));
		selling = produceItemStack(stack.pop());
		buying1 = produceItemStack(stack.pop());
		if(!stack.isEmpty())
			buying2 = produceItemStack(stack.pop());
	}
	
	private ItemStack produceItemStack(String params) {
		StringTokenizer tokenizer = new StringTokenizer(params);
		int id = Integer.parseInt(tokenizer.nextToken());
		int size = Integer.parseInt(tokenizer.nextToken());
		int damage = Integer.parseInt(tokenizer.nextToken());
		ItemStack result = new ItemStack(Item.getItemById(id), size, damage);
		return result;
	}
	
	public MerchantRecipe getRecipe() {
		return new MerchantRecipe(buying1, buying2, selling);
	}
	
	public static boolean isRecipeBook(ItemStack stack) {
		return stack.hasTagCompound()
				&& stack.getTagCompound().hasKey(TITLE_KEY)
				&& stack.getTagCompound().getString(TITLE_KEY).equalsIgnoreCase(SHOP_TITLE)
				&& stack.getTagCompound().hasKey(PAGE_KEY)
				&& (stack.getTagCompound().getTagList(PAGE_KEY, TAG_LIST_KEY).tagCount() == 2
				|| stack.getTagCompound().getTagList(PAGE_KEY, TAG_LIST_KEY).tagCount() == 3);
	}
	
}
