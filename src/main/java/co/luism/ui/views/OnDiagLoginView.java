package co.luism.ui.views;


import co.luism.backend.enterprise.User;
import co.luism.common.PageElementId;
//import co.luism.diagnostics.webmanager.LanguageManager;
import co.luism.ui.MainView;
import co.luism.ui.components.FlexBoxLayout;
import co.luism.ui.util.UIUtils;
import co.luism.ui.util.ValidTextField;
import co.luism.ui.util.WebPageEnum;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.GeneratedVaadinTextField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import org.slf4j.LoggerFactory;


import java.util.logging.Logger;


@SuppressWarnings("serial")
@PageTitle("Login")
@Route(value = "Login", layout = MainView.class)
public class OnDiagLoginView extends ViewFrame implements ComponentEventListener<ClickEvent<Button>> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(OnDiagLoginView.class);
    public static final WebPageEnum NAME = WebPageEnum.login;
    private TextField user = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button loginButton = new Button();
    private VerticalLayout fieldsLayout;
    //private PasswordRecovery passwordRecovery;

    public OnDiagLoginView() {

        setViewContent(createContent());
        user.setPattern("^[\\w]{3,16}$");
        passwordField.setPattern("^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&?.]).*$");
    }



    public Component createContent() {

        Component c = buildLayout();
        FlexBoxLayout content = new FlexBoxLayout(c);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        return content;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
//        getUI().ifPresent(ui -> {
//            Page page = ui.getPage();
//
//        });

        MainView.get().getAppBar().hide();

    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {

        super.onDetach(detachEvent);
    }

    private void loginClicked() {
        //UIUtils.showNotification("Not implemented yet.");

        if (user.isInvalid() || !passwordField.isInvalid()) {

            UIUtils.showNotification("Wrong user or password.");
            return;
        }

        String username = user.getValue();
        String password = this.passwordField.getValue();
        //IWebManagerFacade myFacade = WebManagerFacade.getInstance();
        //boolean isValid = (myFacade.validateUser(username, password) == ReturnCode.RET_OK) ? true : false;
        boolean isValid = true;
        if (isValid) {


            //User myUser = WebManagerFacade.getInstance().getUser(username);
            User myUser = new User();
            if (myUser == null) {
                log.error(String.format("The current user login %s was not found in the system", username));
                //UIUtils.showNotification(LanguageManager.getInstance().getValue("User Not Found!"), Notification.Type.HUMANIZED_MESSAGE);
                UIUtils.showNotification("User Not Found!");
                return;
            }
            getUI().get().getSession().setAttribute(User.class, myUser);

            // Navigate to main view
            String goTo = "home";
            // if (OnDiagPermissionManager.grantAdminAccess(myUser)) {
            //    goTo = OnDiagAdministrationView.NAME.getValue();
            //} else {
            //   goTo = OnDiagMainView.NAME.getValue();
            //}

            getUI().get().navigate(goTo);
            log.debug("go to: " + goTo);

        } else {

            Notification.show("WRONG_PASSWORD_TEXT");
            // Wrong passwordField clear the passwordField field and refocuses it
            this.passwordField.setValue(null);
            this.passwordField.focus();



        }



    }


    public void updateLanguageGui(String currentLanguage) {
       // LanguageManager.getInstance().setCurrentLanguage(currentLanguage);

        String fCaption;
//        if(passwordRecovery.isEnabled()){
//            fCaption = LanguageManager.getInstance().getValue("RESET_PASS_INSTRUCTIONS");
//        } else {
//            fCaption = LanguageManager.getInstance().getValue("PLEASE_LOGIN_TEXT");
//        }
//        fieldsLayout.setCaption(fCaption);
          //user.setCaption(LanguageManager.getInstance().getValue("USER_NAME_LABEL"));
          //user.setInputPrompt(LanguageManager.getInstance().getValue("USER_NAME_PROMPT"));
          //passwordField.setCaption(LanguageManager.getInstance().getValue("USER_PASSWORD_LABEL"));
          //loginButton.setCaption(LanguageManager.getInstance().getValue("LOGIN_BUTTON"));

    }

    public WebPageEnum getNAME() {
        return NAME;
    }


    public Component buildLayout() {

        // Create the user input field
        user.setId(PageElementId.USER_NAME_TEXT_FIELD);
        user.setWidth("300px");
        user.setRequired(true);
        user.setLabel("UserName");

        // Create login button
        loginButton.getElement().setAttribute("id", PageElementId.LOGIN_BNT);
        loginButton.addClickListener(this);
        loginButton.setText("Login");
        //loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // Create the pass filed
        passwordField.setId(PageElementId.USER_PASSWORD_TEXTFIELD);
        passwordField.setWidth("300px");
        //passwordField.addValidator(new PasswordValidator());
        passwordField.setRequired(true);
        passwordField.setValue("");
        passwordField.setLabel("password");
        //passwordField.setNullRepresentation("");

//        passwordField.addFocusListener(new FieldEvents.FocusListener() {
//            @Override
//            public void focus(FieldEvents.FocusEvent event) {
//                loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
//            }
//        });

        // Add both to a panel
        fieldsLayout = new VerticalLayout(user, passwordField, loginButton);
        fieldsLayout.setSpacing(true);
        //fieldsLayout.setMargin(new MarginInfo(true, true, true, false));
        fieldsLayout.setSizeUndefined();

        // The view root layout
        //VerticalLayout viewLayout = new VerticalLayout();
        //viewLayout.setSizeFull();
        //Component header = new PageHeader(this);
        //updateLanguageGui(LanguageManager.getInstance().getCurrentLanguage());

        //viewLayout.addComponent(header);
        //viewLayout.addComponent(fieldsLayout);
        //viewLayout.setComponentAlignment(fieldsLayout, Alignment.MIDDLE_CENTER);
        //viewLayout.setExpandRatio(fieldsLayout, 1);
        //setCompositionRoot(viewLayout);
        return fieldsLayout;
    }


    @Override
    public void onComponentEvent(ClickEvent<Button> event) {
        Button b = event.getSource();
        String s = b.getElement().getAttribute("id");
        if (s.equals(PageElementId.LOGIN_BNT.toString())) {
            this.loginClicked();
        }
    }
}

