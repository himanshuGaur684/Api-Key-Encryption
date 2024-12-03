package gaur.himanshu.apikeyencryption.remote

import android.content.Context
import gaur.himanshu.apikeyencryption.MY_IP
import gaur.himanshu.apikeyencryption.R
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class RetrofitInstance(private val context:Context) {

    private val certificateInputStream = context.resources.openRawResource(R.raw.cert)
    val certificateFactory = CertificateFactory.getInstance("X.509")
    val certificate = certificateFactory.generateCertificate(certificateInputStream)

    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
        load(null,null)
        setCertificateEntry("ca",certificate)
    }

    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
        init(keyStore)
    }

    val sslContext = SSLContext.getInstance("TLS").apply {
        init(null,trustManagerFactory.trustManagers,null)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(
            sslSocketFactory = sslContext.socketFactory,
            trustManager = trustManagerFactory.trustManagers.first() as X509TrustManager
        )
        .hostnameVerifier({ hostName, sslSession -> hostName.equals(MY_IP) })
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://${MY_IP}:4000/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}

interface ApiService {
    @GET("secure_backend")
    suspend fun connect(): CustomMessage
}

data class CustomMessage(
    val message: String = ""
)