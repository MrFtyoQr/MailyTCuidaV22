package com.example.mailyt_cuida_v22

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mailyt_cuida_v22.ui.theme.MailyTCuidaV22Theme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var auth: FirebaseAuth
    private lateinit var googleAuthHelper: GoogleAuthHelper
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate iniciado")
        
        auth = Firebase.auth
        Log.d("MainActivity", "Firebase Auth inicializado")
        
        // Initialize Google Sign-In launcher
        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            Log.d("MainActivity", "ActivityResult recibido")
            googleAuthHelper.handleSignInResult(result.data) {
                // Este bloque se ejecuta tras login exitoso con Google
                runOnUiThread {
                    navHostController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                    android.widget.Toast.makeText(this, "Inicio de sesión exitoso", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        // Initialize Google Auth Helper
        googleAuthHelper = GoogleAuthHelper(this, auth)
        googleAuthHelper.initialize(signInLauncher)
        Log.d("MainActivity", "GoogleAuthHelper inicializado")
        
        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            MailyTCuidaV22Theme {
                NavigationWrapper(navHostController, auth, googleAuthHelper)
            }
        }
        Log.d("MainActivity", "onCreate completado")
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //deberia navegar a la home
            Log.i("Joseph", "Estoy logado como: ${currentUser.email}")
        }
    }
}

