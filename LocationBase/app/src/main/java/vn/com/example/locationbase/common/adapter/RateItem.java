package vn.com.example.locationbase.common.adapter;

public class RateItem {
    private float value;
    private String text;

    public RateItem(float value, String text) {
        this.value = value;
        this.text = text;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
