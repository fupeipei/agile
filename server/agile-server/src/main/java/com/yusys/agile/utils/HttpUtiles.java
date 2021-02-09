package com.yusys.agile.utils;

import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * 辅助工具类
 */
public class HttpUtiles {

    /**
     * 向目的URL发送post请求
     *
     * @param url         目的url
     * @param jsonRequest 发送的参数
     * @return ResultMessage
     */
    public static ControllerResponse sendPostRequest(String url, String jsonRequest) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //设置编码格式
        client.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        HttpEntity<Object> entity = new HttpEntity<>(jsonRequest, headers);
        //执行HTTP请求
        ResponseEntity<ControllerResponse> response = client.postForEntity(url, entity, ControllerResponse.class);
        return response.getBody();
    }

    /**
     * 向目的URL发送post请求 返回Object
     *
     * @param url         目的url
     * @param jsonRequest 发送的参数
     * @return Object
     */
    public static String sendPostReq(String url, String jsonRequest) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //设置编码格式
        client.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        HttpEntity<Object> entity = new HttpEntity<>(jsonRequest, headers);
        //执行HTTP请求
        ResponseEntity<String> response = client.postForEntity(url, entity, String.class);
        return response.getBody();
    }

}
