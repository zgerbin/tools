package indi.zgerbin.tools.files;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zgb
 * @date 2018/8/16
 */
public class ZipUtils {

    private static final String ENCODING = "UTF-8";

    private static final Integer BUFFER = 2048;


    private ZipUtils() {

    }

    /**
     * 将所选文件进行压缩（包含文件、文件夹等）
     *
     * @param paths   所选文件路径
     * @param zipName 压缩文件名
     * @param dstPath 保存路径
     * @return
     */
    private static boolean compressing(List<String> paths, String zipName, String dstPath, String charset) {
        charset = (charset == null || charset.trim().equals("")) ? ENCODING : charset;
        if (!zipName.endsWith(".zip")) {
            zipName = zipName + ".zip";
        }
        if (!dstPath.endsWith(File.separator)) {
            dstPath = dstPath + File.separator;
        }
        File dstFile = new File(dstPath);
        if (!dstFile.exists() && !dstFile.isDirectory()) {
            if (!dstFile.mkdirs()) {
                return false;
            }
        }
        try (ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(dstPath + zipName)))) {
            out.setEncoding(charset);
            for (String file : paths) {
                compress(new File(file), out, "");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean compress(List<String> paths, String zipName, String dstPath, String charset) {
        return compressing(paths, zipName, dstPath, charset);
    }

    public static boolean compress(List<String> paths, String zipName, String dstPath) {
        return compressing(paths, zipName, dstPath, null);
    }

    public static boolean compress(String path, String zipName, String dstPath) {
        List<String> paths = new ArrayList<>();
        paths.add(path);
        return compressing(paths, zipName, dstPath, null);
    }

    public static boolean compress(String path, String zipName, String dstPath, String charset) {
        List<String> paths = new ArrayList<>();
        paths.add(path);
        return compressing(paths, zipName, dstPath, charset);
    }


    private static boolean compressFile(File file, ZipOutputStream out, String dstPath) {
        byte[] buffer = new byte[BUFFER];
        try {
            ZipEntry entry = new ZipEntry(dstPath + file.getName());
            out.putNextEntry(entry);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            int len;
            while ((len = in.read(buffer, 0, buffer.length)) > -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    private static boolean compressDirectory(File dir, ZipOutputStream out, String dstPath) {
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null && files.length == 0) {
            try {
                ZipEntry entry = new ZipEntry(dstPath + dir.getName() + File.separator);
                out.putNextEntry(entry);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        for (File file : files) {
            compress(file, out, dstPath + dir.getName());
        }
        return true;
    }

    private static boolean compress(File file, ZipOutputStream out, String dstPath) {
        if (!dstPath.equals("") && !dstPath.endsWith(File.separator)) {
            dstPath = dstPath + File.separator;
        }
        if (file.isDirectory()) {
            compressDirectory(file, out, dstPath);
        } else {
            compressFile(file, out, dstPath);
        }
        return true;
    }
}
