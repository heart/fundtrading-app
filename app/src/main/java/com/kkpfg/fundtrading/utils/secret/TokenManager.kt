import android.annotation.SuppressLint
import android.content.Context
import com.cioccarellia.ksprefs.KsPrefs
import com.cioccarellia.ksprefs.config.EncryptionType
import com.cioccarellia.ksprefs.config.model.KeySize
import com.cioccarellia.ksprefs.config.model.KeyTagSize
import java.security.KeyStore


class TokenManager(private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance:TokenManager? = null

        fun getTokenManager(): TokenManager{
            if(instance == null){
                throw Exception("TokenManager are not initialize yet.")
            }
            return instance!!
        }

        fun initTokenManager(applicationContext: Context):TokenManager {
            if(instance == null) {
                instance = TokenManager(applicationContext)
            }
            return instance!!
        }
    }

    private var prefs: KsPrefs? = null

    init{
        prefs = KsPrefs(context){
            encryptionType = EncryptionType.KeyStore("token_keystore", KeyTagSize.SIZE_128_BITS)
            //encryptionType = EncryptionType.AesEcb("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", KeySize.SIZE_256_BITS)
        }
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        prefs?.let{
            it.push("accessToken", accessToken)
            it.push("refreshToken", refreshToken)
        }
    }

    fun getAccessToken(): String? {
        try{
            val token =  prefs?.pull<String>("accessToken")
            if(token != null){
                return "Bearer $token"
            }
        }catch (_:Exception){
        }
        return null
    }

    fun getRefreshToken(): String? {
        try{
            val token = prefs?.pull<String>("refreshToken")
            if(token != null){
                return "Bearer $token"
            }
        }catch (_:Exception){
        }
        return null
    }

}
