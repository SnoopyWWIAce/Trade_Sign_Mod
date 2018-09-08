package format;

public abstract class FormatComponent<T> {
	
	private final byte key;
	
	public FormatComponent(byte keyIn) {
		key = keyIn;
	}
	
	public abstract T getComponent(String input, Format format);
	
	public abstract void setComponent(T input, Format format);
	
	public boolean doKeysMatch(byte keyIn) {
		return key == keyIn;
	}

}
