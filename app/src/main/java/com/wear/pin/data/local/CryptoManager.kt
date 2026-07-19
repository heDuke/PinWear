package com.wear.pin.data.local

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Handles symmetric encryption and decryption using the Android Keystore system.
 * The key never leaves the hardware boundary if supported by the device.
 */
class CryptoManager {
    private val keyStore by lazy {
        KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    }

    private val encryptCipher get() =
        Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getKey())
        }

    private fun getDecryptCipherForIv(iv: ByteArray): Cipher =
        Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), GCMParameterSpec(128, iv))
        }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey =
        KeyGenerator
            .getInstance(ALGORITHM)
            .apply {
                init(
                    KeyGenParameterSpec
                        .Builder(
                            ALIAS,
                            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                        ).setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(PADDING)
                        .setUserAuthenticationRequired(false)
                        .setRandomizedEncryptionRequired(true)
                        .build()
                )
            }.generateKey()

    /**
     * Encrypts the given bytes.
     * @return A Pair where the first element is the IV, and the second is the Ciphertext.
     */
    fun encrypt(bytes: ByteArray): Pair<ByteArray, ByteArray> {
        val cipher = encryptCipher
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return Pair(iv, encrypted)
    }

    /**
     * Decrypts the given ciphertext using the provided IV.
     */
    fun decrypt(
        iv: ByteArray,
        encrypted: ByteArray
    ): ByteArray {
        val cipher = getDecryptCipherForIv(iv)
        return cipher.doFinal(encrypted)
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
        private const val ALIAS = "pinwear_oauth_token_key"
    }
}
