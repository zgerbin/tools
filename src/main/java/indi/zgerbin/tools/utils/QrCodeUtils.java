package indi.zgerbin.tools.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.prism.paint.Color;
import indi.zgerbin.tools.utils.entity.QrCodeConfig;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.ceil;

public class QrCodeUtils {

    private static int onColor;
    private static int offColor;
    private static Font font;

    public static BufferedImage createSimpleQrCode(Integer qrCodeSize, String content, QrCodeConfig config) throws WriterException, IOException {
        return createQrCode(qrCodeSize, content, null, null, null, null, config);
    }

    public static BufferedImage createQrCodeWithLogo(Integer qrCodeSize, String content, Integer logoSize, String logoPath, QrCodeConfig config) throws WriterException, IOException {
        return createQrCode(qrCodeSize, content, logoSize, logoPath, null, null, config);
    }

    public static BufferedImage createQrCodeWithBottomText(Integer qrCodeSize, String content, String bottomText, Font font, QrCodeConfig config) throws WriterException, IOException {
        return createQrCode(qrCodeSize, content, null, null, bottomText, font, config);
    }

    public static BufferedImage createQrCode(Integer qrCodeSize, String content, Integer logoSize, String logoPath, String bottomText, Font font, QrCodeConfig config) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        onColor = Color.BLACK.getIntArgbPre();
        offColor = Color.WHITE.getIntArgbPre();

        if (config != null) {
            if (config.getMargin() != null)
                hints.put(EncodeHintType.MARGIN, config.getMargin());
            if (config.getErrorCorrectionLevel() != null)
                hints.put(EncodeHintType.ERROR_CORRECTION, config.getErrorCorrectionLevel());
            if (config.getOnColor() != null) {
                onColor = config.getOnColor().getIntArgbPre();
            }
            if (config.getOffColor() != null) {
                offColor = config.getOffColor().getIntArgbPre();
            }
        }

        if (qrCodeSize == null) qrCodeSize = 200;
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(onColor, offColor);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);// 生成矩阵

        BufferedImage qrCode = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
        if (logoPath != null) {
            if (logoSize == null || logoSize < 0) logoSize = qrCodeSize / 3;
            BufferedImage qrCodeWithLogo = new BufferedImage(qrCodeSize, qrCodeSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graph = qrCodeWithLogo.createGraphics();
            graph.setColor(java.awt.Color.white);
            graph.fillRect(0, 0, qrCodeSize, qrCodeSize);
            graph.drawImage(qrCode, 0, 0, qrCodeSize, qrCodeSize, null);

            BufferedImage logo = null;
            try {
                logo = ImageIO.read(new File(logoPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image image = logo.getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);

            graph.drawImage(image, qrCodeSize * 2 / 5, qrCodeSize * 2 / 5, image.getWidth(null), image.getHeight(null), null);
            graph.dispose();
            qrCodeWithLogo.flush();
            qrCode = qrCodeWithLogo;
        }
        if (bottomText != null) {
            if (font == null) {
                font = new Font("宋体", Font.BOLD, 17);
            }
            FontMetrics fm = FontDesignMetrics.getMetrics(font);
            int wordheight = fm.getHeight();
            int strWidth = fm.stringWidth(bottomText);
            int height = new Double(qrCodeSize + ceil(wordheight / 1.4)).intValue();
            //总长度减去文字长度的一半  （居中显示）
            BufferedImage qrCodeWithText = new BufferedImage(qrCodeSize, height + 2, BufferedImage.TYPE_INT_ARGB);

            Graphics2D graph = qrCodeWithText.createGraphics();
            graph.setColor(new java.awt.Color(offColor));
            graph.fillRect(0, 0, qrCodeWithText.getWidth(), qrCodeWithText.getHeight());
            graph.drawImage(qrCode, 0, 0, qrCodeSize, qrCodeSize, null);
            // 画文字到新的面板
            graph.setColor(java.awt.Color.BLACK);
            graph.setFont(font);
            int wordStartX = (qrCodeSize - strWidth) / 2;
            int wordStartY = height;

            // 画文字
            graph.drawString(bottomText, wordStartX, wordStartY);
            graph.dispose();
            qrCodeWithText.flush();
            qrCode = qrCodeWithText;
        }
        return qrCode;
    }
}
