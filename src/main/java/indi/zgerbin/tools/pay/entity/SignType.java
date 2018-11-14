package indi.zgerbin.tools.pay.entity;

public enum SignType {
    RSA2("RSA2"), SA("SA");

    private final String value;

    private SignType(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
