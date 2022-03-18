package be.ucll.java.ent.view;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.Locale;

public class AanvraagFragment extends FormLayout {
    // Public fields for ease of access
    public Label lblID;
    public TextField txtProductNaam;
    public TextField txtUsernaam;
    public DatePicker Datum;
    public AanvraagFragment() {
        super();

        lblID = new Label("");

        txtProductNaam = new TextField();
        txtProductNaam.setRequired(true);
        txtProductNaam.setMaxLength(128);
        txtProductNaam.setErrorMessage("Verplicht veld");

        txtUsernaam = new TextField();
        txtUsernaam.setRequired(true);
        txtUsernaam.setMaxLength(128);

        Datum = new DatePicker();
        LocalDate now = LocalDate.now();
        Datum.setPlaceholder("dd/mm/jjjj");
        //datGeboorte.setValue(now);
        Datum.setMin(now.minusYears(100));
        Datum.setMax(now);
        Datum.setRequired(true);
        Datum.addInvalidChangeListener(e -> Datum.setErrorMessage("Verplicht veld. Ongeldig datumformaat of datum in de toekomst"));
        Datum.setLocale(new Locale("nl", "BE"));
        Datum.setClearButtonVisible(true);

        addFormItem(txtProductNaam, "productnaam");
        addFormItem(txtUsernaam, "user");
        addFormItem(Datum, "datum nodig");
    }

    public void resetForm() {
        lblID.setText("");
        txtProductNaam.clear();
        txtProductNaam.setInvalid(false);
        txtUsernaam.clear();
        txtUsernaam.setInvalid(false);
        Datum.clear();
        Datum.setInvalid(false);
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
