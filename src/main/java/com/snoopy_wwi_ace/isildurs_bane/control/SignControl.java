package com.snoopy_wwi_ace.isildurs_bane.control;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;

@Deprecated
public class SignControl {
	
	private static final String IDENTIFIER = "[trade]";
	private static final String DELIMITER = ":";
	private static final int MAX_SIGN_INDEX = 4;
	private static final int FOR_SALE_INDEX = 1;
	private static final int INCOME_INDEX = 2;
	
	public boolean canBePurchased(String[] sign) {
		return this.getRemainingSale(sign) >= this.getSaleQuantity(sign);
	}
	
	public boolean isIncomeEmpty(String[] sign) {
		return this.getStoredIncome(sign) == 0;
	}
	
	public boolean isIncomeSet(String[] sign) {
		return this.getCurrencyItem(sign) != null && !this.isIncomeEmpty(sign);
	}
	
	public boolean isSaleEmpty(String[] sign) {
		return this.getRemainingSale(sign) == 0;
	}
	
	public boolean isSaleSet(String[] sign) {
		return this.getSaleItem(sign) != null && !this.isSaleEmpty(sign);
	}
	
	public boolean isPlayersSign(EntityPlayerMP player, String[] sign) {
		return player.getDisplayName().compareTo(sign[MAX_SIGN_INDEX - 1]) == 0;
	}
	
	/**
	 * Checks whether this string represents a valid trade sign.
	 * 
	 * @param lines The array of Strings from a sign.
	 * @return True if these Strings represent a valid trade sign.
	 */
	public boolean isTradeSign(String[] lines) {
		return lines[0].compareTo(IDENTIFIER) == 0;
	}
	
	/**
	 * Updates the {@link TileEntitySign} at the given location.
	 * 
	 * @param world The {@link World} the sign is in.
	 * @param x The x coordinate of the sign.
	 * @param y The y coordinate of the sign.
	 * @param z The z coordinate of the sign.
	 * @param sign The new sign data.
	 */
	public static void updateSign(World world, int x, int y, int z, String[] sign) {
		if(world.blockExists(x, y, z) && world.getTileEntity(x, y, z) instanceof TileEntitySign) {
			TileEntitySign tile = (TileEntitySign)world.getTileEntity(x, y, z);
			System.arraycopy(sign, 0, tile.signText, 0, MAX_SIGN_INDEX);
			tile.markDirty();
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	/**
	 * Checks whether the String array parameter makes a correct trade sign.
	 * Does not check whether the sign is a trade sign. The format of the
	 * sign should be:
	 * [trade]
	 * {Quantity sold per purchase} {Purchase Item ID} : {Quantity of items for sale}
	 * {Price per purchase} {Currency Item ID} : {Amount accumulated}
	 * {Sign owner's name}
	 * 
	 * @param sign The value of the sign.
	 * @return Whether the 
	 */
	public boolean isCorrectTradeSign(String[] sign) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(sign[FOR_SALE_INDEX]);
			if(tokenizer.countTokens() != 4 || !isValidLine(tokenizer))
				return false;
			tokenizer = new StringTokenizer(sign[INCOME_INDEX]);
			if(tokenizer.countTokens() != 4 || !isValidLine(tokenizer))
				return false;
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	@Deprecated
	private boolean isValidLine(StringTokenizer tokenizer) {
		return Integer.parseInt(tokenizer.nextToken()) >= 0 
				|| Item.getItemById(Integer.parseInt(tokenizer.nextToken())) != null 
				|| !(tokenizer.nextToken().compareTo(DELIMITER) == 0) 
				|| Integer.parseInt(tokenizer.nextToken()) >= 0;
	}
	
	@Deprecated
	public String[] setStoredIncome(String[] sign, int income) {
		int price = this.getIncomeQuantity(sign);
		int currency = this.getCurrencyID(sign);
		sign[INCOME_INDEX] = price + " " + currency + " " + DELIMITER + " " + income;
		return sign;
	}
	
	@Deprecated
	public String[] setIncomeQuantity(String[] sign, int income) {
		int currency = this.getCurrencyID(sign);
		int stored = this.getStoredIncome(sign);
		sign[INCOME_INDEX] = income + " " + currency + " " + DELIMITER + " " + stored;
		return sign;
	}
	
	@Deprecated
	public String[] setRemainingSale(String[] sign, int sale) {
		int quantity = this.getSaleQuantity(sign);
		int saleID = this.getSaleID(sign);
		sign[this.FOR_SALE_INDEX] = quantity + " " + saleID + " " + DELIMITER + " " + sale;
		return sign;
	}
	
	@Deprecated
	public String[] setSaleQuantity(String[] sign, int sale) {
		int saleID = this.getSaleID(sign);
		int store = this.getRemainingSale(sign);
		sign[FOR_SALE_INDEX] = sale + " " + saleID + " " + DELIMITER + " " + store;
		return sign;
	}
	
	@Deprecated
	public String[] setSaleID(String[] sign, int id) {
		int quantity = this.getSaleQuantity(sign);
		int store = this.getRemainingSale(sign);
		sign[FOR_SALE_INDEX] = quantity + " " + id + " " + DELIMITER + " " + store;
		return sign;
	}
	
	@Deprecated
	public String[] setCurrencyID(String[] sign, int id) {
		int price = this.getIncomeQuantity(sign);
		int stored = this.getStoredIncome(sign);
		sign[INCOME_INDEX] = price + " " + id + " " + DELIMITER + " " + stored;
		return sign;
	}
	
	@Deprecated
	public int getStoredIncome(String[] sign) throws NumberFormatException {
		return getToken(sign, INCOME_INDEX, 4);
	}
	
	@Deprecated
	public int getIncomeQuantity(String[] sign) throws NumberFormatException {
		return getToken(sign, INCOME_INDEX, 1);
	}
	
	@Deprecated
	public int getRemainingSale(String[] sign) throws NumberFormatException {
		return getToken(sign, FOR_SALE_INDEX, 4);
	}
	
	@Deprecated
	public int getSaleQuantity(String[] sign) throws NumberFormatException {
		return getToken(sign, FOR_SALE_INDEX, 1);
	}
	
	@Deprecated
	private int getSaleID(String[] sign) throws NumberFormatException {
		return getToken(sign, FOR_SALE_INDEX, 2);
	}
	
	@Deprecated
	private int getCurrencyID(String[] sign) throws NumberFormatException {
		return getToken(sign, INCOME_INDEX, 2);
	}
	
	@Deprecated
	private int getToken(String[] sign, int index, int token) throws NumberFormatException, NoSuchElementException {
		try {
			StringTokenizer tokenizer = new StringTokenizer(sign[index]);
			for(int i = 0; i < token - 1; i ++)
				tokenizer.nextToken();
			return Integer.parseInt(tokenizer.nextToken());
		} catch(Exception e) {
			return -1;
		}
	}
	
	@Deprecated
	public Item getSaleItem(String[] sign) {
		return Item.getItemById(getSaleID(sign));
	}
	
	@Deprecated
	public Item getCurrencyItem(String[] sign) {
		return Item.getItemById(getCurrencyID(sign));
	}
	
	@Deprecated
	private int getSaleItemDamage(String[] sign) {
		return 0;
	}
	
	@Deprecated
	private int getCurencyItemDamage(String[] sign) {
		return 0;
	}

}
