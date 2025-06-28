package com.example.mailyt_cuida_v22.presentation.signup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mailyt_cuida_v22.R
import com.example.mailyt_cuida_v22.ui.theme.Black
import com.example.mailyt_cuida_v22.ui.theme.Gray
import com.example.mailyt_cuida_v22.ui.theme.SelectedField
import com.example.mailyt_cuida_v22.ui.theme.UnselectedField
import com.example.mailyt_cuida_v22.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignupScreen(auth: FirebaseAuth, navigateToHome: () -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Brush.verticalGradient(listOf(Gray, Black), startY = 0f, endY = 600f))
        .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(){
            Icon(
                painter = painterResource(id = R.drawable.ic_back_24),
                contentDescription = "",
                tint = White,
                modifier = Modifier
                    .padding(vertical = 30.dp)
                    .size(30.dp)
                    .clickable { navigateToHome() } // Navegar a la pantalla de inicio
            )
            Spacer(modifier = Modifier.weight(1f))
        }


        Spacer(modifier = Modifier.weight(1f))
        Text("Email", color = White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )
        Spacer(Modifier.height(48.dp))
        Text("Password", color = White, fontWeight = FontWeight.Bold, fontSize = 40.sp )
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    //Registrado
                    Log.i("Joseph", "Regidtrado correctamente con email: $email")
                }else{
                    //Error al registrarse
                    Log.e("Joseph", "Error al registrarse: ${task.exception?.message}")
                }
            }
        }) {
            Text(text = "Registrarse")
        }



        Spacer(modifier = Modifier.weight(6f))
    }

}