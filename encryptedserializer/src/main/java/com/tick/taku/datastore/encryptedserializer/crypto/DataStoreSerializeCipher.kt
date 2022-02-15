package com.tick.taku.datastore.encryptedserializer.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

internal class DataStoreSerializeCipher(
    private val keyAlias: String,
    private val algorithm: String = KeyProperties.KEY_ALGORITHM_AES,
    private val blockMode: String = KeyProperties.BLOCK_MODE_GCM,
    private val encryptionPadding: String = KeyProperties.ENCRYPTION_PADDING_NONE,
    private val keyStoreProvider: String = DEFAULT_PROVIDER
): Cryptor {

    internal companion object {
        const val DEFAULT_PROVIDER = "AndroidKeyStore"
    }

    private val cipher: Cipher by lazy {
        Cipher.getInstance("$algorithm/$blockMode/$encryptionPadding")
    }

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(keyStoreProvider).also {
            it.load(null)
        }
    }

    private val KeyStore.secretKey: SecretKey
        get() = (getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry)?.secretKey ?: keyGenerator.generateSecretKey()

    private val keyGenerator: KeyGenerator by lazy {
        KeyGenerator.getInstance(algorithm, keyStoreProvider)
    }

    private fun KeyGenerator.generateSecretKey() = also {
        it.init(
            KeyGenParameterSpec.Builder(keyAlias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(blockMode)
                .setEncryptionPaddings(encryptionPadding)
                .setUserAuthenticationRequired(false)
                .setRandomizedEncryptionRequired(true)
                .build()
        )
    }.generateKey()

    override fun encryptTo(data: ByteArray, outputStream: OutputStream) {
        val encryptCipher = cipher.also {
            it.init(Cipher.ENCRYPT_MODE, keyStore.secretKey)
        }
        val encryptedData = encryptCipher.doFinal(data)

        outputStream.use {
            it.write(encryptCipher.iv.size)
            it.write(encryptCipher.iv)
            it.write(encryptedData)
        }
    }

    override fun decryptFrom(inputStream: InputStream): ByteArray = inputStream.use {
        val iv = ByteArray(it.read()).also { iv ->
            it.read(iv)
        }
        val encryptedData = it.readBytes()

        cipher.also { cipher ->
            cipher.init(Cipher.DECRYPT_MODE, keyStore.secretKey, GCMParameterSpec(128, iv))
        }.doFinal(encryptedData)
    }
}