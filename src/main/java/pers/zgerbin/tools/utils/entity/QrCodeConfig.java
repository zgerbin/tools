package pers.zgerbin.tools.utils.entity;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.prism.paint.Color;

public class QrCodeConfig {
    private Integer margin = 0;
    private Color onColor = Color.BLACK;
    private Color offColor = Color.WHITE;
    private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.Q;

    public Integer getMargin() {
        return margin;
    }

    public QrCodeConfig setMargin(Integer margin) {
        this.margin = margin;
        return this;
    }

    public Color getOnColor() {
        return onColor;
    }

    public QrCodeConfig setOnColor(Color onColor) {
        this.onColor = onColor;
        return this;
    }

    public Color getOffColor() {
        return offColor;
    }

    public QrCodeConfig setOffColor(Color offColor) {
        this.offColor = offColor;
        return this;
    }

    public ErrorCorrectionLevel getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    public QrCodeConfig setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
        return this;
    }
}
