package com.wear.pin.data.local

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.wear.pin.domain.model.OAuthToken
import com.wear.pin.domain.repository.TokenStorage
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

@Serializable
private data class OAuthTokenEntity(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val scope: String,
    val refreshToken: String?,
    val refreshTokenExpiresIn: Long? = null,
    val acquiredAt: Long
)

private val json = Json { ignoreUnknownKeys = true }

/**
 * Implementation of TokenStorage that securely persists the token in DataStore,
 * encrypting it using CryptoManager.
 */
class DataStoreTokenStorage(
    private val context: Context,
    private val cryptoManager: CryptoManager = CryptoManager()
) : TokenStorage {
    override suspend fun saveToken(token: OAuthToken) {
        val entity =
            OAuthTokenEntity(
                accessToken = token.accessToken,
                tokenType = token.tokenType,
                expiresIn = token.expiresIn,
                scope = token.scope,
                refreshToken = token.refreshToken,
                refreshTokenExpiresIn = token.refreshTokenExpiresIn,
                acquiredAt = token.acquiredAt
            )
        val jsonString = json.encodeToString(entity)

        val (iv, cipherText) = cryptoManager.encrypt(jsonString.toByteArray(Charsets.UTF_8))
        val ivBase64 = Base64.encodeToString(iv, Base64.NO_WRAP)
        val cipherTextBase64 = Base64.encodeToString(cipherText, Base64.NO_WRAP)

        context.tokenDataStore.edit { prefs ->
            prefs[TOKEN_IV_KEY] = ivBase64
            prefs[TOKEN_CIPHER_KEY] = cipherTextBase64
        }
    }

    override suspend fun loadToken(): OAuthToken? {
        val prefs = context.tokenDataStore.data.first()
        val ivBase64 = prefs[TOKEN_IV_KEY] ?: return null
        val cipherTextBase64 = prefs[TOKEN_CIPHER_KEY] ?: return null

        return try {
            val iv = Base64.decode(ivBase64, Base64.NO_WRAP)
            val cipherText = Base64.decode(cipherTextBase64, Base64.NO_WRAP)

            val decryptedBytes = cryptoManager.decrypt(iv, cipherText)
            val jsonString = String(decryptedBytes, Charsets.UTF_8)

            val entity = json.decodeFromString<OAuthTokenEntity>(jsonString)
            OAuthToken(
                accessToken = entity.accessToken,
                tokenType = entity.tokenType,
                expiresIn = entity.expiresIn,
                scope = entity.scope,
                refreshToken = entity.refreshToken,
                refreshTokenExpiresIn = entity.refreshTokenExpiresIn,
                acquiredAt = entity.acquiredAt
            )
        } catch (e: Exception) {
            Log.e("TokenStorage", "Error loading token", e)
            null
        }
    }

    override suspend fun clearToken() {
        context.tokenDataStore.edit { prefs ->
            prefs.remove(TOKEN_IV_KEY)
            prefs.remove(TOKEN_CIPHER_KEY)
        }
    }

    companion object {
        private val TOKEN_IV_KEY = stringPreferencesKey("oauth_token_iv")
        private val TOKEN_CIPHER_KEY = stringPreferencesKey("oauth_token_cipher")
    }
}
