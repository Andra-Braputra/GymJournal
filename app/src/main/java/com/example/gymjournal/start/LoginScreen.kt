package com.example.gymjournal.start

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gymjournal.ui.theme.AppTheme


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode")
@Composable
fun LoginScreenPreview(){
    AppTheme {
        LoginScreen()
    }
}

@Composable
fun LoginScreen(){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}