package com.yufan.controller;

import com.alibaba.fastjson.JSONObject;
import com.yufan.utils.Base64Coder;
import com.yufan.utils.VerifySign;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * 创建人: lirf
 * 创建时间:  2020/1/11 21:06
 * 功能介绍: 测试
 */
@Controller
@RequestMapping("/test/")
public class TestController {

    private Logger LOG = Logger.getLogger(TestController.class);

    //-------------------------------------------测试--------------------------------------------------------------------

    public static int testResultValue = 2;//0失败  1成功 2异常或者超时

    /**
     * http://lirf-shop.51vip.biz:25139/pay-center/test/setTestResult?value=1
     * (测试)
     */
    @RequestMapping("setTestResult")
    public void setTestResult(HttpServletRequest request, HttpServletResponse response, Integer value) {
        PrintWriter writer;
        try {
            writer = response.getWriter();
            testResultValue = value;
            writer.print(testResultValue);
            writer.close();
        } catch (Exception e) {
            LOG.info("----->setTestResult异常");
        }
    }

    /**
     * (测试)
     * http://lirf-shop.51vip.biz:25139/pay-center/test/getTestResult
     *
     * @param request
     * @param response
     */
    @RequestMapping("getTestResult")
    public void getTestResult(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.print(1);
            writer.close();
        } catch (Exception e) {
            LOG.info("----->getTestResult异常");
        }
    }


    /**
     * (测试)
     * http://lirf-shop.51vip.biz:25139/pay-center/test/testResult
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("testResult")
    public ModelAndView testResult(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("payResult");
            modelAndView.addObject("returnUrl", "");
        } catch (Exception e) {
            LOG.info("----->ajaxPayResult异常");
        }
        return modelAndView;
    }


    /**
     * http://lirf-shop.51vip.biz:25139/pay-center/test/recharge
     * 测试充值页面(测试)
     *
     * @return
     */
    @RequestMapping("recharge")
    public ModelAndView rechargePage(HttpServletResponse response, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("rechargePage");
        return modelAndView;
    }

    /**
     * http://lirf-shop.51vip.biz:25139/pay-center/test/payParam
     * 生成支付必须参数(测试)
     */
    @RequestMapping("payParam")
    public void payParam(HttpServletResponse response, HttpServletRequest request, Integer payWay, String orderNo) {
        PrintWriter writer;
        try {
            writer = response.getWriter();

            //
            String secretKey = "dsfsdfsd";
            String sysCode = "h5-web";
            String businessCode = "order_pay";

            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            JSONObject paramData = new JSONObject();
            paramData.put("client_code", sysCode);
            paramData.put("business_code", businessCode);
            paramData.put("order_no", orderNo);
            paramData.put("pay_way", payWay);//交易方式 1 微信2 支付宝
            paramData.put("record_type", 4);//事项 1 订单退押金 2 订单退款 3 提现 4 订单支付
            paramData.put("timestamp", timestamp);

            String sign = VerifySign.getSign(paramData, secretKey);
            paramData.put("sign", sign);
            String base64Str = Base64Coder.encodeString(paramData.toJSONString());
            LOG.info("----请求支付参数-----" + paramData);
            JSONObject out = new JSONObject();
            out.put("values", base64Str);
            writer.print(out);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------测试--------------------------------------------------------------------


}
