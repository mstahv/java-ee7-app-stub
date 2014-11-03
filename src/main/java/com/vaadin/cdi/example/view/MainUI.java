package com.vaadin.cdi.example.view;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.example.util.ViewMenu;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;

/**
 *
 * @author Matti Tahvonen <matti@vaadin.com>
 */
@CDIUI("")
@Theme("valo")
public class MainUI extends UI {
    
    @Inject
    private CDIViewProvider viewProvider;
    
    @Inject
    ViewMenu menu;

    @Override
    public void init(VaadinRequest request) {
        
        VerticalLayout mainarea = new VerticalLayout();
        
        Navigator navigator = new Navigator(this, mainarea);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(ErrorView.class);
        
        setContent(new VerticalLayout(menu.getBasicMenu(), mainarea));
    }    
}
