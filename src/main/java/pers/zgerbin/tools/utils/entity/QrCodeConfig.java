package pers.zgerbin.tools.utils.entity;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.prism.paint.Color;


public class QrCodeConfig {

    private ErrorCorrectionLevel errorCorrectionLevel;
    private Integer margin;
    private Color onColor;
    private Color offColor;

    public ErrorCorrectionLevel getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    public void setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
    }

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    public Color getOnColor() {
        return onColor;
    }

    public void setOnColor(Color onColor) {
        this.onColor = onColor;
    }

    public Color getOffColor() {
        return offColor;
    }

    public void setOffColor(Color offColor) {
        this.offColor = offColor;
    }
}
