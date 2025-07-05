package com.example.mailyt_cuida_v22.presentation.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import com.example.mailyt_cuida_v22.ui.theme.Orange
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

@Composable
fun SignupScreen(auth: FirebaseAuth, navController: NavHostController) {
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Brush.verticalGradient(listOf(Gray, White), startY = 0f, endY = 600f))
        .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(2f))

        Row(){
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.weight(2f))
        Image(painter = painterResource(id = R.drawable.maily),contentDescription = "")
        Text(
            text = "Maily", fontSize = 35.sp, fontWeight = FontWeight.Normal
        )
        Text(
            text = "T-Cuida", fontSize = 18.sp, fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.weight(5f))
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            placeholder = {
                Text(
                    "Nombre (s)", fontSize = 14.sp, color = Color.Gray
                )
            },
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
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        TextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            placeholder = {
                Text(
                    "Apellidos", fontSize = 14.sp, color = Color.Gray
                )
            },
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
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text(
                    "Correo electrónico", fontSize = 14.sp, color = Color.Gray
                )
            },
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
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = {
                Text(
                    "Contraseña", fontSize = 14.sp, color = Color.Gray
                )
            },
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
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
        // Checkbox de Términos y Condiciones
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 19.dp)
        ) {
            Checkbox(
                checked = aceptaTerminos,
                onCheckedChange = { aceptaTerminos = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFFF9800)
                )
            )
            Text(
                "Acepto los Términos y Condiciones de Maily.", fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(35.dp))
        Button(
            onClick = {
                if (aceptaTerminos && nombre.isNotBlank() && apellidos.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                    Log.d("Signup", "Intentando registrar usuario: $email")
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                .setDisplayName("$nombre $apellidos")
                                .build()
                            user?.updateProfile(profileUpdates)?.addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    Log.i("User", "Registrado correctamente con email: $email y nombre: $nombre $apellidos")
                                    Toast.makeText(context, "Registro completado correctamente", Toast.LENGTH_SHORT).show()
                                    navController.navigate("login") {
                                        popUpTo("signup") { inclusive = true }
                                    }
                                } else {
                                    Log.e("User", "Error al actualizar nombre: ${updateTask.exception?.message}")
                                    Toast.makeText(context, "Error al guardar el nombre. Intenta de nuevo.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            val exception = task.exception
                            Log.e("User", "Error al registrarse: ${exception?.message}")
                            val errorMsg = when {
                                exception?.message?.contains("The email address is already in use") == true -> {
                                    "El correo ya está ligado a otra cuenta"
                                }
                                exception?.message?.contains("The email address is badly formatted") == true -> {
                                    "El correo no es válido"
                                }
                                exception?.message?.contains("Password should be at least") == true -> {
                                    "La contraseña es demasiado corta"
                                }
                                else -> {
                                    "Error desconocido: ${exception?.localizedMessage ?: "Intenta de nuevo"}"
                                }
                            }
                            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val msg = when {
                        !aceptaTerminos -> "Debes aceptar los términos y condiciones para continuar"
                        nombre.isBlank() || apellidos.isBlank() || email.isBlank() || password.isBlank() -> "Todos los campos deben estar llenos para registrarse"
                        else -> "Error desconocido"
                    }
                    Log.w("Signup", msg)
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(horizontal = 100.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange),
            shape = RoundedCornerShape(8.dp),
            enabled = aceptaTerminos
        ) {
            Text(text = "Registrarse")
        }

        Spacer(modifier = Modifier.weight(7f))
    }
}