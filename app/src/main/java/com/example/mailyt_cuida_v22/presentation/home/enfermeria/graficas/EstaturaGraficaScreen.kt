package com.example.mailyt_cuida_v22.presentation.home.enfermeria.graficas

import android.content.Context
import android.graphics.Color
import android.widget.Toast
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
import androidx.compose.ui.graphics.toArgb
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
fun EstaturaGraficaScreen(navController: NavController) {
    val context = LocalContext.current
    var estaturaData by remember { mutableStateOf<List<Pair<Date, Float>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    
    // Inicializar base de datos y repositorio
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { SignosVitalesRepository(database.signosVitalesDao()) }
    
    // Cargar datos de estatura
    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            try {
                repository.getEstaturaHistory(currentUser.uid).collectLatest { entities ->
                    val data = entities.mapNotNull { entity ->
                        entity.estatura?.toFloatOrNull()?.let { estatura ->
                            entity.fecha to estatura
                        }
                    }.sortedBy { it.first }
                    estaturaData = data
                    isLoading = false
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar datos: ${e.message}", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        } else {
            Toast.makeText(context, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(androidx.compose.ui.graphics.Color(0xFF192C59), androidx.compose.ui.graphics.Color.White),
                    startY = 0f, endY = 600f
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // TopAppBar con flecha para regresar
            TopAppBar(
                title = { Text("Gráfica de Estatura", color = androidx.compose.ui.graphics.Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = androidx.compose.ui.graphics.Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = androidx.compose.ui.graphics.Color(0xFF0097A7))
            )

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (estaturaData.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay datos de estatura registrados",
                            fontSize = 16.sp,
                            color = androidx.compose.ui.graphics.Color.Gray
                        )
                    }
                } else {
                    // Información resumida
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Resumen de Estatura",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Registros totales: ${estaturaData.size}")
                            Text("Última medición: ${estaturaData.lastOrNull()?.second ?: 0} cm")
                            if (estaturaData.size > 1) {
                                val first = estaturaData.first().second
                                val last = estaturaData.last().second
                                val diferencia = last - first
                                Text("Cambio total: ${if (diferencia >= 0) "+" else ""}${String.format("%.1f", diferencia)} cm")
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Gráfica
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
                    ) {
                        AndroidView(
                            modifier = Modifier.fillMaxSize(),
                            factory = { context ->
                                LineChart(context).apply {
                                    description.isEnabled = false
                                    legend.isEnabled = true
                                    setTouchEnabled(true)
                                    setScaleEnabled(true)
                                    setPinchZoom(true)
                                    
                                    xAxis.apply {
                                        position = XAxis.XAxisPosition.BOTTOM
                                        setDrawGridLines(false)
                                        valueFormatter = object : ValueFormatter() {
                                            private val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
                                            override fun getFormattedValue(value: Float): String {
                                                val index = value.toInt()
                                                return if (index < estaturaData.size) {
                                                    dateFormat.format(estaturaData[index].first)
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
                                val entries = estaturaData.mapIndexed { index, (_, estatura) ->
                                    Entry(index.toFloat(), estatura)
                                }
                                
                                val dataSet = LineDataSet(entries, "Estatura (cm)").apply {
                                    color = androidx.compose.ui.graphics.Color(0xFF2196F3).toArgb()
                                    setCircleColor(androidx.compose.ui.graphics.Color(0xFF2196F3).toArgb())
                                    lineWidth = 3f
                                    circleRadius = 5f
                                    setDrawValues(true)
                                    valueTextSize = 12f
                                }
                                
                                chart.data = LineData(dataSet)
                                chart.invalidate()
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Lista de registros
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Historial de Registros",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            estaturaData.reversed().forEach { (fecha, estatura) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(fecha),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${estatura} cm",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Espacio para la barra de navegación
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

