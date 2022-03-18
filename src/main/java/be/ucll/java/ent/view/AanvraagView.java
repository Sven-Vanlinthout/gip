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
import java.util.ArrayList;
import java.util.List;

public class AanvraagView extends VerticalLayout {

    private final AanvraagController aanvraagController;
    private SplitLayout splitLayout;
    private VerticalLayout lpvLayout; // Left Panel Vertical Layout
    private HorizontalLayout lphLayout;
    private VerticalLayout rpvLayout; // Right Panel Vertical Layout
    private HorizontalLayout rphLayout;
    private AanvraagFragment frm;

    private Label lblNaam;
    private TextField txtNaam;

    private Grid<AanvraagDTO> grid;

    private Button btnCancel;
    private Button btnDelete;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public AanvraagView() {
        super();

        // Load Spring Beans via a utility class
        // We can't use @Autowired because Vaadin Views are preferably NOT declared as SpringComponent
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
        grid.setItems(new ArrayList<AanvraagDTO>(0));
        grid.addColumn(AanvraagDTO::getProductNaam).setHeader("Productnaam").setSortable(true);
        grid.addColumn(AanvraagDTO::getNaamUser).setHeader("Usernaam").setSortable(true);
        grid.addColumn(AanvraagDTO::getDatum).setHeader("Datum nodig").setSortable(true);



        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));

        lpvLayout.add(lphLayout);
        lpvLayout.add(grid);
        lpvLayout.setWidth("70%");
        return lpvLayout;
    }

    private Component createEditorLayout() {
        rpvLayout = new VerticalLayout();

        frm = new AanvraagFragment();

        rphLayout = new HorizontalLayout();
        rphLayout.setWidthFull();
        rphLayout.setSpacing(true);

        btnCancel = new Button("Annuleren");
        btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnCancel.addClickListener(e -> handleClickCancel(e));

        btnDelete = new Button("Verwijderen");
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnDelete.addClickListener(e -> handleClickDelete(e));
        btnDelete.setVisible(false);

        rphLayout.add(btnCancel, btnDelete);

        rpvLayout.add(frm);
        rpvLayout.add(rphLayout);
        rpvLayout.setWidth("30%");

        return rpvLayout;
    }

    public void loadData() {
        if (aanvraagController != null) {
            List<AanvraagDTO> lst = aanvraagController.getAllStudents();
            grid.setItems(lst);
        } else {
            System.err.println("Autowiring failed");
        }
    }

    private void handleClickSearch(ClickEvent event) {
        if (txtNaam.getValue().trim().length() == 0) {
            grid.setItems(aanvraagController.getAllStudents());
        } else {
            try {
                grid.setItems(aanvraagController.getAanvragen(txtNaam.getValue().trim()));
            } catch (IllegalArgumentException e) {
                Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        }
    }

    private void handleClickCancel(ClickEvent event) {
        grid.asSingleSelect().clear();
        frm.resetForm();
        btnDelete.setVisible(false);
    }




    private void handleClickDelete(ClickEvent event) {
        try {
            aanvraagController.deleteAanvraag(Integer.parseInt(frm.lblID.getText()));
            Notification.show("Aanvraag verwijderd", 3000, Notification.Position.TOP_CENTER);
        } catch (IllegalArgumentException e) {
            Notification.show("Het is NIET mogelijk de aanvraag te verwijderen", 5000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        frm.resetForm();
        handleClickSearch(null);
        btnDelete.setVisible(false);
    }

    private void populateForm(AanvraagDTO p) {
        btnDelete.setVisible(true);

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

