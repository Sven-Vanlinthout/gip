package be.ucll.java.ent.view;

import be.ucll.java.ent.controller.UserController;
import be.ucll.java.ent.utils.BeanUtil;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;

@Route("")
public class LoginView extends Composite<LoginOverlay> {
    private final UserController userController;
    public LoginView() {
        userController = BeanUtil.getBean(UserController.class);
        LoginOverlay loginOverlay = getContent();
        loginOverlay.setTitle("Inventory");
        loginOverlay.setDescription("");
        loginOverlay.setOpened(true);

        loginOverlay.addLoginListener(event ->{
            if("admin".equals(event.getUsername())){UI.getCurrent().navigate(MainView.class);}
            else{UI.getCurrent().navigate(MainUserView.class);}
        });
    }
}