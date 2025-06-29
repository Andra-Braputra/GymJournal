package com.example.gymjournal.presentations.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymjournal.R
import com.example.gymjournal.core.constant.Routes

@Composable
fun StartScreen(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.gymjournal),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        )
        Button(
            onClick = { navController.navigate(Routes.LOGIN) },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(horizontal = 16.dp)
        ) {
            Text("Login")
        }
        Button(
            onClick = {navController.navigate(Routes.REGISTER)},
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(horizontal = 16.dp)
        ) {
            Text("Sign-up")
        }
    }
}