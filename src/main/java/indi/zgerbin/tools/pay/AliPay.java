package indi.zgerbin.tools.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import indi.zgerbin.tools.pay.entity.AliPayConfig;

public class AliPay {


    private AlipayClient alipayClient;

    private AliPay() {
    }

    public AliPay(AliPayConfig config) {
        this.alipayClient = new DefaultAlipayClient(config.getGatewayUrl(), config.getApp_id(), config.getMerchant_private_key(), "json",
                config.getCharset(),
                config.getAlipay_public_key(),
                config.getSign_type().getValue());
    }

    public static void main(String[] args) {
        AliPayConfig aliPayConfig = new AliPayConfig.Builder().build();
    }
}
