package com.vaadin.cdi.example.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import javax.annotation.PostConstruct;
import org.vaadin.maddon.label.RichText;
import org.vaadin.maddon.layouts.MVerticalLayout;

/**
 *
 * @author Matti Tahvonen <matti@vaadin.com>
 */
@CDIView("error")
public class ErrorView extends MVerticalLayout implements View {

    @PostConstruct
    public void initComponent() {
        addComponents(
                new RichText().withMarkDownResource("/error.md")
        );
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // TODO, figure out how to get the error that caused this view to 
        // be instantiated
    }

}
