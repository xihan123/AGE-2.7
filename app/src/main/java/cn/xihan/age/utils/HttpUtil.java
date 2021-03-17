package cn.xihan.age.utils;

import android.annotation.SuppressLint;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.concurrent.Semaphore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 10:13
 * @介绍 :
 */
public class HttpUtil {

    /**使用volatile双重校验锁**/
    private static volatile Semaphore        semaphore    = null;
    private static final    OkHttpClient     okHttpClient = null;
    private static          SSLSocketFactory sslSocketFactory;


    /**建立单例模式*/
    public static  Semaphore getSemaphoreInstance(){
        //只能0个线程同时访问
        synchronized (HttpUtil.class) {
            if (semaphore == null) {
                semaphore = new Semaphore(0);
            }
        }
        return semaphore;
    }


    public static void sendRequestWithOkhttp(String address, Callback callback)
    {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }


    public static String createHttpsPostBystring(String url, String postjson, String authorization) {
        final StringBuilder buffer = new StringBuilder();
        final X509TrustManager trustManager = new X509TrustManager() {
            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };


        OkHttpClient build = new OkHttpClient.Builder()
                .sslSocketFactory(getSSLSocketFactory(),trustManager)
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .build();


        //OkHttpClient build = new OkHttpClient.Builder().sslSocketFactory(createSSLSocketFactory()).hostnameVerifier(new TrustAllHostnameVerifier()).build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody requestBody = RequestBody.create(mediaType, postjson);
        Request request  = new Request.Builder().header("Authorization",authorization).url(url).post(requestBody).build();

        final Call call     = build.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String res = Objects.requireNonNull(response.body()).string();
                buffer.append(res);
                getSemaphoreInstance().release();//释放
            }
        });

        try {
            getSemaphoreInstance().acquire();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return  buffer.toString();
    }



    /**
     * @Description createSSLSocketFactory
     * @return 参数
     * @return SSLSocketFactory 返回类型
     * @throws
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { new TrustAllCerts() }, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }


    /**
     * @Author    liangjl
     * @Copyright (c) All Rights Reserved, 2018.
     */
    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    /**
     * @Description    验证所有主机
     * @ClassName  TrustAllHostnameVerifier
     * @Copyright (c) All Rights Reserved, 2018.
     */
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }



    //获取这个SSLSocketFactory
    public static SSLSocketFactory getSSLSocketFactory() {
        try
        {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    //获取TrustManager
    private static TrustManager[] getTrustManager() {
        //就是把下面这个X509TrustManager更换一下
        return new TrustManager[]{new X509TrustManager()
        {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
            {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
            {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers()
            {
                return new X509Certificate[]{};
            }
        }};
    }

    //获取HostnameVerifier
    public static HostnameVerifier getHostnameVerifier() {
        return (s, sslSession) -> true;
    }



}




