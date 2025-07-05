package com.example.mailyt_cuida_v22.presentation.home.enfermeria

// ─────────────────────────────────────────────────────────────────────────────
// Imports necesarios
// ─────────────────────────────────────────────────────────────────────────────
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// ─────────────────────────────────────────────────────────────────────────────
// Pantalla para ingresar todos los signos vitales
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignosVitalesScreen(navController: NavController) {
    // 1. Estados de cada campo
    var peso by remember { mutableStateOf("") }
    var estatura by remember { mutableStateOf("") }
    var frecuenciaCardiaca by remember { mutableStateOf("") }
    var frecuenciaRespiratoria by remember { mutableStateOf("") }
    var presionSistolica by remember { mutableStateOf("") }
    var presionDiastolica by remember { mutableStateOf("") }
    var saturacionOxigeno by remember { mutableStateOf("") }
    var temperatura by remember { mutableStateOf("") }
    var glucosa by remember { mutableStateOf("") }
    var trigliceridos by remember { mutableStateOf("") }
    var urea by remember { mutableStateOf("") }
    var creatinina by remember { mutableStateOf("") }
    var hemoglobina by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF192C59), Color.White),
                    startY = 0f, endY = 600f
                )
            )
    ) {
        Column(Modifier.fillMaxSize()) {
            // ─────────────────────────────────────────────────────────
            // TopAppBar
            // ─────────────────────────────────────────────────────────
            TopAppBar(
                title = { Text("Signos vitales", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0097A7))
            )

            // ─────────────────────────────────────────────────────────
            // Lista de inputs
            // ─────────────────────────────────────────────────────────
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp)
            ) {
                CampoEnfermeria(Icons.Default.MonitorWeight,      "Peso (kg)",               peso)               { peso = it }
                CampoEnfermeria(Icons.Default.Straighten,         "Estatura (cm)",           estatura)           { estatura = it }
                CampoEnfermeria(Icons.Default.Favorite,           "F. Cardíaca",             frecuenciaCardiaca) { frecuenciaCardiaca = it }
                CampoEnfermeria(Icons.Default.Air,                "F. Respiratoria",         frecuenciaRespiratoria) { frecuenciaRespiratoria = it }

                // Dos campos en fila para presión arterial
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = presionSistolica,
                        onValueChange = { presionSistolica = it },
                        label = { Text("P. Sistólica") },
                        leadingIcon = { Icon(Icons.Default.Add, contentDescription = null) },
                        modifier = Modifier.weight(1f).padding(end = 4.dp)
                    )
                    OutlinedTextField(
                        value = presionDiastolica,
                        onValueChange = { presionDiastolica = it },
                        label = { Text("P. Diastólica") },
                        leadingIcon = { Icon(Icons.Default.Remove, contentDescription = null) },
                        modifier = Modifier.weight(1f).padding(start = 4.dp)
                    )
                }

                CampoEnfermeria(Icons.Default.AirlineSeatFlat,    "Sat O₂ (%)",              saturacionOxigeno)  { saturacionOxigeno = it }
                CampoEnfermeria(Icons.Default.DeviceThermostat,   "Temperatura (°C)",        temperatura)        { temperatura = it }
                CampoEnfermeria(Icons.Default.Bloodtype,          "Glucosa (mg/dL)",         glucosa)            { glucosa = it }
                CampoEnfermeria(Icons.Default.LocalFireDepartment,"Triglicéridos (mg/dL)",   trigliceridos)      { trigliceridos = it }
                CampoEnfermeria(Icons.Default.Healing,            "Urea (mg/dL)",            urea)               { urea = it }
                CampoEnfermeria(Icons.Default.MedicalServices,             "Creatinina (mg/dL)",      creatinina)         { creatinina = it }
                CampoEnfermeria(Icons.Default.Bloodtype,          "Hemoglobina (g/dL)",      hemoglobina)        { hemoglobina = it }

                Spacer(Modifier.height(80.dp))
            }
        }

        // ─────────────────────────────────────────────────────────
        // Botón Guardar (Extended FAB)
        // ─────────────────────────────────────────────────────────
        ExtendedFloatingActionButton(
            text = { Text("Guardar") },
            onClick = {
                // 1) Guardamos cada valor individualmente en el SavedStateHandle
                navController.previousBackStackEntry?.savedStateHandle?.apply {
                    set("peso", peso)
                    set("estatura", estatura)
                    set("frecuenciaCardiaca", frecuenciaCardiaca)
                    set("frecuenciaRespiratoria", frecuenciaRespiratoria)
                    set("presionSistolica", presionSistolica)
                    set("presionDiastolica", presionDiastolica)
                    set("saturacionOxigeno", saturacionOxigeno)
                    set("temperatura", temperatura)
                    set("glucosa", glucosa)
                    set("trigliceridos", trigliceridos)
                    set("urea", urea)
                    set("creatinina", creatinina)
                    set("hemoglobina", hemoglobina)
                }
                // 2) Navegamos atrás
                navController.popBackStack()
            },
            icon = { /* opcional: poner un ícono */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .navigationBarsPadding(),
            containerColor = Color(0xFFE1BEE7)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// 6. Reutilizamos tu CampoEnfermeria para los inputs de texto
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun CampoEnfermeria(
    icon: ImageVector,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    )
}
