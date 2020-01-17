package com.ixilink.banknote_box.common.util;


import javax.net.ssl.*;
import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * description: 证书验证屏蔽工具
 * author: 张俊
 * date: 2019-11-06 10:20
 */
public class DisableSSLCertificateCheckUtil {


    /**

     * Prevent instantiation of utility class.

     */
    private
    DisableSSLCertificateCheckUtil() {



    }



    /**

     * Disable trust checks for SSL connections.

     */



    public
    static  void  disableChecks() {

        try
        {

            new
                    URL("https://0.0.0.0/").getContent();

        } catch
                (IOException e) {

            // This invocation will always fail, but it will register the

            // default SSL provider to the URL class.

        }

        try
        {

            SSLContext sslc;

            sslc = SSLContext.getInstance("TLS");

            TrustManager[] trustManagerArray = {new
                    X509TrustManager() {

                        @Override

                        public
                        void  checkClientTrusted(X509Certificate[] chain, String authType) throws
                                CertificateException {



                        }



                        @Override

                        public
                        void  checkServerTrusted(X509Certificate[] chain, String authType) throws
                                CertificateException {



                        }



                        @Override

                        public
                        X509Certificate[] getAcceptedIssuers() {

                            return
                                    new  X509Certificate[0];

                        }

                    }};

            sslc.init(null, trustManagerArray, null);

            HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                                                                      @Override

                                                                      public
                                                                      boolean  verify(String s, SSLSession sslSession) {

                                                                          return
                                                                                  true;

                                                                      }

                                                                  });

        } catch
                (Exception e) {

//            LOGGER.error("error msg:{}", e);
            e.printStackTrace();

            throw
                    new  IllegalArgumentException("证书校验异常！");

        }

    }
}
