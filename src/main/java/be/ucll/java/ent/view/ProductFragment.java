package be.ucll.java.ent.view;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.Locale;

public class ProductFragment extends FormLayout {
    // Public fields for ease of access
    public Label lblID;
    public TextField txtProductNaam;
    public ProductFragment() {
        super();

        lblID = new Label("");

        txtProductNaam = new TextField();
        txtProductNaam.setRequired(true);
        txtProductNaam.setMaxLength(128);
        txtProductNaam.setErrorMessage("Verplicht veld");


        addFormItem(txtProductNaam, "productnaam");
    }

    public void resetForm() {
        lblID.setText("");
        txtProductNaam.clear();
        txtProductNaam.setInvalid(false);
    }

    public boolean isformValid() {
        boolean result = true;
        if (txtProductNaam.getValue() == null) {
            txtProductNaam.setInvalid(true);
            result = false;
        }
        if (txtProductNaam.getValue().trim().length() == 0) {
            txtProductNaam.setInvalid(true);
            result = false;
        }
        return result;
    }
}
