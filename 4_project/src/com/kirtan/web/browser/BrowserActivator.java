package com.kirtan.web.browser;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class BrowserActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.kirtan.web.browser"; //$NON-NLS-1$

	// The shared instance
	private static BrowserActivator plugin;
	
	/**
	 * The constructor
	 */
	public BrowserActivator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		System.out.println("System has Started !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static BrowserActivator getDefault() {
		return plugin;
	}
	
	public static String getString(String key , Object ... args) {
		try {
			return MessageFormat.format(getString(key), args);
		}catch(MissingResourceException e) {
			return key;
		}catch(NullPointerException e) {
			return "!" + key + "!";
		}
	}
	
	public static void showErrorMessage(Shell shell, String message) {
		MessageDialog.openInformation(shell, getString("info.dialog.title"), message);
		
	}
	
	public static IViewPart getView (IWorkbenchWindow window,  String ViewID) 
	{
		IViewReference[] refs = window.getActivePage().getViewReferences();
		for (IViewReference viewReference : refs) {
			if ( viewReference.getId().equals(ViewID) )
				return viewReference.getView(true);
		}
		return null;
	}

}
