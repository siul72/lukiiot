package co.luism.ui.util;



public enum WebPageEnum {

    none("empty"), login("Login"), main(""),
    admin("admin"), rpass("prp");

    private final String value;

    private WebPageEnum(String value) {
        this.value = value;


    }

    public String getValue() {
        return value;
    }

}
