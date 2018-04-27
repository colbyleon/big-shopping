package com.jt.common.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HttpClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

    @Autowired(required = false)
    private CloseableHttpClient httpClient;

    @Autowired(required = false)
    private RequestConfig requestConfig;

    /**
     * 实现httpClient的get提交
     * url:localhost:8091/addUser?id=18&name=tom
     * 参数设定
     * uri:表示请求路径
     * Map<String,String> params 进行数据封装
     * charset:字符编码
     * 编码的思路：
     * 1. 判断是否有参数
     * 有：uri拼接参数
     * 没有：表示该请求不需要参数
     * 2. 判断编码是否为null，则设定默认的值为utf-8
     * 3. 通过httpClient对象发送请求，之后获取返回值数据
     */
    public String doGet(String uri, Map<String, String> params, String charset) throws URISyntaxException {
        // 拼接参数的格式
        if (params != null) {
            URIBuilder builder = new URIBuilder(uri);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
            }
            uri = builder.build().toString();
        }
        // 判断编码是否为空
        charset = charset == null ? "utf-8" : charset;
        //定义get请求对象
        HttpGet httpGet = new HttpGet(uri);
        //定义请求连接的时长
        httpGet.setConfig(requestConfig);
        //发送请求
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 判断请求是否正确
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity(), charset);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String doGet(String uri) throws URISyntaxException {
        return doGet(uri, null, null);
    }

    /**
     * 实现httpClient的Post提交
     * 1. 创建post请求的对象
     * 2. 添加请求的参数（请求链接的时长）
     * 3. 将需要传递的参数通过form表单的形式进行数据封装
     * 4. 发出http请求获取响应信息
     * 5. 判断响应是否成功，这后返回数据
     */
    public String doPost(String uri, Map<String, String> params, String charset) throws UnsupportedEncodingException {
        /* 1. 创建post请求的对象*/
        HttpPost httpPost = new HttpPost(uri);
        /* 2. 添加请求的参数（请求链接的时长）*/
        httpPost.setConfig(requestConfig);
        /* 3. 将需要传递的参数通过form表单的形式进行数据封装*/
        charset = charset == null ? "utf-8" : charset;
        if (params != null && params.size() > 0) {
            List<NameValuePair> paramList = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, charset));
        }

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity(), charset);
                return result;
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String doPost(String uri) throws UnsupportedEncodingException {
        return doPost(uri, null, null);
    }

}
