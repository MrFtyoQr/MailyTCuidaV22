package com.example.mailyt_cuida_v22

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mailyt_cuida_v22.presentation.initial.InitialScreen
import com.example.mailyt_cuida_v22.presentation.login.LoginScreen
import com.example.mailyt_cuida_v22.presentation.signup.SignupScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth){

    NavHost(navController = navHostController, startDestination = "initial"){
        composable("initial"){
            InitialScreen(
                navigateToLogin = {navHostController.navigate("LogIn") },
                navigateToSignup = {navHostController.navigate("SignUp") }
            )
        }

        composable("login"){
            LoginScreen(auth, navHostController)
        }

        composable("signup"){
            SignupScreen(auth, navHostController)
        }
    }

}