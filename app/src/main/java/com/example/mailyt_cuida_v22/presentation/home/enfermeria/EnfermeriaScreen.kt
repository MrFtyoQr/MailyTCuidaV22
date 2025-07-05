package com.example.mailyt_cuida_v22.presentation.home.enfermeria

// ─────────────────────────────────────────────────────────────────────────────
// 1. Imports necesarios
// ─────────────────────────────────────────────────────────────────────────────
import android.content.Context
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.draw.drawWithContent
import com.example.mailyt_cuida_v22.data.database.AppDatabase
import com.example.mailyt_cuida_v22.data.entity.SignosVitalesEntity
import com.example.mailyt_cuida_v22.data.repository.SignosVitalesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collectLatest

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
    val context = LocalContext.current
    
    // 4.1. Estados que muestran los valores actuales
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
    
    // 4.2. Inicializar base de datos y repositorio
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { SignosVitalesRepository(database.signosVitalesDao()) }
    
    // 4.3. Cargar datos más recientes desde Room
    LaunchedEffect(Unit) {
        Log.d("EnfermeriaScreen", "Iniciando carga de datos...")
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            Log.d("EnfermeriaScreen", "Usuario encontrado: ${currentUser.uid}")
            try {
                // Cargar el valor más reciente de cada campo individual
                val latestPeso = repository.getLatestPeso(currentUser.uid)
                val latestEstatura = repository.getLatestEstatura(currentUser.uid)
                val latestFrecuenciaCardiaca = repository.getLatestFrecuenciaCardiaca(currentUser.uid)
                val latestFrecuenciaRespiratoria = repository.getLatestFrecuenciaRespiratoria(currentUser.uid)
                val latestPresionSistolica = repository.getLatestPresionSistolica(currentUser.uid)
                val latestPresionDiastolica = repository.getLatestPresionDiastolica(currentUser.uid)
                val latestSaturacionOxigeno = repository.getLatestSaturacionOxigeno(currentUser.uid)
                val latestTemperatura = repository.getLatestTemperatura(currentUser.uid)
                val latestGlucosa = repository.getLatestGlucosa(currentUser.uid)
                val latestTrigliceridos = repository.getLatestTrigliceridos(currentUser.uid)
                val latestUrea = repository.getLatestUrea(currentUser.uid)
                val latestCreatinina = repository.getLatestCreatinina(currentUser.uid)
                val latestHemoglobina = repository.getLatestHemoglobina(currentUser.uid)
                
                // Asignar valores solo si existen
                latestPeso?.let { peso = it }
                latestEstatura?.let { estatura = it }
                latestFrecuenciaCardiaca?.let { frecuenciaCardiaca = it }
                latestFrecuenciaRespiratoria?.let { frecuenciaRespiratoria = it }
                latestPresionSistolica?.let { presionSistolica = it }
                latestPresionDiastolica?.let { presionDiastolica = it }
                latestSaturacionOxigeno?.let { saturacionOxigeno = it }
                latestTemperatura?.let { temperatura = it }
                latestGlucosa?.let { glucosa = it }
                latestTrigliceridos?.let { trigliceridos = it }
                latestUrea?.let { urea = it }
                latestCreatinina?.let { creatinina = it }
                latestHemoglobina?.let { hemoglobina = it }
                
                Log.d("EnfermeriaScreen", "Datos cargados individualmente - Peso: '$peso', Estatura: '$estatura', FCardiaca: '$frecuenciaCardiaca'")
            } catch (e: Exception) {
                Log.e("EnfermeriaScreen", "Error cargando datos: ${e.message}", e)
            }
        } else {
            Log.w("EnfermeriaScreen", "No hay usuario autenticado")
        }
    }

    // 4.4. Layout con fondo, TopAppBar y FAB
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
                // 4.5.1. Box de IMC expandible
                val imc = try {
                    if (peso.isNotBlank() && estatura.isNotBlank()) {
                        val pesoFloat = peso.toFloat()
                        val estaturaFloat = estatura.toFloat()
                        val alturaMetros = estaturaFloat / 100f
                        val imcValue = pesoFloat / (alturaMetros * alturaMetros)
                        Log.d("EnfermeriaScreen", "Calculando IMC - Peso: $peso, Estatura: $estatura, IMC: $imcValue")
                        imcValue
                    } else {
                        Log.d("EnfermeriaScreen", "No hay datos suficientes para calcular IMC")
                        0f
                    }
                } catch (_: Exception) { 
                    Log.e("EnfermeriaScreen", "Error calculando IMC")
                    0f 
                }
                IMCBoxExpandable(imc = imc)

                Spacer(modifier = Modifier.height(16.dp))

                // 4.5.2. Lista de filas con cada vital (ahora clickeables)
                VitalRow(
                    icon = Icons.Default.MonitorWeight,
                    label = "Peso",
                    value = peso,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a peso_grafica")
                        navController.navigate("peso_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.Straighten,
                    label = "Estatura",
                    value = estatura,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a estatura_grafica")
                        navController.navigate("estatura_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.Favorite,
                    label = "F. Cardíaca",
                    value = frecuenciaCardiaca,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a frecuencia_cardiaca_grafica")
                        navController.navigate("frecuencia_cardiaca_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.Air,
                    label = "F. Respiratoria",
                    value = frecuenciaRespiratoria,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a frecuencia_respiratoria_grafica")
                        navController.navigate("frecuencia_respiratoria_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.Add,
                    label = "P. Sistólica",
                    value = presionSistolica,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a presion_sistolica_grafica")
                        navController.navigate("presion_sistolica_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.Remove,
                    label = "P. Diastólica",
                    value = presionDiastolica,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a presion_diastolica_grafica")
                        navController.navigate("presion_diastolica_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.AirlineSeatFlat,
                    label = "Sat O₂",
                    value = saturacionOxigeno,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a saturacion_oxigeno_grafica")
                        navController.navigate("saturacion_oxigeno_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.DeviceThermostat,
                    label = "Temperatura",
                    value = temperatura,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a temperatura_grafica")
                        navController.navigate("temperatura_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.Bloodtype,
                    label = "Glucosa",
                    value = glucosa,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a glucosa_grafica")
                        navController.navigate("glucosa_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.LocalFireDepartment,
                    label = "Triglicéridos",
                    value = trigliceridos,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a trigliceridos_grafica")
                        navController.navigate("trigliceridos_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.Healing,
                    label = "Urea",
                    value = urea,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a urea_grafica")
                        navController.navigate("urea_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.MedicalServices,
                    label = "Creatinina",
                    value = creatinina,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a creatinina_grafica")
                        navController.navigate("creatinina_grafica") 
                    }
                )
                VitalRow(
                    icon = Icons.Default.Bloodtype,
                    label = "Hemoglobina",
                    value = hemoglobina,
                    onClick = { 
                        Log.d("EnfermeriaScreen", "Navegando a hemoglobina_grafica")
                        navController.navigate("hemoglobina_grafica") 
                    }
                )

                // ⠀⠀⠀Espacio para que no lo cubra el FAB
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // ─────────────────────────────────────────────────────────
        // 4.6. FloatingActionButton para abrir pantalla de signos
        // ─────────────────────────────────────────────────────────
        FloatingActionButton(
            onClick = { 
                Log.d("EnfermeriaScreen", "Navegando a signos_vitales")
                navController.navigate("signos_vitales") 
            },
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
// 5. Fila que muestra icono, etiqueta y valor (ahora clickeable)
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun VitalRow(icon: ImageVector, label: String, value: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .clickable { onClick() },
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
            Text(
                text = if (value.isNotBlank()) value else "No registrado",
                fontWeight = FontWeight.Bold,
                color = if (value.isNotBlank()) Color.Black else Color.Gray
            )
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
    val tieneDatos = imc > 0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(altura)
            .clickable { if (tieneDatos) expandido = !expandido },
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
                if (tieneDatos) {
                    Icon(
                        imageVector = if (expandido) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }

            if (tieneDatos) {
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
            } else {
                Spacer(Modifier.height(8.dp))
                Text(
                    "Ingresa peso y estatura para calcular IMC",
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}
