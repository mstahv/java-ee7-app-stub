package com.vaadin.cdi.example.util;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import org.vaadin.maddon.button.MButton;
import org.vaadin.maddon.layouts.MHorizontalLayout;

/**
 * A helper to automatically create a menu from available Vaadin CDI view.
 * 
 * You'll probably want something more sophisticated in your app, but this might 
 * be handy prototyping small CRUD apps.
 * 
 * @author Matti Tahvonen <matti@vaadin.com>
 */
@SessionScoped
public class ViewMenu implements Serializable {
    
    @Inject
    BeanManager beanManager;
    
    public Set<Bean<?>> getAvailableViews() {
        Set<Bean<?>> all = beanManager.getBeans(View.class,
                new AnnotationLiteral<Any>() {
                });
        // TODO check if accessible for current user
        return all;
    }
    
    public Component getBasicMenu() {
        return new MHorizontalLayout(getAsLinkButtons(getAvailableViews())).
                withMargin(true);
    }
    
    private Component[] getAsLinkButtons(
            Set<Bean<?>> availableViews) {
        ArrayList<Button> buttons = new ArrayList();
        for (Bean<?> viewBean : availableViews) {
            Class<?> beanClass = viewBean.getBeanClass();
            if (beanClass.getAnnotation(CDIView.class) != null) {
                buttons.add(getButtonFor(beanClass));
            }
        }
        return buttons.toArray(new Button[0]);
    }
    
    private MButton getButtonFor(Class<?> beanClass) {
        final MButton button = new MButton(getNameFor(beanClass)).withStyleName(
                "link");
        button.addClickListener(e -> {
            CDIView cdiview = beanClass.getAnnotation(CDIView.class);
            UI.getCurrent().getNavigator().navigateTo(cdiview.value());
        });
        return button;
    }
    
    protected String getNameFor(Class<?> viewType) {
        return viewType.getSimpleName();
    }
}
