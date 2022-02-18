# EncryptedDataStoreSerializer

Serializer that encrypt when saving/reading data for Android DataStore Proto.

## Setup

```groovy
dependencies {
    implementation 'io.github.tick-taku:datastore.encryptedserializer:1.0.0'
}
```

## Usage

1. You can use `DataStoreCyprtoSerializer` to make custom serializer.

```kotlin
class InputTextSerializer(
    private val stringFormat: StringFormat = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
): DataStoreCryptoSerializer<InputText>("input_text") {

    override val defaultValue: InputText
        get() = InputText()

    override fun encodeTo(data: InputText): String = stringFormat.encodeToString(data)

    override fun decodeFrom(data: String): InputText = runCatching {
        stringFormat.decodeFromString(data) as InputText
    }.getOrElse {
        if (it is SerializationException) throw CorruptionException("Cannot read proto.", it)
        else throw it
    }
}
```

2. Pass serializer when dataStore initialization.

```kotlin
class TextProtoDataStore(
    private val context: Context,
    serializer: Serializer<InputText>
) {

    private val Context.dataStore: DataStore<InputText> by dataStore(
        fileName = "text.pb",
        serializer = serializer
    )

}
```

3. Check saved file.

If you using Android Studio, you can use DeviceFileExplorer.

- `data/data/<yout application>/files/datastore/<your pb file>`
