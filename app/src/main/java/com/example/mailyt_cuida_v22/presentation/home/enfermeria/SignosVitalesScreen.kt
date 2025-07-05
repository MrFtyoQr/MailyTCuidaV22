package com.example.mailyt_cuida_v22.presentation.home.enfermeria

// ─────────────────────────────────────────────────────────────────────────────
// Imports necesarios
// ─────────────────────────────────────────────────────────────────────────────
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mailyt_cuida_v22.data.database.AppDatabase
import com.example.mailyt_cuida_v22.data.entity.SignosVitalesEntity
import com.example.mailyt_cuida_v22.data.repository.SignosVitalesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.util.Date

// ─────────────────────────────────────────────────────────────────────────────
// Pantalla para ingresar todos los signos vitales
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignosVitalesScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    Log.d("SignosVitalesScreen", "Iniciando pantalla de signos vitales")
    
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
    
    // 2. Estado de carga
    var isLoading by remember { mutableStateOf(false) }
    
    // 3. Inicializar base de datos y repositorio
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { SignosVitalesRepository(database.signosVitalesDao()) }
    
    // 4. Función para validar si hay al menos un campo lleno
    fun hasAtLeastOneField(): Boolean {
        val hasField = peso.isNotBlank() || estatura.isNotBlank() || frecuenciaCardiaca.isNotBlank() ||
               frecuenciaRespiratoria.isNotBlank() || presionSistolica.isNotBlank() || 
               presionDiastolica.isNotBlank() || saturacionOxigeno.isNotBlank() || 
               temperatura.isNotBlank() || glucosa.isNotBlank() || trigliceridos.isNotBlank() ||
               urea.isNotBlank() || creatinina.isNotBlank() || hemoglobina.isNotBlank()
        Log.d("SignosVitalesScreen", "Validación de campos: $hasField")
        return hasField
    }
    
    // 5. Función para guardar datos
    fun saveData() {
        Log.d("SignosVitalesScreen", "Iniciando guardado de datos...")
        Log.d("SignosVitalesScreen", "Valores actuales - Peso: '$peso', Estatura: '$estatura', FCardiaca: '$frecuenciaCardiaca'")
        
        if (!hasAtLeastOneField()) {
            Log.w("SignosVitalesScreen", "No hay campos llenos para guardar")
            Toast.makeText(context, "Debes llenar al menos un campo", Toast.LENGTH_SHORT).show()
            return
        }
        
        scope.launch {
            isLoading = true
            try {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser != null) {
                    Log.d("SignosVitalesScreen", "Usuario autenticado: ${currentUser.uid}")
                    
                    val signosVitales = SignosVitalesEntity(
                        userId = currentUser.uid,
                        fecha = Date(),
                        peso = peso.takeIf { it.isNotBlank() },
                        estatura = estatura.takeIf { it.isNotBlank() },
                        frecuenciaCardiaca = frecuenciaCardiaca.takeIf { it.isNotBlank() },
                        frecuenciaRespiratoria = frecuenciaRespiratoria.takeIf { it.isNotBlank() },
                        presionSistolica = presionSistolica.takeIf { it.isNotBlank() },
                        presionDiastolica = presionDiastolica.takeIf { it.isNotBlank() },
                        saturacionOxigeno = saturacionOxigeno.takeIf { it.isNotBlank() },
                        temperatura = temperatura.takeIf { it.isNotBlank() },
                        glucosa = glucosa.takeIf { it.isNotBlank() },
                        trigliceridos = trigliceridos.takeIf { it.isNotBlank() },
                        urea = urea.takeIf { it.isNotBlank() },
                        creatinina = creatinina.takeIf { it.isNotBlank() },
                        hemoglobina = hemoglobina.takeIf { it.isNotBlank() }
                    )
                    
                    Log.d("SignosVitalesScreen", "Entidad creada: $signosVitales")
                    
                    val id = repository.insertSignosVitales(signosVitales)
                    Log.d("SignosVitalesScreen", "Datos guardados con ID: $id")
                    
                    Toast.makeText(context, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                } else {
                    Log.e("SignosVitalesScreen", "Usuario no autenticado")
                    Toast.makeText(context, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("SignosVitalesScreen", "Error al guardar datos: ${e.message}", e)
                Toast.makeText(context, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

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
            text = { Text(if (isLoading) "Guardando..." else "Guardar") },
            onClick = { if (!isLoading && hasAtLeastOneField()) saveData() },
            icon = { /* opcional: poner un ícono */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .navigationBarsPadding(),
            containerColor = if (hasAtLeastOneField()) Color(0xFFE1BEE7) else Color.Gray
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
