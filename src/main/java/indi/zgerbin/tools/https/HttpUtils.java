package indi.zgerbin.tools.https;

import indi.zgerbin.tools.https.entity.HeaderEntity;
import net.sf.json.JSONObject;
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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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


    public static JSONObject postInJson(String url, Map<String, Object> params, String charset, HeaderEntity header)
            throws
            IOException {
        String var = post(url, params, charset, header);
        return JSONObject.fromObject(var);
    }


    public static JSONObject postInJson(String url) {
        try {
            String var = post(url, null, null, null);
            return JSONObject.fromObject(var);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject postInJson(String url, String charset) {
        try {
            String var = post(url, null, charset, null);
            return JSONObject.fromObject(var);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject postInJson(String url, Map<String, Object> params) {
        try {
            String var = post(url, params, null, null);
            return JSONObject.fromObject(var);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getInJson(String url, Map<String, Object> params, String charset, HeaderEntity header) throws
            IOException {
        try {
            String var = get(url, params, charset, header);
            return JSONObject.fromObject(var);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static JSONObject getInJson(String url) {
        try {
            String var = get(url, null, null, null);
            return JSONObject.fromObject(var);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getInJson(String url, String charset) {
        try {
            String var = get(url, null, charset, null);
            return JSONObject.fromObject(var);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getInJson(String url, Map<String, Object> params) {
        try {
            String var = get(url, params, null, null);
            return JSONObject.fromObject(var);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String upload(String path, File file) throws IOException {
        URL url = new URL(path);
        //连接
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        String result = null;
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary="
                        + BOUNDARY);

        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length() + "\";filename=\""

                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

    public static JSONObject uploadInJson(String path, File file) throws IOException {
        try {
            String var = upload(path, file);
            return JSONObject.fromObject(var);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
