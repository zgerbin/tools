package pers.zgerbin.tools.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.prism.paint.Color;
import pers.zgerbin.tools.utils.entity.QrCodeConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.ceil;

public class QrCodeUtils {

    private static int onColor;
    private static int offColor;

    public static void createSimpleQrCode(Integer qrCodeSize, String content, String savePath, String name, QrCodeConfig
            config) throws WriterException, IOException {
        createQrCode(qrCodeSize, content, null, null, null, null, savePath, name, config);
    }

    public static void createQrCodeWithLogo(Integer qrCodeSize, String content, Integer logoSize, String logoPath, String
            savePath, String name, QrCodeConfig config) throws WriterException, IOException {
        createQrCode(qrCodeSize, content, logoSize, logoPath, null, null, savePath, name, config);
    }

    public static void createQrCodeWithBottomText(Integer qrCodeSize, String content, String bottomText, Font font,
                                                  String savePath, String name, QrCodeConfig config) throws WriterException, IOException {
        createQrCode(qrCodeSize, content, null, null, bottomText, font, savePath, name, config);
    }

    public static void createQrCode(Integer qrCodeSize, String content, Integer logoSize, String logoPath, String bottomText, Font font,
                                    String savePath, String name, QrCodeConfig config) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        onColor = Color.BLACK.getIntArgbPre();
        offColor = Color.WHITE.getIntArgbPre();
        if (config != null) {
            if (config.getMargin() != null)
                hints.put(EncodeHintType.MARGIN, config.getMargin());
            if (config.getErrorCorrectionLevel() != null)
                hints.put(EncodeHintType.MARGIN, config.getErrorCorrectionLevel());
            if (config.getOnColor() != null) {
                onColor = config.getOnColor().getIntArgbPre();
            }
            if (config.getOffColor() != null) {
                offColor = config.getOffColor().getIntArgbPre();
            }
        }
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(onColor, offColor);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);// 生成矩阵

        BufferedImage qrCode = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
        if (logoPath != null) {
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
           /* BufferedImage tag = new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = tag.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();*/

            graph.drawImage(image, 200 * 2 / 5, 200 * 2 / 5, image.getWidth(null), image.getHeight(null), null);
            graph.dispose();
            image.flush();
            qrCode = qrCodeWithLogo;
        }
        if (content != null) {
            FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(font);
            int wordheigh = fm.getHeight();
            int strWidth = fm.stringWidth(bottomText);
            int height = new Double(200 + ceil(wordheigh / 1.4)).intValue();
            //总长度减去文字长度的一半  （居中显示）
            BufferedImage qrCodeWithText = new BufferedImage(qrCodeSize, height + 2, BufferedImage.TYPE_INT_ARGB);

            Graphics2D graph = qrCodeWithText.createGraphics();
            graph.setColor(java.awt.Color.white);
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
            Path path = FileSystems.getDefault().getPath(savePath, name);

            ImageIO.write(qrCodeWithText, "png", path.toFile());
        }

    }


    public static void main(String[] args) throws WriterException, IOException {
        String filePath = "D://";
        String fileName = "zxing.png";
        String content = "static double rint(double a)：四舍五入函数，返回与a的值最相近的整数（但是以浮点数形式存储）。   ";
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        MatrixToImageConfig config = new MatrixToImageConfig(Color.RED.getIntArgbPre(), Color.GREEN.getIntArgbPre());

        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);   //设置白边
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
        BufferedImage logoImage = new BufferedImage(200, 200,
                BufferedImage
                        .TYPE_INT_ARGB);

        Graphics2D graph1 = logoImage.createGraphics();
        graph1.setColor(java.awt.Color.white);
        graph1.fillRect(0, 0, logoImage.getWidth(), logoImage.getHeight());
        graph1.drawImage(bufferedImage, 0, 0, 200, 200, null);

        BufferedImage bi = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\111.jpeg"));
        Image image = bi.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = tag.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        graph1.drawImage(image, 200 * 2 / 5, 200 * 2 / 5, image.getWidth(null), image.getHeight(null), null);
        graph1.dispose();
        image.flush();

        bufferedImage = logoImage;

        // 字体、字型、字号
        Font f = new Font("宋体", Font.BOLD, 17);

        FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(f);
        // 高度
        int wordheigh = fm.getHeight();
        System.out.println(wordheigh);
        // 整个字符串的宽度
        int strWidth = fm.stringWidth("瑞特热太热呢吧");

        //总长度减去文字长度的一半  （居中显示）
        BufferedImage textImage = new BufferedImage(200, new Double(200 + ceil(wordheigh / 1.4)).intValue() + 2,
                BufferedImage
                        .TYPE_INT_ARGB);

        Graphics2D graph = textImage.createGraphics();
        graph.setColor(java.awt.Color.white);
        graph.fillRect(0, 0, textImage.getWidth(), textImage.getHeight());
        graph.drawImage(bufferedImage, 0, 0, 200, 200, null);
        // 画文字到新的面板
        graph.setColor(java.awt.Color.BLACK);
        graph.setFont(f);
        int wordStartX = (200 - strWidth) / 2;
        //height + (outImage.getHeight() - height) / 2 + 12
        int wordStartY = new Double(200 + ceil(wordheigh / 1.4)).intValue();
        // 画文字
        graph.drawString("瑞特热太热呢吧", wordStartX, wordStartY);
        graph.dispose();
        textImage.flush();
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        ImageIO.write(textImage, format, path.toFile());
       /* path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);*/

        System.out.println("输出成功.");
    }
}
