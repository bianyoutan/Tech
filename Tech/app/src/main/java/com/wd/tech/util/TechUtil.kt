package com.wd.tech.util

import android.content.Context
import com.wd.tech.api.TechApi
import com.wd.tech.base.BaseApplication
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.IOException
import java.lang.Exception
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.collections.HashMap

/**
 * <p>文件描述：<p>
 * <p>作者：包志燕<p>
 * <p>创建时间：2019/12/20<p>
 * <p>更改时间：2019/12/20<p>
 */

class TechUtil private constructor(){
    var techApi:TechApi? = null;
    companion object{
        val instances:TechUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            TechUtil();
        }
    }

    init {
        val x509TrustManager:X509TrustManager = getTrustManager("server.crt")

        var httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(false)
            .sslSocketFactory(getSSLSocketFactory(x509TrustManager), x509TrustManager)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl("https://mobile.bwstudent.com/")
            .client(okHttpClient)
            .build()

        techApi = retrofit.create(TechApi::class.java)
    }

    fun getNone(url: String, callBack: CallBack) {
        techApi!!.getNone(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getobserver(callBack))
    }

    fun postHead(url: String, map: HashMap<String, String>, callBack: CallBack) {
        techApi!!.postHead(url,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getobserver(callBack))
    }

    private fun getobserver(callBack: CallBack?): Observer<ResponseBody> {
        return  object : Observer<ResponseBody> {
            override fun onError(e: Throwable?) {

            }

            override fun onNext(responseBody: ResponseBody?) {
                try {
                    callBack!!.onSuccess(responseBody.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                    callBack!!.onError();
                }

            }

            override fun onCompleted() {

            }
        }

    }

    open interface CallBack {
        fun onSuccess(jsonStr: String)
        fun onError()
    }

    fun getTrustManager(certificateName: String):X509TrustManager{
        var tCertFactory = CertificateFactory.getInstance("X.509")
        val tJDBCert = readJDBCertFromAsset(BaseApplication.getAppContext(), certificateName, tCertFactory)
        val tTrustMgr = newTrustManager(tJDBCert)

        return  tTrustMgr
    }

    fun getSSLSocketFactory(trustManager: X509TrustManager): SSLSocketFactory? {
        try {
            val tSSLContext = newSSLContext(trustManager)
            val sslSocketFactory = tSSLContext.socketFactory
            return sslSocketFactory
        } catch ( e:CertificateException) {
            e.printStackTrace()
        } catch (e:IOException ) {
            e.printStackTrace()
        } catch ( e:NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch ( e:KeyManagementException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * xxx.cer证书 -> X509Certificate
     */
    @Throws(IOException::class, CertificateException::class)
    private fun readJDBCertFromAsset(context: Context, certificateName: String, pCertFactory: CertificateFactory): X509Certificate? {
        val caInput = context.assets.open(certificateName) ?: return null

        try {
            val tCert = pCertFactory.generateCertificate(caInput) as X509Certificate
            tCert.checkValidity()
            return tCert
        } finally {
            caInput.close()
        }
    }

    /**
     * X509TrustManager实例化 Android 自带的一套https认证机制
     */
    fun newTrustManager(privateCert: X509Certificate?): X509TrustManager {
        return object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>?, authType: String?) {
                if (null == chain || 0 == chain.size) {
                    throw CertificateException("Certificate chain is invalid.")
                }

                if (null == authType || 0 == authType.length) {
                    throw CertificateException("Authentication type is invalid.")
                }

                for (cert in chain) {
                    cert.checkValidity()
                    try {
                        cert.verify(privateCert!!.publicKey)
                    } catch (e: NoSuchAlgorithmException) {
                        e.printStackTrace()
                    } catch (e: InvalidKeyException) {
                        e.printStackTrace()
                    } catch (e: NoSuchProviderException) {
                        e.printStackTrace()
                    } catch (e: SignatureException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }

    @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
    private fun newSSLContext(pTrustManager: X509TrustManager): SSLContext {
        val tSSLContext = SSLContext.getInstance("TLS")
        tSSLContext.init(null, arrayOf<TrustManager>(pTrustManager), null)
        return tSSLContext
    }
}
