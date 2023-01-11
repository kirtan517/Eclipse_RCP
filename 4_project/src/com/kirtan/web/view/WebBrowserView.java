package com.kirtan.web.view;



//import org.eclipse.jface.action.Action;
//
//import org.eclipse.jface.action.IMenuManager;
//import org.eclipse.jface.action.IStatusLineManager;
//import org.eclipse.jface.action.IToolBarManager;
//import org.eclipse.jface.action.Separator;
//import org.eclipse.jface.resource.ImageDescriptor;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.SWTError;
//import org.eclipse.swt.browser.Browser;
//import org.eclipse.swt.browser.LocationEvent;
//import org.eclipse.swt.browser.LocationListener;
//import org.eclipse.swt.browser.ProgressEvent;
//import org.eclipse.swt.browser.ProgressListener;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.events.SelectionListener;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Combo;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Control;
//import org.eclipse.swt.widgets.CoolBar;
//import org.eclipse.swt.widgets.CoolItem;
//import org.eclipse.ui.IActionBars;
//import org.eclipse.ui.ISharedImages;
//import org.eclipse.ui.IWorkbenchActionConstants;
//import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.part.ViewPart;

import com.kirtan.web.browser.BrowserActivator;

import com.kirtan.web.browser.Web_Browser;


import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

//import ch02.browser.internal.BrowserActivator;
//import ch02.browser.util.SimpleHTTPClient;

public class WebBrowserView extends ViewPart {
	
	static public String ID = WebBrowserView.class.getName();
	private Browser browser;
	private Combo urlCombo;
	
	private Action actionBack;
	private Action actionForward;
	private Action actionHome;
	private Action actionAddBookmark;
	
	private final String startUrl = "https://www.google.com/";
	
	// for home Icon I am using same as forward Icon just to make things work
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
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 200;
	    gridData.heightHint = 200;
		coolbar.setLayoutData(gridData);
//		coolbar.setBackground(SWT.COLOR_RED);
		
		CoolItem coolitem = new CoolItem(coolbar,SWT.NONE);
		coolitem.setControl(createComboView(coolbar,new GridData(GridData.FILL_HORIZONTAL)));
		calcSize(coolitem);
		
		
		try {
			browser = new Browser(comp,SWT.NONE);
		}catch(SWTError e) {
			BrowserActivator.showErrorMessage(getViewSite().getShell(),"Error in creating browser " + e);
			return;
		}
		
		browser.setLayoutData(new GridData(GridData.FILL_BOTH));
		browser.setUrl(startUrl);
		
		
		browser.addLocationListener(new LocationListener() {
			@Override
			public void changed(LocationEvent e) {
				locChanged(e);
			}
			@Override
			public void changing(LocationEvent e) {
				return ;  // I don't know what this is need to understand what this function is doing first
			}
			
		});
		
		browser.addProgressListener(new ProgressListener() {
			
			@Override
			public void changed(ProgressEvent e) {
				onProgress(e);
			}
			
			@Override
			public void completed(ProgressEvent e) {
				
			}
		});
		
		makeActions();
		contributeToActionBar();
		
		statusLine = getViewSite().getActionBars().getStatusLineManager();
		

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		browser.setFocus();
	}
	
	
	// need to experiment with this stuff
	private Control createComboView(Composite parent, GridData layout) {
		urlCombo = new Combo(parent,SWT.NONE);
		layout.widthHint = 150;
	    layout.heightHint = 150;
		urlCombo.setLayoutData(layout);
		urlCombo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				final String url = ((Combo)e.getSource()).getText();
				browser.setUrl(url);
				urlCombo.add(url);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				browser.setUrl(((Combo)e.getSource()).getText());	
			}
			
		});
		
		return urlCombo;
	}
	
	// need to understand this function too
	private void calcSize(CoolItem item){
		Control control = item.getControl();
		org.eclipse.swt.graphics.Point pt = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		pt = item.computeSize(pt.x, pt.y);
		item.setSize(pt);
	}
	
	// understood this function 
	private void makeActions() {
		
		// back action 
		actionBack = new Action(){
			public void run() {
				browser.back();
			}
		};
		actionBack.setText("Back");
		actionBack.setToolTipText("Back from here");
		actionBack.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_BACK));
		
		
		// back forward
		actionForward = new Action(){
			public void run() {
				browser.forward();
			}
		};
		actionForward.setText("Forward");
		actionForward.setToolTipText("Forward from here");
		actionForward.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
		
		
		// ACTION action 
		actionHome = new Action(){
			public void run() {
				browser.setUrl(startUrl);
			}
		};
		actionHome.setText("Home");
		actionHome.setToolTipText("Take me Home");
		actionHome.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
		
		
		// action Add Bookmark
		actionAddBookmark = new Action(){
			public void run() {
				addBookmark(urlCombo.getText());
			}
		};
		actionAddBookmark.setText("Add BookMark");
		actionAddBookmark.setToolTipText("Add the bookmark to your list");
		actionAddBookmark.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE));
	}
	
	
	// need to understand this function
	private void contributeToActionBar() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}
	
	//need to understand 
	private void fillLocalPullDown(IMenuManager manager) {
		System.out.println(manager);
		manager.add(actionAddBookmark);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	//need to understand
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionHome);
		manager.add(actionForward);
		manager.add(actionBack);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	
	private void addBookmark(final String url) {
		BookMarkView v = (BookMarkView) BrowserActivator.getView(getViewSite().getWorkbenchWindow(),BookMarkView.ID);
		if(v != null) {
			v.addBookmark(url);
		}
	}
	
	void locChanged(LocationEvent event) {
		urlCombo.setText(event.location);
	}
	
	
	// got the higher level understanding still need to investigate this function 
	private void onProgress(ProgressEvent event) {
		if(event.total == 0)
			return;
		int ratio = event.current * 100 / event.total;
		statusLine.getProgressMonitor().worked(ratio);
	}
	
	
	//understood this function no need to check
	public void navigateTo(String url) {
        browser.setUrl(url);
    }
	

	
	
	

}
