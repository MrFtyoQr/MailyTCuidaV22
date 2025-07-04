package com.example.mailyt_cuida_v22

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleAuthHelper(
    private val context: Context,
    private val auth: FirebaseAuth
) {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    
    companion object {
        private const val TAG = "GoogleAuthHelper"
    }

    fun initialize(signInLauncher: ActivityResultLauncher<Intent>) {
        this.signInLauncher = signInLauncher
        
        Log.d(TAG, "Inicializando GoogleAuthHelper")
        
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1096716348860-btj3rb38d85fg1d31jfi0ui5tm8jnog7.apps.googleusercontent.com") // Reemplazar con tu Web Client ID de Firebase Console
            .requestEmail()
            .build()

        Log.d(TAG, "GoogleSignInOptions configurado")
        googleSignInClient = GoogleSignIn.getClient(context, gso)
        Log.d(TAG, "GoogleSignInClient creado")
    }

    fun signIn() {
        Log.d(TAG, "signIn() llamado")
        try {
            val signInIntent = googleSignInClient.signInIntent
            Log.d(TAG, "Intent de Google Sign-In creado, lanzando...")
            signInLauncher.launch(signInIntent)
        } catch (e: Exception) {
            Log.e(TAG, "Error al lanzar Google Sign-In", e)
        }
    }

    fun handleSignInResult(data: Intent?, onLoginSuccess: () -> Unit) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account, onLoginSuccess)
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
            when (e.statusCode) {
                10 -> {
                    Log.e(TAG, "DEVELOPER_ERROR: Verifica la configuración en Firebase Console")
                    Log.e(TAG, "1. Asegúrate de que Google Sign-In esté habilitado")
                    Log.e(TAG, "2. Verifica que el google-services.json esté actualizado")
                    Log.e(TAG, "3. Verifica que el Web Client ID sea correcto")
                }
                12501 -> Log.e(TAG, "SIGN_IN_CANCELLED: Usuario canceló el sign-in")
                12500 -> Log.e(TAG, "SIGN_IN_FAILED: Error interno")
                else -> Log.e(TAG, "Error desconocido: ${e.statusCode}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount, onLoginSuccess: () -> Unit) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.i("Joseph", "Login con Google exitoso: ${user?.email}")
                    onLoginSuccess()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Log.e("Joseph", "Error en login con Google: ${task.exception?.message}")
                }
            }
    }

    fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
    }
} 