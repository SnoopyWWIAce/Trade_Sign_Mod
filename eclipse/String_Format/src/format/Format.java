package format;

import java.util.ArrayList;
import java.util.Iterator;

public class Format {
	
	private final ArrayList<FormatComponent> components;
	private final String delimiter;

	public Format(String delimiterIn, FormatComponent... componentsIn) {
		components = new ArrayList<FormatComponent>(componentsIn.length);
		for (FormatComponent current : componentsIn)
			components.add(current);
		delimiter = delimiterIn;
	}
	
	public FormatComponent getComponent(byte keyIn) {
		Iterator<FormatComponent> iter = components.iterator();
		while(iter.hasNext()) {
			FormatComponent component = iter.next();
			if(component.doKeysMatch(keyIn))
				return component;
		}
		return null;
	}
	
}
