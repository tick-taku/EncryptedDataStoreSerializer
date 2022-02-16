package com.tick.taku.datastore.encryptedserializer

import android.security.keystore.KeyProperties
import androidx.datastore.core.Serializer
import com.tick.taku.datastore.encryptedserializer.crypto.Cryptor
import com.tick.taku.datastore.encryptedserializer.crypto.DataStoreSerializeCipher
import java.io.InputStream
import java.io.OutputStream

abstract class DataStoreCryptoSerializer <T: Any> @JvmOverloads constructor(
    keyAlias: String,
    algorithm: String = KeyProperties.KEY_ALGORITHM_AES,
    blockMode: String = KeyProperties.BLOCK_MODE_GCM,
    encryptionPadding: String = KeyProperties.ENCRYPTION_PADDING_NONE,
    keyStoreProvider: String = DataStoreSerializeCipher.DEFAULT_PROVIDER
): Serializer<T> {

    private val cipher: Cryptor by lazy {
        DataStoreSerializeCipher(
            keyAlias = keyAlias,
            algorithm = algorithm,
            blockMode = blockMode,
            encryptionPadding = encryptionPadding,
            keyStoreProvider = keyStoreProvider
        )
    }

    override suspend fun readFrom(input: InputStream): T {
        return decodeFrom(
            data = cipher.decryptFrom(input).decodeToString()
        )
    }

    abstract fun decodeFrom(data: String): T

    override suspend fun writeTo(t: T, output: OutputStream) {
        cipher.encryptTo(
            data = encodeTo(t).encodeToByteArray(),
            outputStream = output
        )
    }

    abstract fun encodeTo(data: T): String
}