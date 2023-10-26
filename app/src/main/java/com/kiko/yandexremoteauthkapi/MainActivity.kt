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
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeRequestEntity
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
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var value by remember { mutableStateOf("check") }

                    val yandexRemoteAuth = YandexRemoteAuth.create()

                    LaunchedEffect(true) {
                        when (val yandexRemoteAuth = yandexRemoteAuth.getCode(
                            CodeRequestEntity(
                                "23cabbbdc6cd418abb4b39c32c41195d",
                                UUID.randomUUID().toString(),
                                Build.MODEL
                            )
                        )) {
                            is CodeYandexAuthState.Error -> value = yandexRemoteAuth.message
                            is CodeYandexAuthState.Success -> value = yandexRemoteAuth.data.userCode
                        }
                    }

                    Text(value)
                }
            }
        }
    }
}
