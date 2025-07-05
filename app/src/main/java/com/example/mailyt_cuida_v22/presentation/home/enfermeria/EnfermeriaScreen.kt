package com.example.mailyt_cuida_v22.presentation.home.enfermeria

// ─────────────────────────────────────────────────────────────────────────────
// 1. Imports necesarios
// ─────────────────────────────────────────────────────────────────────────────
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.draw.drawWithContent

// ─────────────────────────────────────────────────────────────────────────────
// 2. Modelo que define cada rango de IMC (nombre, color, valores mínimo y máximo)
// ─────────────────────────────────────────────────────────────────────────────
data class IMCRango(
    val nombre: String,
    val color: Color,
    val min: Float,
    val max: Float
)

// ─────────────────────────────────────────────────────────────────────────────
// 3. Modelo para pasar datos de vuelta
// ─────────────────────────────────────────────────────────────────────────────
data class SignosData(
    val peso: String,
    val estatura: String,
    val frecuenciaCardiaca: String,
    val frecuenciaRespiratoria: String,
    val presionSistolica: String,
    val presionDiastolica: String,
    val saturacionOxigeno: String,
    val temperatura: String,
    val glucosa: String,
    val trigliceridos: String,
    val urea: String,
    val creatinina: String,
    val hemoglobina: String
)

