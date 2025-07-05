package com.example.mailyt_cuida_v22

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mailyt_cuida_v22.presentation.initial.InitialScreen
import com.example.mailyt_cuida_v22.presentation.login.LoginScreen
import com.example.mailyt_cuida_v22.presentation.signup.SignupScreen
import com.example.mailyt_cuida_v22.presentation.home.HomeScreen
import com.example.mailyt_cuida_v22.presentation.home.enfermeria.EnfermeriaScreen
import com.example.mailyt_cuida_v22.presentation.home.enfermeria.SignosVitalesScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth, googleAuthHelper: GoogleAuthHelper){

    NavHost(navController = navHostController, startDestination = "initial"){
        composable("initial"){
            InitialScreen(
                navigateToLogin = {navHostController.navigate("LogIn") },
                navigateToSignup = {navHostController.navigate("SignUp") },
                navigateToHome = {navHostController.navigate("Home") },
                googleAuthHelper = googleAuthHelper
            )
        }

        composable("login"){
            LoginScreen(auth, navHostController)
        }

        composable("signup"){
            SignupScreen(auth, navHostController)
        }

        composable("home"){
            HomeScreen(navHostController)
        }

        composable("enfermeria"){
            EnfermeriaScreen(navHostController)
        }

        composable("signos_vitales"){
            SignosVitalesScreen(navHostController)
        }
    }

}