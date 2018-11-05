package indi.zgerbin.tools.files;

import java.io.*;

public class FileOperateUtils {

    private static final String ENCODING = "UTF-8";
    private static final int buffer = 1024 * 5;

    private static boolean write(File tarFile, String content, String charset, boolean append, boolean newLine) {
        if (!tarFile.exists()) {
            try {
                tarFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = new FileOutputStream(tarFile, append);
            writer = new BufferedWriter(new OutputStreamWriter(out, charset));
            if (newLine) writer.newLine();
            writer.write(content);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean write(File tarFile, String content) throws UnsupportedEncodingException, FileNotFoundException {
        return write(tarFile, content, ENCODING, false, false);
    }


    public static boolean write(File tarFile, String content, String charset) {
        if (charset == null) charset = ENCODING;
        return write(tarFile, content, charset, false, false);
    }

    public static boolean append(File tarFile, String content, boolean newLine) throws UnsupportedEncodingException,
            FileNotFoundException {
        return write(tarFile, content, ENCODING, true, newLine);
    }

    public static boolean append(File tarFile, String content, String charset, boolean newLine) throws UnsupportedEncodingException,
            FileNotFoundException {
        if (charset == null) charset = ENCODING;
        return write(tarFile, content, charset, true, newLine);
    }

    public static boolean copy(File fromFile, File toFile, int buffer) {
        if (!fromFile.exists()) {
            return false;
        }
        if (!toFile.exists()) {
            try {
                toFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            fis = new FileInputStream(fromFile);
            bis = new BufferedInputStream(fis);

            fos = new FileOutputStream(toFile);
            bos = new BufferedOutputStream(fos);

            int size = 0;
            byte[] buf = new byte[buffer];
            while ((size = bis.read(buf)) != -1) {
                bos.write(buf, 0, size);
            }
            bos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private static boolean copy(File fromFile, File toFile) {
        return copy(fromFile, toFile, buffer);
    }

    public static String fileCharset(File file) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();
        bin.close();
        String code = null;
        switch (p) {
            case 61371:
                code = "UTF-8";
                break;
            case 65279:
                code = "UTF-16BE";
                break;
            case 65534:
                code = "Unicode";
                break;
            default:
                code = "GBK";
        }
        return code;
    }

    public static void main(String[] args) {
        copy(new File("E:\\logs\\aab.txt"), new File("E:\\logs\\aac.txt"));
    }


}