// ─────────────────────────────────────────────────────────────────────────────
// 4. Pantalla principal de Enfermería
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnfermeriaScreen(navController: NavController) {
    // 4.1. Estados que muestran los valores actuales
    var peso by remember { mutableStateOf("0") }
    var estatura by remember { mutableStateOf("0") }
    var frecuenciaCardiaca by remember { mutableStateOf("0") }
    var frecuenciaRespiratoria by remember { mutableStateOf("0") }
    var presionSistolica by remember { mutableStateOf("0") }
    var presionDiastolica by remember { mutableStateOf("0") }
    var saturacionOxigeno by remember { mutableStateOf("0") }
    var temperatura by remember { mutableStateOf("0") }
    var glucosa by remember { mutableStateOf("0") }
    var trigliceridos by remember { mutableStateOf("0") }
    var urea by remember { mutableStateOf("0") }
    var creatinina by remember { mutableStateOf("0") }
    var hemoglobina by remember { mutableStateOf("0") }

    // 4.2. Escuchamos el resultado de la pantalla de signos
    val backStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(backStackEntry?.savedStateHandle) {
        backStackEntry?.savedStateHandle?.let { handle ->
            // Leemos cada valor individualmente
            handle.get<String>("peso")?.let { peso = it }
            handle.get<String>("estatura")?.let { estatura = it }
            handle.get<String>("frecuenciaCardiaca")?.let { frecuenciaCardiaca = it }
            handle.get<String>("frecuenciaRespiratoria")?.let { frecuenciaRespiratoria = it }
            handle.get<String>("presionSistolica")?.let { presionSistolica = it }
            handle.get<String>("presionDiastolica")?.let { presionDiastolica = it }
            handle.get<String>("saturacionOxigeno")?.let { saturacionOxigeno = it }
            handle.get<String>("temperatura")?.let { temperatura = it }
            handle.get<String>("glucosa")?.let { glucosa = it }
            handle.get<String>("trigliceridos")?.let { trigliceridos = it }
            handle.get<String>("urea")?.let { urea = it }
            handle.get<String>("creatinina")?.let { creatinina = it }
            handle.get<String>("hemoglobina")?.let { hemoglobina = it }
            
            // Limpiamos los valores para que no queden pendientes
            handle.remove<String>("peso")
            handle.remove<String>("estatura")
            handle.remove<String>("frecuenciaCardiaca")
            handle.remove<String>("frecuenciaRespiratoria")
            handle.remove<String>("presionSistolica")
            handle.remove<String>("presionDiastolica")
            handle.remove<String>("saturacionOxigeno")
            handle.remove<String>("temperatura")
            handle.remove<String>("glucosa")
            handle.remove<String>("trigliceridos")
            handle.remove<String>("urea")
            handle.remove<String>("creatinina")
            handle.remove<String>("hemoglobina")
        }
    }

    // 4.3. Layout con fondo, TopAppBar y FAB
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFB2EBF2), Color.White),
                    startY = 0f, endY = 800f
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ─────────────────────────────────────────────────────────
            // TopBar
            // ─────────────────────────────────────────────────────────
            TopAppBar(
                title = { Text("Enfermería", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0097A7))
            )

            // ─────────────────────────────────────────────────────────
            // Contenido scrollable
            // ─────────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp)
                    .weight(1f)
            ) {
                // 4.3.1. Box de IMC expandible
                val imc = try {
                    val m = estatura.toFloat() / 100f
                    peso.toFloat() / (m * m)
                } catch (_: Exception) { 0f }
                IMCBoxExpandable(imc = imc)

                Spacer(modifier = Modifier.height(16.dp))

                // 4.3.2. Lista de filas con cada vital
                VitalRow(Icons.Default.MonitorWeight,      "Peso",                peso)
                VitalRow(Icons.Default.Straighten,         "Estatura",            estatura)
                VitalRow(Icons.Default.Favorite,           "F. Cardíaca",         frecuenciaCardiaca)
                VitalRow(Icons.Default.Air,                "F. Respiratoria",     frecuenciaRespiratoria)
                VitalRow(Icons.Default.Add,                "P. Sistólica",        presionSistolica)
                VitalRow(Icons.Default.Remove,             "P. Diastólica",       presionDiastolica)
                VitalRow(Icons.Default.AirlineSeatFlat,    "Sat O₂",              saturacionOxigeno)
                VitalRow(Icons.Default.DeviceThermostat,   "Temperatura",         temperatura)
                VitalRow(Icons.Default.Bloodtype,          "Glucosa",             glucosa)
                VitalRow(Icons.Default.LocalFireDepartment,"Triglicéridos",       trigliceridos)
                VitalRow(Icons.Default.Healing,            "Urea",                urea)
                VitalRow(Icons.Default.MedicalServices,             "Creatinina",          creatinina)
                VitalRow(Icons.Default.Bloodtype,          "Hemoglobina",         hemoglobina)

                // ⠀⠀⠀Espacio para que no lo cubra el FAB
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // ─────────────────────────────────────────────────────────
        // 4.4. FloatingActionButton para abrir pantalla de signos
        // ─────────────────────────────────────────────────────────
        FloatingActionButton(
            onClick = { navController.navigate("signos_vitales") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .navigationBarsPadding(),
            containerColor = Color(0xFF2196F3)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar datos")
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// 5. Fila que muestra icono, etiqueta y valor
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun VitalRow(icon: ImageVector, label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = label, tint = Color(0xFF0097A7))
            Spacer(modifier = Modifier.width(12.dp))
            Text(label, modifier = Modifier.weight(1f))
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// 6. Composable expandible del IMC (igual que antes)
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun IMCBoxExpandable(imc: Float) {
    var expandido by remember { mutableStateOf(false) }
    val altura by animateDpAsState(if (expandido) 200.dp else 80.dp)

    val rangos = listOf(
        IMCRango("Bajo Peso", Color.Red, 0f, 18.4f),
        IMCRango("Normal", Color.Green, 18.5f, 24.9f),
        IMCRango("Sobrepeso", Color(0xFFFFA726), 25f, 29.9f),
        IMCRango("Obesidad", Color.Red, 30f, 60f)
    )
    val pos = (imc / 60f).coerceIn(0f, 1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(altura)
            .clickable { expandido = !expandido },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("IMC", color = Color.White, fontWeight = FontWeight.Bold)
                Icon(
                    imageVector = if (expandido) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

            if (expandido) {
                Spacer(Modifier.height(8.dp))
                Text(imc.toString(), fontSize = 28.sp, color = Color.Red)
                Spacer(Modifier.height(8.dp))
            }

            Box(Modifier.fillMaxWidth().height(20.dp)) {
                Row(Modifier.fillMaxSize()) {
                    rangos.forEach {
                        val w = (it.max - it.min) / 60f
                        Box(Modifier.weight(w).fillMaxHeight().background(it.color))
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .drawWithContent {
                            drawContent()
                            drawCircle(
                                color = Color.Red,
                                radius = 10f,
                                center = Offset(size.width * pos, size.height / 2)
                            )
                        }
                )
            }

            if (expandido) {
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    rangos.forEach {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(it.nombre, fontSize = 12.sp)
                            Text("${it.min}–${it.max}", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
