package pers.zgerbin.tools.strings;


public class StringUtils {

    private StringUtils() {

    }

    public static boolean isNull(String str) {
        return str == null;
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String nvl(String str) {
        return nvl(str, "");
    }

    public static String nvl(String str, String defval) {
        return isNull(str) ? defval : str;
    }
}
