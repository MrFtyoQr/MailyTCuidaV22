package com.example.mailyt_cuida_v22.presentation.initial

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mailyt_cuida_v22.R
import com.example.mailyt_cuida_v22.GoogleAuthHelper
import com.example.mailyt_cuida_v22.ui.theme.BackGroundButton
import com.example.mailyt_cuida_v22.ui.theme.Black
import com.example.mailyt_cuida_v22.ui.theme.Gray
import com.example.mailyt_cuida_v22.ui.theme.Green
import com.example.mailyt_cuida_v22.ui.theme.Orange
import com.example.mailyt_cuida_v22.ui.theme.ShapeButton
import com.example.mailyt_cuida_v22.ui.theme.White

@Preview
@Composable
fun InitialScreen(
    navigateToLogin: () -> Unit = {}, 
    navigateToSignup: () -> Unit = {}, 
    navigateToHome: () -> Unit = {},
    googleAuthHelper: GoogleAuthHelper? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Gray, White), startY = 0f, endY = 600f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.maily),contentDescription = "")
        Text(
            "Maily",
            color = Color.Gray,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "T-Cuida AI",
            color = Color.Gray,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { 
            Log.d("InitialScreen", "Navegando a Login")
            navigateToLogin() 
        }, modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 95.dp), colors = ButtonDefaults.buttonColors(containerColor = Orange), shape = RoundedCornerShape(8.dp)) {
            Text(text = "Iniciar Sesión", color = Black, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            onGoogleClick = {
                Log.d("GoogleSignIn", "Botón de Google presionado")
                if (googleAuthHelper != null) {
                    Log.d("GoogleSignIn", "googleAuthHelper no es null, ejecutando signIn()")
                    googleAuthHelper.signIn()
                } else {
                    Log.e("GoogleSignIn", "googleAuthHelper es null!")
                }
            },
            painterResource(id = R.drawable.glog),
            "Continua con Google"
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            onGoogleClick = {
                Log.d("GithubSignIn", "Botón de Github presionado")
                // TODO: Implementar autenticación con Github
            },
            painterResource(id = R.drawable.github),
            "Continua con Github"
        )
        Text(
            text = "Registrarse",
            color = Color.Black,
            modifier = Modifier
                .padding(24.dp)
                .clickable { 
                    Log.d("InitialScreen", "Navegando a Registro")
                    navigateToSignup() 
                },
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
    }

}

@Composable
fun CustomButton(onGoogleClick: () -> Unit, painter: Painter, title: String) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .padding(horizontal = 95.dp)
        .background(BackGroundButton, shape = RoundedCornerShape(8.dp))
        .border(2.dp, ShapeButton, shape = RoundedCornerShape(8.dp))
        .clickable { onGoogleClick() },
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp)
        )
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
    }
}
