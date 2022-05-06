package eu.maxkim.boredombuster.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import eu.maxkim.boredombuster.ui.theme.BoredomBusterTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoredomBusterTheme {
                BoredomBusterApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}