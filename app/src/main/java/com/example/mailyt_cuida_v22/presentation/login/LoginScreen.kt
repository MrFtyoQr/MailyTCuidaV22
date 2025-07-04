package com.example.mailyt_cuida_v22.presentation.login

import android.net.wifi.hotspot2.pps.HomeSp
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mailyt_cuida_v22.ui.theme.Black
import com.example.mailyt_cuida_v22.ui.theme.White
import com.google.firebase.auth.FirebaseAuth
import com.example.mailyt_cuida_v22.R
import com.example.mailyt_cuida_v22.ui.theme.Gray
import com.example.mailyt_cuida_v22.ui.theme.Red
import com.example.mailyt_cuida_v22.ui.theme.SelectedField
import com.example.mailyt_cuida_v22.ui.theme.UnselectedField
import java.nio.file.WatchEvent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.navigation.NavHostController
import com.example.mailyt_cuida_v22.ui.theme.Orange
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

@Composable
fun LoginScreen(auth: FirebaseAuth, navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Brush.verticalGradient(listOf(Gray, White), startY = 0f, endY = 600f))
        .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Row(){
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }


        Spacer(modifier = Modifier.weight(2f))
        //De esta manera importo la imagen que tengo en la carpeeta de drawable, la centro y le dare altura mas abajo con
        // las medidas f qque exactamente no se que es pero funciona en todo el column asi que me gusta
        Image(painter = painterResource(id = R.drawable.maily), contentDescription = "")
        Text(
            text = "Maily", fontSize = 35.sp, fontWeight = FontWeight.Normal
        )
        Text(
            text = "T-Cuida", fontSize = 18.sp, fontWeight = FontWeight.Normal
        )

        //Empezare a comentar jsjsjs, aqui estoy dejando un espacio de 5f para que el simbolo se vea de arribita, mas parecido a
        // la app original y va aqui por que esta abajo del texto de maily t cuida para que tenga una mejor apariencia
        Spacer(modifier = Modifier.weight(5f))
        //Los textfield son los que permiten que introduzcamos textos, o datos no importa pero son
        //textos, y mas abajo en el boton esta la logica de verdad que jalara los value o valores de los textfield
        //como vez en este caso son el email y el password, que son los que se necesitan para iniciar sesion
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Correo", fontSize = 14.sp, color = Color.Gray) },
            modifier = Modifier
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp)
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Gray,
                unfocusedTextColor = Color.Gray
            )
        )
        Spacer(Modifier.height(48.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Contraseña", fontSize = 14.sp, color = Color.Gray) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp)
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Gray,
                unfocusedTextColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = {
            when {
                email.isBlank() || password.isBlank() -> {
                    Log.w("Login", "Ningún campo debe estar vacío")
                    Toast.makeText(context, "Ningún campo debe estar vacío", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.d("Login", "Intentando login con: $email")
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.i("User", "Login successful")
                            Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            val exception = task.exception
                            Log.e("User", "Login failed: ${exception?.message}")
                            val errorMsg = when {
                                exception?.message?.contains("There is no user record") == true -> {
                                    "Esta cuenta no existe"
                                }
                                exception?.message?.contains("The password is invalid") == true -> {
                                    "El correo y/o la contraseña están mal"
                                }
                                exception?.message?.contains("The email address is badly formatted") == true -> {
                                    "El correo no es válido"
                                }
                                else -> {
                                    "Error desconocido: ${exception?.localizedMessage ?: "Intenta de nuevo"}"
                                }
                            }
                            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        },
            modifier = Modifier.padding(horizontal = 100.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange),
            shape = RoundedCornerShape(8.dp)
            ) {
            Text(text = "Iniciar Sesión")
        }



        Spacer(modifier = Modifier.weight(6f))
    }
}


