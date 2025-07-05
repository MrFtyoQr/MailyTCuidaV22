package com.example.mailyt_cuida_v22.presentation.home.enfermeria.graficas

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.mailyt_cuida_v22.data.database.AppDatabase
import com.example.mailyt_cuida_v22.data.repository.SignosVitalesRepository
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalGraficaScreen(
    navController: NavController,
    vitalType: String,
    vitalLabel: String,
    dataExtractor: (String?) -> Float?
) {
    val context = LocalContext.current
    var dataPoints by remember { mutableStateOf<List<Pair<Date, Float>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    
    Log.d("VitalGraficaScreen", "Iniciando gráfica para: $vitalType")
    
    // Inicializar base de datos y repositorio
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { SignosVitalesRepository(database.signosVitalesDao()) }
    
    // Cargar datos
    LaunchedEffect(Unit) {
        try {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                Log.d("VitalGraficaScreen", "Cargando datos para usuario: ${currentUser.uid}")
                val allData = repository.getAllSignosVitales(currentUser.uid)
                
                val points = allData.mapNotNull { entity ->
                    val value = dataExtractor(getVitalValue(entity, vitalType))
                    if (value != null) {
                        Pair(entity.fecha, value)
                    } else null
                }.sortedBy { it.first }
                
                Log.d("VitalGraficaScreen", "Datos cargados: ${points.size} puntos")
                dataPoints = points
            }
        } catch (e: Exception) {
            Log.e("VitalGraficaScreen", "Error cargando datos: ${e.message}", e)
        } finally {
            isLoading = false
        }
    }
    
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
            // TopAppBar
            TopAppBar(
                title = { Text("Gráfica de $vitalLabel", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0097A7))
            )
            
            // Contenido
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (dataPoints.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay datos disponibles para mostrar", fontSize = 18.sp)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Historial de $vitalLabel",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    // Gráfica
                    AndroidView(
                        factory = { context ->
                            LineChart(context).apply {
                                description.isEnabled = false
                                legend.isEnabled = true
                                setTouchEnabled(true)
                                setScaleEnabled(true)
                                setPinchZoom(true)
                                
                                xAxis.apply {
                                    position = XAxis.XAxisPosition.BOTTOM
                                    valueFormatter = object : ValueFormatter() {
                                        private val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
                                        override fun getFormattedValue(value: Float): String {
                                            val index = value.toInt()
                                            return if (index >= 0 && index < dataPoints.size) {
                                                dateFormat.format(dataPoints[index].first)
                                            } else ""
                                        }
                                    }
                                }
                                
                                axisLeft.apply {
                                    setDrawGridLines(true)
                                    axisMinimum = 0f
                                }
                                
                                axisRight.isEnabled = false
                            }
                        },
                        update = { chart ->
                            val entries = dataPoints.mapIndexed { index, (_, value) ->
                                Entry(index.toFloat(), value)
                            }
                            
                            val dataSet = LineDataSet(entries, vitalLabel).apply {
                                color = android.graphics.Color.parseColor("#0097A7")
                                setCircleColor(android.graphics.Color.parseColor("#0097A7"))
                                lineWidth = 2f
                                circleRadius = 4f
                                setDrawValues(true)
                            }
                            
                            chart.data = LineData(dataSet)
                            chart.invalidate()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(vertical = 16.dp)
                    )
                    
                    // Lista de datos
                    Text(
                        text = "Datos registrados:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    
                    dataPoints.forEach { (date, value) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(date),
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "$value",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0097A7)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

// Función auxiliar para obtener el valor del signo vital según el tipo
private fun getVitalValue(entity: com.example.mailyt_cuida_v22.data.entity.SignosVitalesEntity, vitalType: String): String? {
    return when (vitalType) {
        "peso" -> entity.peso
        "estatura" -> entity.estatura
        "frecuencia_cardiaca" -> entity.frecuenciaCardiaca
        "frecuencia_respiratoria" -> entity.frecuenciaRespiratoria
        "presion_sistolica" -> entity.presionSistolica
        "presion_diastolica" -> entity.presionDiastolica
        "saturacion_oxigeno" -> entity.saturacionOxigeno
        "temperatura" -> entity.temperatura
        "glucosa" -> entity.glucosa
        "trigliceridos" -> entity.trigliceridos
        "urea" -> entity.urea
        "creatinina" -> entity.creatinina
        "hemoglobina" -> entity.hemoglobina
        else -> null
    }
} 