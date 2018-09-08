package com.snoopy_wwi_ace.isildurs_bane.control;

import java.util.ArrayList;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * A control class that provides inventory management algorithms.
 * 
 * @author SnoopyWWIAce
 *
 */
public class PlayerInventoryControl {

	/**
	 * Returns whether the {@link EntityPlayerMP} has at least the specified
	 * amount of this particular {@link Item} in its {@link InventoryPlayer}.
	 * 
	 * @param player
	 *            The {@link EntityPlayerMP} whose {@link InventoryPlayer} is
	 *            being searched.
	 * @param item
	 *            The type of {@link Item} to search for.
	 * @param amount
	 *            The number of {@link Item} to search for.
	 * @return True if there are at least amount of {@link item} in the
	 *         {@link EntityPlayerMP}'s {@link InventoryPlayer}.
	 */
	public static boolean hasEnough(EntityPlayer player, ItemStack item, int amount) {
		return hasEnough(player.inventory.mainInventory, item, amount);
	}
	
	public static boolean hasEnough(ItemStack[] inventory, ItemStack item, int amount) {
		if (inventory == null || item == null)
			return false;
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null && inventory[i].getItem() == item.getItem())
				amount -= inventory[i].stackSize;
			if (amount <= 0)
				return true;
		}
		return false;
	}

	/**
	 * Removes the specified amount of items from the {@link EntityPlayerMP}'s
	 * {@link InventoryPlayer}. No checks are made before iterating through the
	 * {@link InventoryPlayer}.
	 * 
	 * @param player
	 *            A handle to the {EntityPlayerMP} whose {@link InventoryPlayer}
	 *            is being searched.
	 * @param item
	 *            The type of {@link Item} to be consumed from the inventory.
	 * @param amount
	 *            The number of {@link Item} to remove from the
	 *            {@link InventoryPlayer}.
	 * @return The number of {@link Item} that were removed.
	 */
	public static int consumeItems(EntityPlayer player, ItemStack item, int amount) {
		return consumeItems(player.inventory.mainInventory, item, amount);
	}
	
	public static int consumeItems(ItemStack[] inventory, ItemStack item, int amount) {
		int removed = 0;
		int remaining = amount;
		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];
			if (stack != null && stack.getItem() == item.getItem())
				if (stack.stackSize > remaining) {
					stack.stackSize -= remaining;
					removed += remaining;
					return removed;
				} else {
					inventory[i] = null;
					remaining -= stack.stackSize;
					removed += stack.stackSize;
				}
			if (remaining <= 0)
				return removed;
		}
		return removed;
	}

	/**
	 * Adds the specified number of {@link Item}'s to the {@link EntityPlayerMP}
	 * 's {@link InventoryPlayer}. If there are too many {@link Item}s to fit in
	 * the {@link InventoryPlayer}, the remaining {@link Item}s are spawned in
	 * the {@link World}.
	 * 
	 * @param player
	 *            The {@link EntityPlayerMP} to give the {@link Item}s.
	 * @param item
	 *            The type of {@link Item} to add to the {@link InventoryPlayer}
	 *            .
	 * @param amount
	 *            The number of {@link Item}s to add to the
	 *            {@link InventoryPlayer}.
	 */
	public static void addToInventory(EntityPlayer player, ItemStack item, int amount) {
		addToInventory(player.inventory.mainInventory, item, amount, player.worldObj, player.posX, player.posY, player.posZ);
	}
	
	public static void addToInventory(ItemStack[] inventory, ItemStack item, int amount, World world, double x, double y, double z) {
		if (item == null || amount <= 0)
			return;
		for(int i = 0; i < inventory.length && amount > 0; i ++) {
			if(amount >= item.getMaxStackSize()) {
				if(inventory[i] == null) {
					inventory[i] = new ItemStack(item.getItem(), item.getMaxStackSize());
					amount -= item.getMaxStackSize();
				} else if(inventory[i].getItem() == item.getItem() && inventory[i].getItemDamage() == item.getItemDamage()) {
					amount -= item.getMaxStackSize() - inventory[i].stackSize;
					inventory[i].stackSize = item.getMaxStackSize();
				}
			} else {
				if(inventory[i] == null) {
					inventory[i] = new ItemStack(item.getItem(), amount);
					amount = 0;
				} else if(inventory[i].getItem() == item.getItem()) {
					inventory[i].stackSize += amount;
					if(inventory[i].stackSize > item.getMaxStackSize()) {
						amount = inventory[i].stackSize - item.getMaxStackSize();
						inventory[i].stackSize = item.getMaxStackSize();
					} else
						break;
				}
			}
		}
		for (ItemStack stack : breakIntoStacks(item.getItem(), amount))
			world.spawnEntityInWorld(new EntityItem(world, x, y, z, stack));
	}
	
	/**
	 * Add the list of {@link ItemStack}s to the {@link EntityPlayer}'s inventory,
	 * or spawns them in the {@link World} if the inventory is full.
	 * 
	 * @param player The {@link EntityPlayer} to give the {@link ItemStack}s to.
	 * @param stacks The {@link ArrayList} of {@link ItemStack}s to add to the inventory.
	 */
	public static void addToInventory(EntityPlayer player, ArrayList<ItemStack> stacks) {
		addToInventory(player.inventory.mainInventory, stacks, player.worldObj, player.posX, player.posY, player.posZ);
	}
	
	public static void addToInventory(ItemStack[] inventory, ArrayList<ItemStack> stacks, World world, double x, double y, double z) {
		while(!stacks.isEmpty() && getFirstAvailableSlot(inventory, stacks.get(0)) >= 0) {
			ItemStack current = stacks.get(0);
			int slot = getFirstAvailableSlot(inventory, current);
			if(inventory[slot] != null) {
				int available = current.getItem().getItemStackLimit(current) - current.stackSize;
				if(available >= current.stackSize) { //There is room for the entire stack in this slot
					inventory[slot].stackSize += current.stackSize;
					stacks.remove(0);
				} else { //There is not enough room for the entire stack in this slot
					inventory[slot].stackSize += available;
					current.stackSize -= available;
				}
			} else {
				inventory[slot] = current;
				stacks.remove(0);
			}
		}
		for(ItemStack current : stacks) {
			world.spawnEntityInWorld(new EntityItem(world, x, y, z, current));
		}
	}

	/**
	 * Creates an {@link ArrayList} of {@link ItemStack}s.
	 * 
	 * @param item
	 *            The type of {@link Item} to use in {@link ItemStack} creation.
	 * @param quantity
	 *            The total number of {@link Item}s that are contained in the
	 *            resulting {@link ArrayList}.
	 * @return An {@link ArrayList} of {@link ItemStack}s.
	 */
	public static ArrayList<ItemStack> breakIntoStacks(Item item, int quantity) {
		if (item == null || quantity <= 0)
			return new ArrayList<ItemStack>();
		int stackSize = item.getItemStackLimit();
		int remaining = quantity;
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		for (; remaining > 0; remaining -= stackSize)
			if (remaining > stackSize)
				result.add(new ItemStack(item, stackSize));
			else
				result.add(new ItemStack(item, remaining));
		return result;
	}
	
	/**
	 * Sets the {@link EntityPlayer}'s held {@link ItemStack} to {@value null} and returns
	 * the overridden {@link ItemStack}.
	 * 
	 * @param player The {@link EntityPlayer} whose held item is being consumed.
	 * @return The {@link ItemStack} that the {@link EntityPlayer} was holding.
	 */
	public static ItemStack consumeHeldItem(EntityPlayer player) {
		ItemStack result = player.getHeldItem();
		int heldIndex = player.inventory.currentItem;
		player.inventory.mainInventory[heldIndex] = null;
		return result;
	}
	
	public static int getFirstAvailableSlot(ItemStack[] inventory, ItemStack item) {
		for(int slot = 0; slot < inventory.length; slot ++) {
			if(inventory[slot] != null && inventory[slot].isItemEqual(item) && inventory[slot].stackSize < inventory[slot].getMaxStackSize())
				return slot;
		}
		for(int slot = 0; slot < inventory.length; slot ++) {
			if(inventory[slot] == null)
				return slot;
		}
		return -1;
	}
	
	public static boolean hasRoom(ItemStack[] inventory, ItemStack item, int amount) {
		 return getRoom(inventory, item, amount) >= amount;
	}
	
	public static int getRoom(ItemStack[] inventory, ItemStack item, int amount) {
		int space = 0;
		for(ItemStack slot : inventory) {
			if(slot == null) {
				space += item.getMaxStackSize();
			} else if(item.isItemEqual(slot) && slot.stackSize < slot.getMaxStackSize()) {
				space += (slot.getMaxStackSize() - slot.stackSize);
			}
		}
		return space;
	}
	
	public static int getRemaining(ItemStack[] inventory, ItemStack item) {
		int remaining = 0;
		for(ItemStack slot : inventory) {
			if(slot != null && slot.isItemEqual(item))
				remaining += slot.stackSize;
		}
		return remaining;
	}

}
