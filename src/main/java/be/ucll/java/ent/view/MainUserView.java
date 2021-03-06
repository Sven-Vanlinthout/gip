package be.ucll.java.ent.view;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import javax.annotation.PostConstruct;

@Route("MainUser")
@PageTitle("Inventory management")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainUserView extends AppLayout {
    private UserProductView pView;

    private Tab tab1;
    private static final String TABNAME1 = "stock";
    private Tab tab2;
    private static final String TABNAME2 = "Afmelden";
    private Tabs tabs;
    public MainUserView() {
        // Header / Menu bar on the top of the page
        H3 header = new H3("Inventory Managment");
        addToNavbar(new DrawerToggle(),
                new Html("<span>&nbsp;&nbsp;</span>"),
                header,
                new Html("<span>&nbsp;&nbsp;</span>"));

        tab1 = new Tab(TABNAME1);
        tab2 = new Tab(TABNAME2);

        tabs = new Tabs(tab1, tab2);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addSelectedChangeListener(event -> {
            handleTabClicked(event);
        });
        addToDrawer(tabs);
    }

    @PostConstruct
    private void setMainViewContent() {
        pView = new UserProductView();
        pView.loadData();

        this.setContent(pView);
    }

    private void handleTabClicked(Tabs.SelectedChangeEvent event) {
        Tab selTab = tabs.getSelectedTab();
        if (selTab.getLabel() != null) {
            if (selTab.getLabel().equals(TABNAME1)) {
                setContent(pView);
            }
            if (selTab.getLabel().equals(TABNAME2)) {
                UI.getCurrent().navigate(LoginView.class);
            }
            else {
                setContent(new Label("Te implementeren scherm voor Admins only"));
            }
        }
    }
}
