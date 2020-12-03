package xyz.kwin.voa.utils;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Http请求工具
 *
 * @author Kwin
 * @since 2020/11/28 上午10:35
 */
public class Http {

    /**
     * 执行请求操作
     */
    private static final Function<HttpRequestBase, Response> EXECUTION = (request) -> {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        Integer statusCode = null;
        try {
            httpResponse = httpClient.execute(request);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new HttpException("状态码异常, 状态码：" + statusCode);
            }

            HttpEntity entity = httpResponse.getEntity();
            String data = EntityUtils.toString(entity);
            return new Response(statusCode, data);
        } catch (Exception ex) {
            return new Response(statusCode, null, ex);
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
            if (null != httpResponse) {
                HttpClientUtils.closeQuietly(httpResponse);
            }
        }
    };

    /**
     * 设置头部信息
     */
    private static final BiFunction<HttpRequestBase, Map<String, String>, HttpRequestBase> GET_REQUEST = (request, headers) -> {
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                request.setHeader(header.getKey(), header.getValue());
            }
        }
        return request;
    };

    /**
     * 执行get请求
     *
     * @param url     url
     * @param headers 请求头
     * @return 返回结果
     */
    public static Response get(String url, Map<String, String> headers) {
        HttpGet request = (HttpGet) GET_REQUEST.apply(new HttpGet(url), headers);

        return EXECUTION.apply(request);
    }

    /**
     * 执行get请求
     *
     * @param url     url
     * @param params  参数map
     * @param headers 请求头
     * @return 返回结果
     */
    public static Response get(String url, Map<String, String> params, Map<String, String> headers) {
        return get(toRequestUrl(url, params), headers);
    }

    /**
     * 执行post请求
     *
     * @param url     url
     * @param param   参数
     * @param headers 请求头
     * @return 返回结果
     * @throws UnsupportedEncodingException 参数异常
     */
    public static Response post(String url, Object param, Map<String, String> headers) throws UnsupportedEncodingException {
        HttpPost request = (HttpPost) GET_REQUEST.apply(new HttpPost(url), headers);

        if (param instanceof Map) {
            // map参数
            Map<String, String> paramMap = (Map<String, String>) param;
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for (Map.Entry<String, String> paramEntry : paramMap.entrySet()) {
                nameValuePairList.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
            }
            request.setEntity(new UrlEncodedFormEntity(nameValuePairList));
        } else {
            // json参数
            StringEntity requestEntity = new StringEntity(JSON.toJSONString(param), "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            request.setHeader("Content-type", "application/json");
            request.setEntity(requestEntity);
        }

        return EXECUTION.apply(request);
    }

    /**
     * 拼接url
     *
     * @param url    url
     * @param params 请求参数
     * @return 完整url
     */
    private static String toRequestUrl(String url, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            url += "?";
            List<String> paramPairs = new ArrayList<>();
            for (Map.Entry<String, String> paramEntry : params.entrySet()) {
                paramPairs.add(paramEntry.getKey() + "=" + paramEntry.getValue());
            }
            url += StringUtils.join(paramPairs, "&");
        }

        return url;
    }

    /**
     * Http请求返回结果封装
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Integer code;
        private String data;
        private Throwable ex;

        public Response(Integer code, String data) {
            this.code = code;
            this.data = data;
        }

        public boolean error() {
            return ex != null;
        }
    }
}
