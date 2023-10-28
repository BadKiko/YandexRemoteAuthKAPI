package com.kiko.yandexremoteauthkapi

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kiko.yandexremoteauthapi.YandexRemoteAuth
import com.kiko.yandexremoteauthapi.data.auth.remote.dto.AuthRequestEntity
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeRequestEntity
import com.kiko.yandexremoteauthapi.data.common.AuthYandexAuthState
import com.kiko.yandexremoteauthapi.data.common.CodeYandexAuthState
import com.kiko.yandexremoteauthkapi.ui.theme.YandexRemoteAuthKAPITheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YandexRemoteAuthKAPITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    var value by remember { mutableStateOf("check") }

                    val yandexRemoteAuth = YandexRemoteAuth.create()

                    LaunchedEffect(true) {
                        when (val codeResponse = yandexRemoteAuth.getCode(
                            CodeRequestEntity(
                                BuildConfig.YANDEX_CLIENT_ID,
                                UUID.randomUUID().toString(),
                                Build.MODEL
                            )
                        )) {
                            is CodeYandexAuthState.Error -> value = codeResponse.message
                            is CodeYandexAuthState.Success -> {
                                value = codeResponse.data.userCode
                                when (val authResponse = yandexRemoteAuth.getAuth(
                                    AuthRequestEntity(
                                        "device_code",
                                        codeResponse.data.deviceCode,
                                        BuildConfig.YANDEX_CLIENT_ID,
                                        BuildConfig.YANDEX_CLIENT_SECRET,
                                        codeResponse.data.interval,
                                        codeResponse.data.expiresIn
                                    )
                                )) {
                                    is AuthYandexAuthState.Error -> {
                                        value = "error in auth"
                                    }
                                    is AuthYandexAuthState.Success -> {
                                        value = "success! token = ${authResponse.data.accessToken}"
                                    }
                                }
                            }
                        }
                    }

                    Text(value)
                }
            }
        }
    }
}
