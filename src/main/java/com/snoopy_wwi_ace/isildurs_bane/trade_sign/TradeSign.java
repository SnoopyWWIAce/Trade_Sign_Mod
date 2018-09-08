package com.snoopy_wwi_ace.isildurs_bane.trade_sign;

import java.util.StringTokenizer;

import com.snoopy_wwi_ace.isildurs_bane.control.PlayerInventoryControl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

@Deprecated
public abstract class TradeSign {
	
	private static final String IDENTIFIER = "[trade]";
	private static final int MAX_SIGN_INDEX = 4;
	private static final int FOR_SALE_INDEX = 1;
	private static final int INCOME_INDEX = 2;
	
	protected TradeLine sale, money;
	private String name;
	protected PlayerInventoryControl playerControl = new PlayerInventoryControl();
	
	public TradeSign(String[] signIn, EntityPlayer player) {
		sale = new TradeLine(signIn[this.FOR_SALE_INDEX]);
		money = new TradeLine(signIn[this.INCOME_INDEX]);
		name = player.getCommandSenderName();
	}
	
	/**
	 * Checks whether this is a valid trade sign.
	 * @param signIn The sign, in {@link String} format, to be checked.
	 * @return True if the initial line of the sign is "[trade]", false
	 * 		otherwise.
	 */
	public static boolean isValidSign(String[] signIn) {
		return signIn != null 
				&& signIn.length >= 1 
				&& signIn[0] != null
				&& signIn[0].compareTo(IDENTIFIER) == 0;
	}
	
	public static boolean isPlayersSign(String[] signIn, EntityPlayer player) {
		return signIn != null && player != null
				&& signIn.length >= MAX_SIGN_INDEX
				&& signIn[MAX_SIGN_INDEX - 1].compareTo(player.getDisplayName()) == 0;
	}
	
	/**
	 * Handles sign input for the sales line.
	 * @param player The player interacting with the sign.
	 */
	public abstract void rightClick(EntityPlayer player);
	
	/**
	 * Handles sign input for the currency line.
	 * @param player The player interacting with the sign.
	 */
	public abstract void leftClick(EntityPlayer player);
	
	/**
	 * Returns the updated value of the sign in string format.
	 * For use in updating the sign in-world.
	 * @return A {@link String} array containing the contents
	 * 		of the sign
	 */
	public String[] getSign() {
		String[] result = new String[MAX_SIGN_INDEX];
		result[0] = IDENTIFIER;
		result[FOR_SALE_INDEX] = sale.getLine();
		result[INCOME_INDEX] = money.getLine();
		result[MAX_SIGN_INDEX - 1] = name;
		return result;
	}

}
