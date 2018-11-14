package indi.zgerbin.tools.utils;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {

    private static String img2Base64(BufferedImage image, String type) {
        String result;
        try {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                ImageIO.write(image, type, byteArrayOutputStream);//写入流中
                byte[] bytes = byteArrayOutputStream.toByteArray();//转换成字节
                BASE64Encoder encoder = new BASE64Encoder();
                //转换成base64串
                result = encoder.encodeBuffer(bytes).trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (result != null && !("").equals(result)) {
            result = "data:image/" + type + ";base64," + result.replaceAll("\n", "").replaceAll("\r", "");
        }
        return result;
    }


}
