package cn.yami.micro_sparrow;

public enum Config {

    SECRET_PASSWORD("KISSqklxzl520");

    private String value;

    Config(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
