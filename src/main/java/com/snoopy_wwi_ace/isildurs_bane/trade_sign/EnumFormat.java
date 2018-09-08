package com.snoopy_wwi_ace.isildurs_bane.trade_sign;

public enum EnumFormat {
	
	BLACK("§0"),
	BLUE_DARK("§1"),
	GREEN_DARK("§2"),
	AQUA_DARK("§3"),
	RED_DARK("§4"),
	PURPLE_DARK("§5"),
	GOLD("§6"),
	GRAY("§7"),
	GRAY_DARK("§8"),
	BLUE("§9"),
	GREEN("§a"),
	AQUA("§b"),
	RED("§c"),
	PURPLE_LIGHT("§d"),
	YELLOW("§e"),
	WHITE("§f"),
	OBFUSCATED("§k"),
	BOLD("§l"),
	STRIKE("§m"),
	UNDERLINE("§n"),
	ITALIC("§o"),
	RESET("§r");
	
	private String value;
	
	private EnumFormat(String valueIn) {
		value = valueIn;
	}
	
	public String getValue() {
		return value;
	}
	
	public EnumFormat getEnum(String valueIn) {
		for(EnumFormat current : EnumFormat.values())
			if(current.getValue().equals(valueIn))
				return current;
		return RESET;
	}

}
