package com.yusys.agile.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Description HttpComponentsUtils工具类
 * zt
 */
public class HttpComponentsUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpComponentsUtils.class);

    private static PoolingHttpClientConnectionManager manager;
    private static CloseableHttpClient httpClient;

    static {
        manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(200);
        httpClient = HttpClients.createMinimal(manager);
    }

    /**
     * @param uri 请求URL
     * @return get请求结果
     * @Description: get请求
     */
    public static String get(String uri) {
        HttpGet get = doGet(uri);
        return getResponse(httpClient, get);
    }

    /**
     * @param uri    请求URL
     * @param params
     * @return post请求结果
     * @Description: post请求
     */
    public static String post(String uri, Map<String, String> params) {
        HttpPost post = doPost(uri, params);
        return getResponse(httpClient, post);
    }

    /**
     * 带请求头的post请求
     *
     * @param url
     * @param headers
     * @param content
     * @return
     */
    public static String postWithHeader(String url, Map<String, Object> headers, String content) throws UnsupportedEncodingException {
        HttpPost post = doPostJsonWithHeader(url, headers, content);
        return getResponse(httpClient, post);
    }

    /**
     * 带请求头的post请求
     *
     * @param url
     * @param headers
     * @param content
     * @return
     */
    public static String postXMLWithHeader(String url, Map<String, Object> headers, String content) throws UnsupportedEncodingException {
        HttpPost post = doPostXMLWithHeader(url, headers, content);
        return getResponse(httpClient, post);
    }

    /**
     * @param uri 请求地址
     * @return get对象
     * @Description: 获取get对象
     */
    private static HttpGet doGet(String uri) {
        HttpGet get = new HttpGet(uri);
        return get;
    }

    /**
     * @param uri    请求地址
     * @param params 传入参数
     * @return post
     * @Description: 获取post对象
     */
    private static HttpPost doPost(String uri, Map<String, String> params) {
        HttpPost post = new HttpPost(uri);
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (!params.isEmpty()) {
            Set<String> set = params.keySet();
            for (Iterator<String> iterator = set.iterator(); iterator.hasNext(); ) {
                String name = (String) iterator.next();
                String value = params.get(name);
                list.add(new BasicNameValuePair(name, value));
            }
        }
        post.setEntity(new UrlEncodedFormEntity(list, Charset.forName("UTF-8")));
        return post;
    }

    /**
     * 获取带请求头的post对象
     *
     * @param url
     * @param headerMap
     * @param content
     * @return
     */
    private static HttpPost doPostJsonWithHeader(String url, Map<String, Object> headerMap, String content) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        if (!headerMap.isEmpty()) {
            Set<String> headerSet = headerMap.keySet();
            for (Iterator<String> iterator = headerSet.iterator(); iterator.hasNext(); ) {
                String name = (String) iterator.next();
                String value = (String) headerMap.get(name);
                post.setHeader(name, value);
            }
        }
        StringEntity entity = new StringEntity(content);
        entity.setContentType("application/json");
        entity.setContentEncoding("UTF-8");
        post.setEntity(entity);
        return post;
    }

    /**
     * @param url
     * @param headerMap
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    private static HttpPost doPostXMLWithHeader(String url, Map<String, Object> headerMap, String content) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        if (!headerMap.isEmpty()) {
            Set<String> headerSet = headerMap.keySet();
            for (Iterator<String> iterator = headerSet.iterator(); iterator.hasNext(); ) {
                String name = (String) iterator.next();
                String value = (String) headerMap.get(name);
                post.setHeader(name, value);
            }
        }
        StringEntity entity = new StringEntity(content);
        entity.setContentType("application/xml");
        entity.setContentEncoding("UTF-8");
        post.setEntity(entity);
        return post;
    }

    /**
     * @param httpClient
     * @param request
     * @return 响应结果
     * @Description: 返回服务端调用结果
     */
    private static String getResponse(HttpClient httpClient, HttpUriRequest request) {
        InputStream is = null;
        try {
            HttpResponse response = httpClient.execute(request);
            LOGGER.info("HttpComponentsUtils get message from server...");
            //200 OK
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    long len = entity.getContentLength();
                    if (len != -1 && len < 2048) {
                        return EntityUtils.toString(entity);
                    } else {
                        entity = new BufferedHttpEntity(entity);
                        is = entity.getContent();
                        byte[] bytes = new byte[1024 * 20];
                        int length = 0;
                        StringBuffer buffer = new StringBuffer();
                        while ((length = is.read(bytes)) != -1) {
                            buffer.append(new String(bytes, 0, length, Charset.forName("UTF-8")));
                        }
                        return buffer.toString();
                    }
                }
            }
        } catch (ClientProtocolException e) {
            LOGGER.error("HttpComponentsUtils-ClientProtocolException Message {}", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("HttpComponentsUtils-IOException Message {}", e.getMessage());
        } finally {
            if (request instanceof HttpPost) {
                HttpPost post = (HttpPost) request;
                post.releaseConnection();
            }

            if (request instanceof HttpGet) {
                HttpGet get = (HttpGet) request;
                get.releaseConnection();
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("HttpComponentsUtils shutdown httpclient connnection...{}", e.getStackTrace());
                }
            }
            LOGGER.info("HttpComponentsUtils shutdown httpclient connnection...");
            //httpClient.getConnectionManager().shutdown();
        }
        return null;
    }

    /**
     * @param url         请求的url
     * @param jsonRequest 请求的参数
     * @return String 返回的结果字符串
     * @Description: 向目的URL发送post请求 返回Object
     */
    public static String sendPostRequest(String url, String jsonRequest) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        // 设置编码格式
        client.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 将请求头部和参数合成一个请求
        org.springframework.http.HttpEntity<Object> entity = new org.springframework.http.HttpEntity<>(jsonRequest, headers);
        // 执行HTTP请求
        ResponseEntity<String> response = client.postForEntity(url, entity, String.class);
        return response.getBody();
    }

}