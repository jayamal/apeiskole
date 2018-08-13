package com.jk.schoo.management.spring.common.ui.crudui;

import com.vaadin.flow.component.combobox.ComboBox;
import org.vaadin.crudui.form.impl.field.provider.AbstractListingProvider;

import java.util.Collection;

/**
 * Created by jayamalk on 7/28/2018.
 */
public class DependentComboBoxProvider<T> extends AbstractListingProvider<ComboBox<T>, T> {

    private ComboBox<T> comboBox;

    public DependentComboBoxProvider(Collection<T> items, ComboBox<T> comboBox) {
        super(items);
    }

    @Override
    protected ComboBox<T> buildAbstractListing() {
        return null;
    }
}
