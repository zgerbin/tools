package indi.zgerbin.tools.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import indi.zgerbin.tools.pay.entity.AliPayConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AliPay {


    private AlipayClient alipayClient;
    private AliPayConfig config;

    private AliPay() {
    }

    public AliPay(AliPayConfig config) {
        this.config = config;
        this.alipayClient = new DefaultAlipayClient(config.getGatewayUrl(), config.getApp_id(), config.getMerchant_private_key(), "json",
                config.getCharset(),
                config.getAlipay_public_key(),
                config.getSign_type().getValue());
    }


    public void createPayForm(String BizContent, HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(config.getReturn_url());
        alipayRequest.setNotifyUrl(config.getNotify_url());//在公共参数中设置回跳和通知地
        alipayRequest.setBizContent(BizContent);//填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + config.getCharset());
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }
}
