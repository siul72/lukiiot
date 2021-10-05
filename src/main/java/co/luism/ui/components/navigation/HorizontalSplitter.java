package co.luism.ui.components.navigation;

import com.vaadin.flow.component.html.Span;

public class HorizontalSplitter extends Span {
    public HorizontalSplitter() {

        getStyle().set("background-color", "blue");
        getStyle().set("flex", "0 0 2px");
        getStyle().set("align-self", "stretch");
    }

}
