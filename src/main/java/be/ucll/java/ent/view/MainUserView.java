package be.ucll.java.ent.view;

import com.vaadin.flow.component.Html;
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
@PageTitle("StuBS")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainUserView extends AppLayout {
    // Content views
    private UsersView uView;
    private ProductView pView;

    // Left navigation tabs
    private Tab tab1;
    private static final String TABNAME1 = "stock";
    private Tabs tabs;
    public MainUserView() {
        // Header / Menu bar on the top of the page
        H3 header = new H3("Inventory Managment");

        addToNavbar(new DrawerToggle(),
                new Html("<span>&nbsp;&nbsp;</span>"),
                header,
                new Html("<span>&nbsp;&nbsp;</span>"));

        // Tabs on the left side drawer
        tab1 = new Tab(TABNAME1);


        tabs = new Tabs(tab1);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addSelectedChangeListener(event -> {
            handleTabClicked(event);
        });
        addToDrawer(tabs);
    }

    @PostConstruct
    private void setMainViewContent() {
        pView = new ProductView();
        pView.loadData();

        // As default load the studentenview
        this.setContent(pView);
    }

    private void handleTabClicked(Tabs.SelectedChangeEvent event) {
        Tab selTab = tabs.getSelectedTab();
        if (selTab.getLabel() != null) {
            if (selTab.getLabel().equals(TABNAME1)) {
                setContent(pView);
            }
            else {
                setContent(new Label("Te implementeren scherm voor Admins only"));
            }
        }
    }
}
