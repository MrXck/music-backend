package com.music.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

/**
 * Tencent Cloud Sms Sendsms
 *
 */
public class SMSUtils {

    public static final String secretId = "";
    public static final String secretKey = "";
    public static final String sdkApp_id = "";
    public static final String sign_name = "";
    public static final String templateId = "";
    public static final String login_id = "";

    public static void sendSMS(String phoneNum, String content) throws TencentCloudSDKException {
        Credential cred = new Credential(secretId, secretKey);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        SmsClient client = new SmsClient(cred, "ap-guangzhou",clientProfile);
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppId(sdkApp_id);
        req.setSignName(sign_name);
        String senderid = "";
        req.setSenderId(senderid);
        req.setTemplateId(templateId);
        String[] phoneNumberSet = {"+86" + phoneNum};
        req.setPhoneNumberSet(phoneNumberSet);
        String[] templateParamSet = {content};
        req.setTemplateParamSet(templateParamSet);
        SendSmsResponse res = client.SendSms(req);
        System.out.println(SendSmsResponse.toJsonString(res));
    }
}