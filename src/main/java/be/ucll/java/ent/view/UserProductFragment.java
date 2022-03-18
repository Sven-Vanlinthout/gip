package be.ucll.java.ent.view;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.Locale;

public class UserProductFragment extends FormLayout {

    public Label lblID;
    public TextField txtProductNaam;
    public TextField txtUserNaam;
    public DatePicker Datum;
    public UserProductFragment() {
        super();

        lblID = new Label("");

        txtProductNaam = new TextField();
        txtProductNaam.setRequired(true);
        txtProductNaam.setMaxLength(128);
        txtProductNaam.setErrorMessage("Verplicht veld");
        txtProductNaam.setReadOnly(true);

        txtUserNaam = new TextField();
        txtUserNaam.setRequired(true);
        txtUserNaam.setMaxLength(128);
        txtUserNaam.setErrorMessage("Verplicht veld");

        Datum = new DatePicker();
        LocalDate now = LocalDate.now();
        Datum.setPlaceholder("dd/mm/jjjj");
        //datGeboorte.setValue(now);
        Datum.setMin(now);
        Datum.setRequired(true);
        Datum.addInvalidChangeListener(e -> Datum.setErrorMessage("Verplicht veld. Ongeldig datumformaat of datum in de toekomst"));
        Datum.setLocale(new Locale("nl", "BE"));
        Datum.setClearButtonVisible(true);



        addFormItem(txtProductNaam, "productnaam");
        addFormItem(txtUserNaam, "Usernaam");
        addFormItem(Datum, "datum nodig");
    }

    public void resetForm() {
        lblID.setText("");
        txtProductNaam.clear();
        txtProductNaam.setInvalid(false);
        txtUserNaam.clear();
        txtUserNaam.setInvalid(false);
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
