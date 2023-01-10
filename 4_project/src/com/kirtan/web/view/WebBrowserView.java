package com.kirtan.web.view;

import java.awt.Desktop.Action;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.ui.part.ViewPart;

public class WebBrowserView extends ViewPart {
	
	static public String ID = WebBrowserView.class.getName();
	private Browser browser;
	private Combo urlCombo;
	
	private Action actionBack;
	private Action actionForward;
	private Action actionHome;
	private Action actionAddBookmark;
	
	private final String startUrl = "https://www.google.com/";
	
//	public static ImageDescriptor ICON_HOME = BrowserActivator.getImageDescriptor(); 
	
	private IStatusLineManager statusLine;
	

	public WebBrowserView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(1,true));
		
		CoolBar coolbar = new CoolBar(comp,SWT.NONE);
		coolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		CoolItem coolitem = new CoolItem(coolbar,SWT.NONE);
//		coolitem.setControl(createComboView(coolbar,new GridData(GridData.FILL_HORIZONTAL)));
//		calcSize(coolitem);
//		
		
		
		

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
