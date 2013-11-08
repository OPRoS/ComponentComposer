package kr.co.n3soft.n3com;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class OPRoSStrings {
	// Bundle ID
	private static final String BUNDLE_ID = "kr.co.n3soft.n3com.OPRoSGUIProfileEditorStrings"; //$NON-NLS-1$
	//Resource bundle.
	private static ResourceBundle resourceBundle;
	static {
		try {
			resourceBundle = ResourceBundle.getBundle(BUNDLE_ID);
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}
	public static String getFormattedString(String key, String arg) {
		return MessageFormat.format(getString(key), (Object[])new String[] { arg });
	}

	public static String getFormattedString(String key, String[] args) {
		return MessageFormat.format(getString(key), (Object[])args);
	}

	public static String getString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!"; //$NON-NLS-1$ //$NON-NLS-2$
		} catch (NullPointerException e) {
			return "#" + key + "#"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private OPRoSStrings() {
		// No constructor
	}
}

