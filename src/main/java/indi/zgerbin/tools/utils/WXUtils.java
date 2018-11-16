package indi.zgerbin.tools.utils;

import indi.zgerbin.tools.https.HttpUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class WXUtils {

    private static Logger logger = LoggerFactory.getLogger(WXUtils.class);

    //网页授权access_token
    private final static String OAUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //普通access_token
    private final static String BASE_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //拉取用户信息(需scope为 snsapi_userinfo)
    private final static String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    //刷新access_token
    private final static String TOKEN_REFRESH_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    //检验授权凭证（access_token）是否有效
    private final static String OAUTH_TOKEN_CHECK_URL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
    //获取自定义菜单配置
    private final static String MENU_INFO_URL = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";
    //创建自定义菜单
    private final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //以snsapi_userinfo为scope发起的网页授权
    private final static String USERINFO_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    //以snsapi_base为scope发起的网页授权
    private final static String BASE_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
    //上传永久素材
    private final static String MATERIAL_UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";
    //获取永久素材列表
    private final static String MATERIAL_LIST_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

    private static String APPID;
    private static String APPSECRET;


    public static void init(String id, String secret) {
        APPID = id;
        APPSECRET = secret;
    }

    public static String getRedirectURIOAuthByUserInfo(String redirectURI, String state) throws
            UnsupportedEncodingException {
        return startOAuth(USERINFO_OAUTH_URL, APPID, redirectURI, state);
    }

    public static String getRedirectURIOAuthByBase(String redirectURI, String state) throws
            UnsupportedEncodingException {
        return startOAuth(BASE_OAUTH_URL, APPID, redirectURI, state);
    }

    private static String startOAuth(String url, String appId, String redirectURI, String state) throws
            UnsupportedEncodingException {
        url = url.replace("APPID", appId).replace("REDIRECT_URI", URLEncoder.encode(redirectURI, "UTF-8"));
        if (state != null) {
            url = url.replace("STATE", state);
        }
        return url;
    }

    /**
     * 用户同意授权后，通过code换取网页授权access_token
     */
    public static JSONObject getOAuthAccessToken(String code) {

        String url = OAUTH_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code);
        JSONObject jsonObject = HttpUtils.postInJson(url);
        if (null != jsonObject) {
            Object obj = jsonObject.get("errcode");
            if (obj != null) {
            }
        }
        return jsonObject;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId      用户标识
     */
    public static JSONObject getUserInfo(String accessToken, String openId) {
        // 拼接请求地址
        String url = USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = HttpUtils.postInJson(url);
        if (null != jsonObject) {
            Object obj = jsonObject.get("errcode");
            if (obj != null) {
            }
        }
        return jsonObject;
    }


    /**
     * 拉取公众号菜单
     *
     * @param accessToken 网页授权接口调用凭证
     */
    public static JSONObject getMenuInfo(String accessToken) {
        // 拼接请求地址
        String url = MENU_INFO_URL.replace("ACCESS_TOKEN", accessToken);
        // 通过网页授权获取用户信息
        return HttpUtils.postInJson(url);
    }

    /**
     * 定义公众号菜单
     */
    public static JSONObject createMenu(String menu) {
        JSONObject json = getBaseAccessToken();
        String token = "";
        if (json != null) {
            token = json.getString("access_token");
        }
        String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", token);
        // 通过网页授权获取用户信息
        return HttpUtils.postInJson(url);
    }

    /**
     * 获取access_token
     */
    public static JSONObject getBaseAccessToken() {
        String url = BASE_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = HttpUtils.postInJson(url);
        Map<String, String> map = null;
        if (null != jsonObject) {
        }
        return jsonObject;
    }

    /**
     * 刷新access_token（如果需要）
     * <p>
     * 由于access_token拥有较短的有效期，当access_token超时后，
     * 可以使用refresh_token进行刷新，refresh_token有效期为30天，
     * 当refresh_token失效之后，需要用户重新授权。
     */
    public static JSONObject refreshAccessToken(String refreshToken) {

        String url = TOKEN_REFRESH_URL.replace("APPID", APPID).replace("REFRESH_TOKEN", refreshToken);
        JSONObject jsonObject = HttpUtils.postInJson(url);
        return jsonObject;
    }

    /**
     * 检验授权凭证（access_token）是否有效
     */
    public static boolean isValidOAuthAccessToken(String accessToken) {

        String url = OAUTH_TOKEN_CHECK_URL.replace("APPID", APPID).replace("REFRESH_TOKEN", accessToken);
        JSONObject jsonObject = HttpUtils.postInJson(url);
        if (null != jsonObject) {
            Integer result = jsonObject.getInt("errcode");
            return result == 0;
        }
        return false;
    }


    public static JSONObject uploadMaterial(File file, String type) throws IOException {
        JSONObject json = getBaseAccessToken();
        String token = "";
        if (json != null) {
            token = json.getString("access_token");
        }
        //上传素材
        String path = MATERIAL_UPLOAD_URL.replace("ACCESS_TOKEN", token).replace("TYPE", type);
        String result = HttpUtils.upload(path, file);
        result = result.replaceAll("[\\\\]", "");

        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }

    /**
     * 获取素材列表并存入集合中
     *
     * @param type   素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  返回素材的数量，取值在1到20之间
     * @return
     */
    public static JSONObject getMaterialList(String type, int offset, int count) {
        JSONObject json = getBaseAccessToken();
        String token = "";
        if (json != null) {
            token = json.getString("access_token");
        }
        String url = MATERIAL_LIST_URL.replace("ACCESS_TOKEN", token);//替换调access_token
        Map<String, Object> para = new HashMap<>();

        para.put("type", type);
        para.put("offset", String.valueOf(offset));
        para.put("count", String.valueOf(count));

        JSONObject jsonObject = HttpUtils.postInJson(url, para);
        return jsonObject;
    }


}
