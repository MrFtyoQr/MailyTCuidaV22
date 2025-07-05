package com.example.mailyt_cuida_v22.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "signos_vitales")
data class SignosVitalesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String, // ID del usuario de Firebase
    val fecha: Date,
    val peso: String?,
    val estatura: String?,
    val frecuenciaCardiaca: String?,
    val frecuenciaRespiratoria: String?,
    val presionSistolica: String?,
    val presionDiastolica: String?,
    val saturacionOxigeno: String?,
    val temperatura: String?,
    val glucosa: String?,
    val trigliceridos: String?,
    val urea: String?,
    val creatinina: String?,
    val hemoglobina: String?
) 