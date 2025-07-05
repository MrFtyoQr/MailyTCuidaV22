package com.example.mailyt_cuida_v22.data.repository

import com.example.mailyt_cuida_v22.data.dao.SignosVitalesDao
import com.example.mailyt_cuida_v22.data.entity.SignosVitalesEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

class SignosVitalesRepository(private val signosVitalesDao: SignosVitalesDao) {
    
    fun getSignosVitalesByUser(userId: String): Flow<List<SignosVitalesEntity>> {
        return signosVitalesDao.getSignosVitalesByUser(userId)
    }
    
    suspend fun getLatestSignosVitales(userId: String): SignosVitalesEntity? {
        return signosVitalesDao.getLatestSignosVitales(userId)
    }
    
    suspend fun getAllSignosVitales(userId: String): List<SignosVitalesEntity> {
        return signosVitalesDao.getAllSignosVitales(userId)
    }
    
    // Métodos para obtener el valor más reciente de cada campo
    suspend fun getLatestPeso(userId: String): String? {
        return signosVitalesDao.getLatestPeso(userId)
    }
    
    suspend fun getLatestEstatura(userId: String): String? {
        return signosVitalesDao.getLatestEstatura(userId)
    }
    
    suspend fun getLatestFrecuenciaCardiaca(userId: String): String? {
        return signosVitalesDao.getLatestFrecuenciaCardiaca(userId)
    }
    
    suspend fun getLatestFrecuenciaRespiratoria(userId: String): String? {
        return signosVitalesDao.getLatestFrecuenciaRespiratoria(userId)
    }
    
    suspend fun getLatestPresionSistolica(userId: String): String? {
        return signosVitalesDao.getLatestPresionSistolica(userId)
    }
    
    suspend fun getLatestPresionDiastolica(userId: String): String? {
        return signosVitalesDao.getLatestPresionDiastolica(userId)
    }
    
    suspend fun getLatestSaturacionOxigeno(userId: String): String? {
        return signosVitalesDao.getLatestSaturacionOxigeno(userId)
    }
    
    suspend fun getLatestTemperatura(userId: String): String? {
        return signosVitalesDao.getLatestTemperatura(userId)
    }
    
    suspend fun getLatestGlucosa(userId: String): String? {
        return signosVitalesDao.getLatestGlucosa(userId)
    }
    
    suspend fun getLatestTrigliceridos(userId: String): String? {
        return signosVitalesDao.getLatestTrigliceridos(userId)
    }
    
    suspend fun getLatestUrea(userId: String): String? {
        return signosVitalesDao.getLatestUrea(userId)
    }
    
    suspend fun getLatestCreatinina(userId: String): String? {
        return signosVitalesDao.getLatestCreatinina(userId)
    }
    
    suspend fun getLatestHemoglobina(userId: String): String? {
        return signosVitalesDao.getLatestHemoglobina(userId)
    }
    
    fun getEstaturaHistory(userId: String): Flow<List<SignosVitalesEntity>> {
        return signosVitalesDao.getEstaturaHistory(userId)
    }
    
    fun getPesoHistory(userId: String): Flow<List<SignosVitalesEntity>> {
        return signosVitalesDao.getPesoHistory(userId)
    }
    
    fun getGlucosaHistory(userId: String): Flow<List<SignosVitalesEntity>> {
        return signosVitalesDao.getGlucosaHistory(userId)
    }
    
    suspend fun insertSignosVitales(signosVitales: SignosVitalesEntity): Long {
        return signosVitalesDao.insertSignosVitales(signosVitales)
    }
    
    suspend fun deleteSignosVitales(signosVitales: SignosVitalesEntity) {
        signosVitalesDao.deleteSignosVitales(signosVitales)
    }
    
    suspend fun deleteAllByUser(userId: String) {
        signosVitalesDao.deleteAllByUser(userId)
    }
} 