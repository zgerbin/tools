package indi.zgerbin.tools.pay.entity;

import static indi.zgerbin.tools.pay.entity.SignType.RSA2;

public class AliPayConfig {

    private final String app_id;

    // 商户私钥，您的PKCS8格式RSA2私钥
    private final String merchant_private_key;

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private final String alipay_public_key;

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private final String notify_url;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private final String return_url;

    // 签名方式
    private final SignType sign_type;

    // 字符编码格式
    private final String charset;

    // 支付宝网关
    private final String gatewayUrl;

    private AliPayConfig(Builder builder) {
        this.app_id = builder.app_id;
        this.merchant_private_key = builder.merchant_private_key;
        this.alipay_public_key = builder.alipay_public_key;
        this.notify_url = builder.notify_url;
        this.return_url = builder.return_url;
        this.sign_type = builder.sign_type;
        this.charset = builder.charset;
        this.gatewayUrl = builder.gatewayUrl;
    }

    public static class Builder {
        private String app_id;
        private String merchant_private_key;
        private String alipay_public_key;
        private String notify_url;
        private String return_url;
        private SignType sign_type = RSA2;
        private String charset = "utf-8";
        private String gatewayUrl = "https://openapi.alipay.com/gateway.do";

        public Builder app_id(String app_id) {
            this.app_id = app_id;
            return this;
        }

        public Builder merchant_private_key(String merchant_private_key) {
            this.merchant_private_key = merchant_private_key;
            return this;
        }


        public Builder alipay_public_key(String alipay_public_key) {
            this.alipay_public_key = alipay_public_key;
            return this;
        }

        public Builder notify_url(String notify_url) {
            this.notify_url = notify_url;
            return this;
        }

        public Builder return_url(String return_url) {
            this.return_url = return_url;
            return this;
        }

        public Builder sign_type(SignType sign_type) {
            this.sign_type = sign_type;
            return this;
        }

        public Builder charset(String charset) {
            this.charset = charset;
            return this;
        }

        public Builder gatewayUrl(String gatewayUrl) {
            this.gatewayUrl = gatewayUrl;
            return this;
        }

        public AliPayConfig build() {
            return new AliPayConfig(this);
        }
    }

    public String getApp_id() {
        return app_id;
    }

    public String getMerchant_private_key() {
        return merchant_private_key;
    }

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public SignType getSign_type() {
        return sign_type;
    }

    public String getCharset() {
        return charset;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }
}

