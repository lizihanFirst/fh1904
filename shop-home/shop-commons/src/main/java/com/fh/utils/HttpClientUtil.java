package com.fh.utils;

import com.alibaba.fastjson.JSON;

import com.fh.commons.ResultEnum;
import com.fh.commons.ServerResult;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private static CloseableHttpClient httpClient;
    static {
        //创建httpclient，代码只执行一次。减少客户端创建的频率，节省服务器资源。
        //你是连接另一个服务器，连接超时设置。目的是不回因为接口调不通造成大量的线程挂起，最总造成堵塞，tomcat
        //直接崩溃
        //setConnectionRequestTimeout:设置与服务器连接的超时时间
        //setSocketTimeout:设置访问接口的超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(500).setSocketTimeout(15000).build();
        //创建http客户端
        httpClient= HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }
    /**
     * httpClient Get请求
     */
    public static final String doGet(String url){
        HttpGet httpGet=new HttpGet(url);
        CloseableHttpResponse response=null;
        try {
             response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity, "UTF-8");
                return string;
        }  //服务器连接超时异常
        catch (HttpHostConnectException e){
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_CONNECT_ERROR));
        }//服务器繁忙异常
        catch (SocketTimeoutException e){
                e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_BUSYNESS));
        }//流异常
        catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_ERROR));
        }finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * httpClient Post请求
     */
    public static final String doPost(String url, Map<String,String> parameterMap) {
        HttpPost httpPost = new HttpPost(url);
        //处理参数
        if (parameterMap != null) {
            List<NameValuePair> pairList = new ArrayList<NameValuePair>();
            parameterMap.entrySet().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                pairList.add(new BasicNameValuePair(key, value));
            });
            try {
                //转换编码格式
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairList, "UTF-8");
                //放入到请求中
                httpPost.setEntity(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        //执行方法
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        }  //服务器连接超时异常
        catch (HttpHostConnectException e) {
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_CONNECT_ERROR));
        }//服务器繁忙异常
        catch (SocketTimeoutException e) {
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_BUSYNESS));
        }//流异常
        catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_ERROR));
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * httpClient delete请求
     */
    public static final String doDelete(String url){
        HttpDelete httpDelete=new HttpDelete(url);
        CloseableHttpResponse response=null;
        try {
             response = httpClient.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,"UTF-8");
        } //服务器连接超时异常
        catch (HttpHostConnectException e) {
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_CONNECT_ERROR));
        }//服务器繁忙异常
        catch (SocketTimeoutException e) {
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_BUSYNESS));
        }//流异常
        catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_ERROR));
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * httpClient put请求
     */
    public static final String doPut(String url, Map<String,String> parameterMap) {
        HttpPut httpPut = new HttpPut(url);
        //处理参数
        List<NameValuePair> pairList = new ArrayList<>();
        if (parameterMap != null) {

            parameterMap.entrySet().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                pairList.add(new BasicNameValuePair(key, value));
            });
            try {
                //转换编码格式
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairList, "UTF-8");
                //放入到请求中
                httpPut.setEntity(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        CloseableHttpResponse response =null;
        try {
             response = httpClient.execute(httpPut);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,"UTF-8");
        }  //服务器连接超时异常
        catch (HttpHostConnectException e) {
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_CONNECT_ERROR));
        }//服务器繁忙异常
        catch (SocketTimeoutException e) {
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_BUSYNESS));
        }//流异常
        catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ServerResult.error(ResultEnum.SERVER_ERROR));
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
