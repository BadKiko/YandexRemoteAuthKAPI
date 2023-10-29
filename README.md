# Yandex Remote Auth API for Kotlin

Это неофициальная библиотека для взаимодействия с API Удаленной аутентификации через код пару на языке Kotlin. 
## Использование
### Установка 

Для использования библиотеки вам  сперва необходимо добавить jitpack

> Для Groovy:

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

> Для Kotlin DSL:

```kotlin
repositories {
  ...
  maven(url="https://jitpack.io")
}
```

И добавить библиотеку в build.gradle dependency

[![](https://jitpack.io/v/BadKiko/YandexMusicKAPI.svg)](https://jitpack.io/#BadKiko/YandexMusicKAPI)

> Для Groovy:

```groovy
implementation 'com.github.BadKiko:YandexRemoteAuthKAPI:version'
```

> Для Kotlin DSL:

```kotlin
implementation("com.github.BadKiko:YandexRemoteAuthKAPI:version")
```

### Использование 

Для начала вам необходимо создать YandexClient instance
```kotlin
val yandexRemoteAuth = YandexRemoteAuth.create()
```

#### Примеры дальнейшего использования 

```kotlin
yandexRemoteAuth.getCode(
    CodeRequestEntity(
        BuildConfig.YANDEX_CLIENT_ID,
        UUID.randomUUID().toString(),
        Build.MODEL
    )
).let {
    codeResponse ->
    when (codeResponse) {
        is CodeYandexAuthState.Error -> value = codeResponse.message
        is CodeYandexAuthState.Success -> {
            value = codeResponse.data.userCode

            yandexRemoteAuth.getAuth(
                AuthRequestEntity(
                    "device_code",
                    codeResponse.data.deviceCode,
                    BuildConfig.YANDEX_CLIENT_ID,
                    BuildConfig.YANDEX_CLIENT_SECRET,
                    codeResponse.data.interval,
                    codeResponse.data.expiresIn
                )
            ).let { authResponse ->
                value = when (authResponse) {
                    is AuthYandexAuthState.Error -> {
                        "error in auth"
                    }

                    is AuthYandexAuthState.Success -> {
                        "success! token = ${authResponse.data.accessToken}"
                    }
                }
            }
        }
    }
}
```

## Используемые библиотеки

* [Retrofit](https://github.com/square/retrofit) - HTTP-клиент для Android и Java
* [SealedX](https://github.com/skydoves/sealedx) - утилитарная библиотека для работы с sealed классами
* [Sandwich](https://github.com/skydoves/sandwich) - утилитарная библиотека для работы с HTTP-ответами
* [Moshi](https://github.com/square/moshi) - библиотека для сериализации и десериализации JSON
