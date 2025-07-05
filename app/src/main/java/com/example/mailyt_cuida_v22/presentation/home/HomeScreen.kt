package com.example.mailyt_cuida_v22.presentation.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mailyt_cuida_v22.R
import com.example.mailyt_cuida_v22.ui.theme.Blue
import com.example.mailyt_cuida_v22.ui.theme.Gray
import com.example.mailyt_cuida_v22.ui.theme.LightBlue
import com.example.mailyt_cuida_v22.ui.theme.White
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast


@Composable
fun HomeScreen(navController: NavController) {
    // Obtener usuario actual
    val user = FirebaseAuth.getInstance().currentUser
    val nombreUsuario = user?.displayName ?: user?.email?.substringBefore("@") ?: "Usuario"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(LightBlue, White), startY = 0f, endY = 600f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PantallaInicio(navController, nombreUsuario)
    }
}

@Composable
fun PantallaInicio(
    navController: NavController,
    nombreUsuario: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra de color para el status bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(Color(0xFF2596be))
        )
        Column {
            BarraSuperior(navController, nombreUsuario)
            ContenidoPrincipal(navController)
            Especialidades()
        }
    }
}

@Composable
fun BarraSuperior(navController: NavController, nombreUsuario: String) {
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2596be))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Parte izquierda: logo + textos
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.logomaily),
                contentDescription = "Logo",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Hola, $nombreUsuario",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = "Registra tu estado de salud hoy.",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
        // Parte derecha: iconos carrito y menú
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate("shop") }) {
                Icon(
                    painter = painterResource(id = R.drawable.iconocarrito),
                    contentDescription = "Carrito",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.iconomenu),
                        contentDescription = "Menú",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Perfil") },
                        onClick = { /* Navegar a perfil */ menuExpanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Cerrar sesión") },
                        onClick = {
                            menuExpanded = false
                            FirebaseAuth.getInstance().signOut()
                            Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                            navController.navigate("initial") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BotonesPrincipales(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BotonPrincipal(
                icon = Icons.Default.LocalHospital,
                texto = "Enfermería",
                onClick = { navController.navigate("enfermeria") }
            )
            BotonPrincipal(
                icon = Icons.Default.Receipt,
                texto = "Recetas",
                onClick = { navController.navigate("recetas") }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BotonPrincipal(
                icon = Icons.Default.DateRange,
                texto = "Calendario",
                onClick = { navController.navigate("calendario") }
            )
            BotonPrincipal(
                icon = Icons.Default.Biotech,
                texto = "Laboratorio",
                onClick = { navController.navigate("laboratorio") }
            )
        }
    }
}

@Composable
fun BotonPrincipal(icon: ImageVector, texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier.size(width = 152.dp, height = 38.dp)
    ) {
        Icon(icon, contentDescription = texto, tint = Color.White)
        Spacer(Modifier.width(8.dp))
        Text(texto, color = Color.White)
    }
}

@Composable
fun ContenidoPrincipal(navController: NavController) {
    Column {
        BotonesPrincipales(navController)
        // Puedes agregar más contenido aquí si lo necesitas
    }
}

@Composable
fun Especialidades() {
    val context = LocalContext.current
    // Números de WhatsApp configurables para cada especialidad
    val numeros = mapOf(
        "Nutrición" to "527442780069",
        "Psicología" to "527442780069",
        "Odontología" to "527442780069",
        "Fisioterapia" to "527442780069",
        "Dermacosmética" to "527442780069",
        "Especialidades" to "527442780069"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "Descubre a nuestros especialistas",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Filas de botones
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BotonEspecialidad("Nutrición", numeros["Nutrición"]!!, context, Modifier.weight(1f))
            BotonEspecialidad("Psicología", numeros["Psicología"]!!, context, Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BotonEspecialidad("Odontología", numeros["Odontología"]!!, context, Modifier.weight(1f))
            BotonEspecialidad("Fisioterapia", numeros["Fisioterapia"]!!, context, Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BotonEspecialidad("Dermacosmética", numeros["Dermacosmética"]!!, context, Modifier.weight(1f))
            BotonEspecialidad("Especialidades", numeros["Especialidades"]!!, context, Modifier.weight(1f))
        }
    }
}

@Composable
fun BotonEspecialidad(texto: String, numeroWhatsApp: String, context: android.content.Context, modifier: Modifier = Modifier) {
    val mensaje = "¡Hola! Si ves esto es que los botones funcionan $texto."
    Button(
        onClick = {
            // Abrir WhatsApp con el número correspondiente
            val url = "https://wa.me/$numeroWhatsApp?text=${Uri.encode(mensaje)}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier.size(width = 155.dp, height = 38.dp)
    ) {
        Text(text = texto, color = Color.Black, fontSize = 14.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPantallaInicio() {
    val navController = rememberNavController()
    HomeScreen(navController)
}