package be.ucll.java.ent.view;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.Locale;

public class UserFragment extends FormLayout {

    // Public fields for ease of access
    public Label lblID;
    public TextField txtVoornaam;
    public TextField txtNaam;
    public DatePicker datGeboorte;
    public TextField txtAdres;
    public TextField txtTelefoon;

    public UserFragment() {
        super();

        lblID = new Label("");

        txtVoornaam = new TextField();
        txtVoornaam.setRequired(true);
        txtVoornaam.setMaxLength(128);
        txtVoornaam.setErrorMessage("Verplicht veld");

        txtNaam = new TextField();
        txtNaam.setRequired(true);
        txtNaam.setMaxLength(128);
        txtNaam.setErrorMessage("Verplicht veld");

        txtAdres = new TextField();
        txtAdres.setRequired(true);
        txtAdres.setMaxLength(128);
        txtAdres.setErrorMessage("Verplicht veld");

        txtTelefoon = new TextField();
        txtTelefoon.setRequired(true);
        txtTelefoon.setMaxLength(128);
        txtTelefoon.setErrorMessage("Verplicht veld");


        datGeboorte = new DatePicker();
        LocalDate now = LocalDate.now();
        datGeboorte.setPlaceholder("dd/mm/jjjj");
        //datGeboorte.setValue(now);
        datGeboorte.setMin(now.minusYears(100));
        datGeboorte.setMax(now);
        datGeboorte.setRequired(true);
        datGeboorte.addInvalidChangeListener(e -> datGeboorte.setErrorMessage("Verplicht veld. Ongeldig datumformaat of datum in de toekomst"));
        datGeboorte.setLocale(new Locale("nl", "BE"));
        datGeboorte.setClearButtonVisible(true);

        addFormItem(txtVoornaam, "Voornaam");
        addFormItem(txtNaam, "Naam");
        addFormItem(datGeboorte, "Geboortedatum");
        addFormItem(txtAdres, "Adres");
        addFormItem(txtTelefoon, "Telefoon");
    }

    public void resetForm() {
        lblID.setText("");
        txtVoornaam.clear();
        txtVoornaam.setInvalid(false);
        txtNaam.clear();
        txtNaam.setInvalid(false);
        datGeboorte.clear();
        datGeboorte.setInvalid(false);
        txtAdres.clear();
        txtAdres.setInvalid(false);
        txtTelefoon.clear();
        txtTelefoon.setInvalid(false);
    }

    public boolean isformValid() {
        boolean result = true;
        if (txtNaam.getValue() == null) {
            txtNaam.setInvalid(true);
            result = false;
        }
        if (txtNaam.getValue().trim().length() == 0) {
            txtNaam.setInvalid(true);
            result = false;
        }
        if (txtVoornaam.getValue() == null) {
            txtVoornaam.setInvalid(true);
            result = false;
        }
        if (txtVoornaam.getValue().trim().length() == 0) {
            txtVoornaam.setInvalid(true);
            result = false;
        }
        if (txtAdres.getValue() == null) {
            txtAdres.setInvalid(true);
            result = false;
        }
        if (txtAdres.getValue().trim().length() == 0) {
            txtAdres.setInvalid(true);
            result = false;
        }
        if (txtTelefoon.getValue() == null) {
            txtTelefoon.setInvalid(true);
            result = false;
        }
        if (txtTelefoon.getValue().trim().length() == 0) {
            txtTelefoon.setInvalid(true);
            result = false;
        }
        if (datGeboorte.getValue() == null) {
            datGeboorte.setInvalid(true);
            result = false;
        }
        return result;
    }
}
