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
import com.example.mailyt_cuida_v22.presentation.home.enfermeria.graficas.EstaturaGraficaScreen
import com.example.mailyt_cuida_v22.presentation.home.enfermeria.graficas.VitalGraficaScreen
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

        // Rutas de gráficas usando la pantalla genérica
        composable("estatura_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "estatura",
                vitalLabel = "Estatura",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("peso_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "peso",
                vitalLabel = "Peso",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("frecuencia_cardiaca_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "frecuencia_cardiaca",
                vitalLabel = "Frecuencia Cardíaca",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("frecuencia_respiratoria_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "frecuencia_respiratoria",
                vitalLabel = "Frecuencia Respiratoria",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("presion_sistolica_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "presion_sistolica",
                vitalLabel = "Presión Sistólica",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("presion_diastolica_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "presion_diastolica",
                vitalLabel = "Presión Diastólica",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("saturacion_oxigeno_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "saturacion_oxigeno",
                vitalLabel = "Saturación de Oxígeno",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("temperatura_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "temperatura",
                vitalLabel = "Temperatura",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("glucosa_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "glucosa",
                vitalLabel = "Glucosa",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("trigliceridos_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "trigliceridos",
                vitalLabel = "Triglicéridos",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("urea_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "urea",
                vitalLabel = "Urea",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("creatinina_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "creatinina",
                vitalLabel = "Creatinina",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
        
        composable("hemoglobina_grafica"){
            VitalGraficaScreen(
                navController = navHostController,
                vitalType = "hemoglobina",
                vitalLabel = "Hemoglobina",
                dataExtractor = { it?.toFloatOrNull() }
            )
        }
    }

}