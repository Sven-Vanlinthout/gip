package be.ucll.java.ent.view;

import be.ucll.java.ent.controller.AanvraagController;
import be.ucll.java.ent.controller.ProductController;
import be.ucll.java.ent.domain.AanvraagDTO;
import be.ucll.java.ent.domain.ProductDTO;
import be.ucll.java.ent.utils.BeanUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserProductView extends VerticalLayout {

    private final ProductController productController;
    private final AanvraagController aanvraagController;
    private SplitLayout splitLayout;
    private VerticalLayout lpvLayout;
    private HorizontalLayout lphLayout;
    private VerticalLayout rpvLayout;
    private HorizontalLayout rphLayout;
    private UserProductFragment frm;

    private Label lblNaam;
    private TextField txtNaam;

    private Grid<ProductDTO> grid;

    private Button btnCancel;
    private Button btnCreate;
    private Button btnUpdate;
    private Button btnDelete;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public UserProductView() {
        super();

        productController = BeanUtil.getBean(ProductController.class);
        aanvraagController = BeanUtil.getBean(AanvraagController.class);

        this.setSizeFull();
        this.setPadding(false);

        splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.addToPrimary(createGridLayout());
        splitLayout.addToSecondary(createEditorLayout());
        add(splitLayout);
    }

    private Component createGridLayout() {
        lpvLayout = new VerticalLayout();
        lpvLayout.setWidthFull();

        lphLayout = new HorizontalLayout();
        lblNaam = new Label("Naam");
        txtNaam = new TextField();
        txtNaam.setValueChangeMode(ValueChangeMode.EAGER);
        txtNaam.addValueChangeListener(e -> handleClickSearch(null));
        lphLayout.add(lblNaam);
        lphLayout.add(txtNaam);

        grid = new Grid<>();
        grid.setItems(new ArrayList<ProductDTO>(0));
        grid.addColumn(ProductDTO::getProductNaam).setHeader("Productnaam").setSortable(true);



        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));

        lpvLayout.add(lphLayout);
        lpvLayout.add(grid);
        lpvLayout.setWidth("70%");
        return lpvLayout;
    }

    private Component createEditorLayout() {
        rpvLayout = new VerticalLayout();

        frm = new UserProductFragment();

        rphLayout = new HorizontalLayout();
        rphLayout.setWidthFull();
        rphLayout.setSpacing(true);

        btnCancel = new Button("Annuleren");
        btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnCancel.addClickListener(e -> handleClickCancel(e));

        btnCreate = new Button("Toevoegen");
        btnCreate.addClickListener(e -> handleClickCreate(e));
        btnCreate.setVisible(false);


        rphLayout.add(btnCancel, btnCreate);

        rpvLayout.add(frm);
        rpvLayout.add(rphLayout);
        rpvLayout.setWidth("30%");

        return rpvLayout;
    }

    public void loadData() {
        if (productController != null) {
            List<ProductDTO> lst = productController.getAllStudents();
            grid.setItems(lst);
        } else {
            System.err.println("Autowiring failed");
        }
    }

    private void handleClickSearch(ClickEvent event) {
        if (txtNaam.getValue().trim().length() == 0) {
            grid.setItems(productController.getAllStudents());
        } else {
            try {
                grid.setItems(productController.getStudents(txtNaam.getValue().trim()));
            } catch (IllegalArgumentException e) {
                Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        }
    }

    private void handleClickCancel(ClickEvent event) {
        grid.asSingleSelect().clear();
        frm.resetForm();
        btnCreate.setVisible(false);
    }

    private void handleClickCreate(ClickEvent event) {
        if (!frm.isformValid()) {
            Notification.show("Er zijn validatiefouten", 3000, Notification.Position.MIDDLE);
            return;
        }

        try {
            Date d = Date.from(frm.Datum.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            AanvraagDTO a = new AanvraagDTO(0,frm.txtUserNaam.getValue(), frm.txtProductNaam.getValue(), d);
            long i = aanvraagController.createAanvraag(a);

            Notification.show("Aanvraag aangemaakt (id: " + i + ")", 3000, Notification.Position.TOP_CENTER);
            frm.resetForm();
            handleClickSearch(null);
            btnCreate.setVisible(false);
        } catch (IllegalArgumentException e) {
            Notification.show(e.getMessage(), 5000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }


    private void populateForm(ProductDTO p) {
        btnCreate.setVisible(true);

        if (p != null) {
            // Copy the ID in a hidden field
            frm.lblID.setText("" + p.getId());
            if (p.getProductNaam() != null) {
                frm.txtProductNaam.setValue(p.getProductNaam());
            } else {
                frm.txtProductNaam.setValue("");
            }

        }
    }
}
