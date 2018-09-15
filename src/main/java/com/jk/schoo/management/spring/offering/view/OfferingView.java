package com.jk.schoo.management.spring.offering.view;

import com.jk.schoo.management.spring.MainView;
import com.jk.schoo.management.spring.offering.domain.Category;
import com.jk.schoo.management.spring.offering.domain.Offering;
import com.jk.schoo.management.spring.offering.service.CategoryRepository;
import com.jk.schoo.management.spring.offering.service.OfferingRepository;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.GeneratedVaadinComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.AbstractListingProvider;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jayamalk on 9/5/2018.
 */
@Route(value = "offering", layout = MainView.class)
@UIScope
public class OfferingView extends VerticalLayout{

    private ComboBox<Category> categoryComboBox;

    @Autowired
    public OfferingView(OfferingRepository offeringRepository, CategoryRepository categoryRepository) {
        setPadding(Boolean.FALSE);
        setMargin(Boolean.FALSE);
        setSpacing(Boolean.FALSE);
        GridCrud<Offering> crudOfferingGrid = new GridCrud<>(Offering.class);
        crudOfferingGrid.getCrudFormFactory().setVisibleProperties(Offering.OFFERING_TYPE, Offering.CATEGORY, Offering.IS_SALE, Offering.SALES_DESCRIPTION, Offering.SALES_PRICERATE);
        crudOfferingGrid.getCrudFormFactory().setUseBeanValidation(true);
        add(crudOfferingGrid);
        setSizeFull();
        crudOfferingGrid.setSizeFull();
        //layout setup
        crudOfferingGrid.setCrudListener(new CrudListener<Offering>() {
            @Override
            public Collection<Offering> findAll() {
                return offeringRepository.findAll();
            }

            @Override
            public Offering add(Offering offering) {
                return offeringRepository.save(offering);
            }

            @Override
            public Offering update(Offering offering) {
                return offeringRepository.save(offering);
            }

            @Override
            public void delete(Offering offering) {
                offeringRepository.delete(offering);
            }
        });
        crudOfferingGrid.getCrudFormFactory().setFieldProvider(Offering.CATEGORY, new AbstractListingProvider<ComboBox<Category>, Category>(categoryRepository.findAll()) {
            @Override
            protected ComboBox<Category> buildAbstractListing() {
                categoryComboBox = new ComboBox<Category>();
                categoryComboBox.setAllowCustomValue(Boolean.TRUE);
                categoryComboBox.setItems(items != null ? items : new ArrayList<Category>());
                categoryComboBox.setItemLabelGenerator(new ItemLabelGenerator<Category>() {
                    @Override
                    public String apply(Category category) {
                        return category.toString();
                    }
                });
                categoryComboBox.addCustomValueSetListener(new ComponentEventListener<GeneratedVaadinComboBox.CustomValueSetEvent<ComboBox<Category>>>() {
                    @Override
                    public void onComponentEvent(GeneratedVaadinComboBox.CustomValueSetEvent<ComboBox<Category>> comboBoxCustomValueSetEvent) {
                        String detail = comboBoxCustomValueSetEvent.getDetail();
                        if(detail != null || !detail.isEmpty()){
                            Category category = new Category();
                            category.setName(detail);
                            Category categorySaved = categoryRepository.save(category);
                            categoryComboBox.setItems(categoryRepository.findAll());
                            categoryComboBox.setValue(categorySaved);
                        }
                    }
                });
                return categoryComboBox;
            }
        });
    }
}
