package co.luism.ui.views.personnel;

import co.luism.backend.DummyData;
import co.luism.backend.Person;
import co.luism.ui.MainView;
import co.luism.ui.components.FlexBoxLayout;
import co.luism.ui.components.Initials;
import co.luism.ui.components.ListItem;
import co.luism.ui.components.detailsdrawer.DetailsDrawer;
import co.luism.ui.components.detailsdrawer.DetailsDrawerFooter;
import co.luism.ui.components.detailsdrawer.DetailsDrawerHeader;
import co.luism.ui.layout.size.Horizontal;
import co.luism.ui.layout.size.Right;
import co.luism.ui.layout.size.Top;
import co.luism.ui.layout.size.Vertical;
import co.luism.ui.util.LumoStyles;
import co.luism.ui.util.UIUtils;
import co.luism.ui.util.css.BoxSizing;
import co.luism.ui.views.SplitViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "accountants", layout = MainView.class)
@PageTitle("Accountants")
public class Accountants extends SplitViewFrame {

	private Grid<Person> grid;
	private ListDataProvider<Person> dataProvider;

	private DetailsDrawer detailsDrawer;
	private DetailsDrawerHeader detailsDrawerHeader;

	public Accountants() {
		setViewContent(createContent());
		setViewDetails(createDetailsDrawer());

		filter();
	}

	private Component createContent() {
		FlexBoxLayout content = new FlexBoxLayout(createGrid());
		content.setBoxSizing(BoxSizing.BORDER_BOX);
		content.setHeightFull();
		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
		return content;
	}

	private Grid createGrid() {
		grid = new Grid<>();
		grid.addSelectionListener(event -> event.getFirstSelectedItem()
				.ifPresent(this::showDetails));
		dataProvider = DataProvider.ofCollection(DummyData.getPersons());
		grid.setDataProvider(dataProvider);
		grid.setHeightFull();

		grid.addColumn(Person::getId)
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("ID")
				.setSortable(true);
		grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
				.setAutoWidth(true)
				.setHeader("Name");
		grid.addColumn(new ComponentRenderer<>(this::createActive))
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Active")
				.setTextAlign(ColumnTextAlign.END);
		grid.addColumn(new ComponentRenderer<>(this::createInvoices))
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Invoices")
				.setTextAlign(ColumnTextAlign.END);
		grid.addColumn(new ComponentRenderer<>(this::createCompanies))
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Companies")
				.setTextAlign(ColumnTextAlign.END);
		grid.addColumn(new ComponentRenderer<>(this::createDate)).setFlexGrow(0)
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Last Report")
				.setTextAlign(ColumnTextAlign.END);

		return grid;
	}

	private Component createUserInfo(Person person) {
		ListItem item = new ListItem(
				new Initials(person.getInitials()), person.getName(),
				person.getEmail());
		item.setPadding(Vertical.XS);
		item.setSpacing(Right.M);
		return item;
	}

	private Component createActive(Person person) {
		Icon icon;
		if (person.getRandomBoolean()) {
			icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
		} else {
			icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
		}
		return icon;
	}

	private Component createInvoices() {
		return UIUtils.createAmountLabel(DummyData.getRandomInt(0, 5000));
	}

	private Component createCompanies() {
		return UIUtils.createUnitsLabel(DummyData.getRandomInt(0, 50));
	}

	private Component createDate(Person person) {
		return new Span(UIUtils.formatDate(person.getLastModified()));
	}

	private DetailsDrawer createDetailsDrawer() {
		detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

		// Header
		detailsDrawerHeader = new DetailsDrawerHeader("");
		detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
		detailsDrawer.setHeader(detailsDrawerHeader);

		// Footer
		DetailsDrawerFooter footer = new DetailsDrawerFooter();
		footer.addSaveListener(e -> {
			detailsDrawer.hide();
			UIUtils.showNotification("Changes saved.");
		});
		footer.addCancelListener(e -> detailsDrawer.hide());
		detailsDrawer.setFooter(footer);

		return detailsDrawer;
	}

	private void showDetails(Person person) {
		detailsDrawerHeader.setTitle(person.getName());
		detailsDrawer.setContent(createDetails(person));
		detailsDrawer.show();
	}

	private FormLayout createDetails(Person person) {
		TextField firstName = new TextField();
		firstName.setValue(person.getFirstName());
		firstName.setWidthFull();

		TextField lastName = new TextField();
		lastName.setValue(person.getLastName());
		lastName.setWidthFull();

		RadioButtonGroup<String> gender = new RadioButtonGroup<>();
		gender.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		gender.setItems("Active", "Inactive");
		gender.setValue(person.getRandomBoolean() ? "Active" : "Inactive");

		FlexLayout phone = UIUtils.createPhoneLayout();

		TextField email = new TextField();
		email.setValue(person.getEmail());
		email.setWidthFull();

		ComboBox company = new ComboBox();
		company.setItems(DummyData.getCompanies());
		company.setValue(DummyData.getCompany());
		company.setWidthFull();

		// Form layout
		FormLayout form = new FormLayout();
		form.addClassNames(LumoStyles.Padding.Bottom.L,
				LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
		form.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1,
						FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("21em", 2,
						FormLayout.ResponsiveStep.LabelsPosition.TOP));
		form.addFormItem(firstName, "First Name");
		form.addFormItem(lastName, "Last Name");
		FormLayout.FormItem statusItem = form.addFormItem(gender, "Status");
		FormLayout.FormItem phoneItem = form.addFormItem(phone, "Phone");
		FormLayout.FormItem emailItem = form.addFormItem(email, "Email");
		FormLayout.FormItem companyItem = form.addFormItem(company, "Company");
		FormLayout.FormItem uploadItem = form.addFormItem(new Upload(),
				"Image");
		UIUtils.setColSpan(2, statusItem, phoneItem, emailItem, companyItem,
				uploadItem);
		return form;
	}

	private void filter() {
		dataProvider.setFilterByValue(Person::getRole, Person.Role.ACCOUNTANT);
	}

}
