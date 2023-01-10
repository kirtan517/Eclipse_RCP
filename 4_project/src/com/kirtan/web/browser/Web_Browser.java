package com.kirtan.web.browser;

import org.eclipse.ui.IPageLayout;

import org.eclipse.ui.IPerspectiveFactory;

import com.kirtan.web.view.BookMarkView;
import com.kirtan.web.view.WebBrowserView;

public class Web_Browser implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		// TODO Auto-generated method stub
		String editorialArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
//		layout.addView(BookMarkView.ID, IPageLayout.LEFT,0.2f,editorialArea);
		layout.addView(WebBrowserView.ID, IPageLayout.LEFT,0.8f,editorialArea);
	}

}
