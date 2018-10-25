package pers.zgerbin.tools.https;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import pers.zgerbin.tools.https.util.HeaderEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static final String ENCODING = "UTF-8";

    private HttpUtils() {

    }

    public static String post(String url, Map<String, Object> params, String charset, HeaderEntity header) throws
            IOException {
        charset = (charset == null || charset.trim().equals("")) ? ENCODING : charset;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity urlEncodedFormEntity = getUrlEncodedFormEntity(params, charset);
            if (urlEncodedFormEntity != null)
                httpPost.setEntity(urlEncodedFormEntity);
            setHeader(header, httpPost);
            httpResponse = httpClient.execute(httpPost);
            //int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity, charset);
                EntityUtils.consume(httpEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null)
                httpResponse.close();
            httpClient.close();
        }
        return result;
    }


    public static String post(String url) {
        try {
            return post(url, null, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, String charset) {
        try {
            return post(url, null, charset, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, Map<String, Object> params) {
        try {
            return post(url, params, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get(String url, Map<String, Object> params, String charset, HeaderEntity header) throws
            IOException {
        charset = (charset == null || charset.trim().equals("")) ? ENCODING : charset;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String result = null;
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = getUrlEncodedFormEntity(params, charset);
            if (urlEncodedFormEntity != null) {
                url = url + "?" + EntityUtils.toString(urlEncodedFormEntity, charset);
            }
            HttpGet httpGet = new HttpGet(url);
            setHeader(header, httpGet);
            httpResponse = httpClient.execute(httpGet);
            //int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity, charset);
                EntityUtils.consume(httpEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null)
                httpResponse.close();
            httpClient.close();
        }
        return result;
    }


    public static String get(String url) {
        try {
            return get(url, null, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get(String url, String charset) {
        try {
            return get(url, null, charset, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get(String url, Map<String, Object> params) {
        try {
            return get(url, params, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static UrlEncodedFormEntity getUrlEncodedFormEntity(Map<String, Object> params, String charset) {
        if (params == null) return null;
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = null;
            if (pairs.size() > 0) {
                urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, charset);
            }
            return urlEncodedFormEntity;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void setHeader(HeaderEntity header, HttpRequestBase requestBase) {
        if (header != null) {
            if (header.getAccept() != null)
                requestBase.setHeader("Accept", header.getAccept());
            if (header.getCookie() != null)
                requestBase.setHeader("Cookie", header.getCookie());
            if (header.getHost() != null)
                requestBase.setHeader("Host", header.getHost());
            if (header.getRefer() != null)
                requestBase.setHeader("refer", header.getRefer());
            if (header.getUserAgent() != null)
                requestBase.setHeader("User-Agent", header.getUserAgent());
        }
    }
}
