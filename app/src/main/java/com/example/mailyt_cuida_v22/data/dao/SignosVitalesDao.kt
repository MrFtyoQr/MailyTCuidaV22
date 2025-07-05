package com.example.mailyt_cuida_v22.data.dao

import androidx.room.*
import com.example.mailyt_cuida_v22.data.entity.SignosVitalesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SignosVitalesDao {
    
    @Insert
    suspend fun insertSignosVitales(signosVitales: SignosVitalesEntity): Long
    
    @Query("SELECT * FROM signos_vitales WHERE userId = :userId ORDER BY fecha DESC")
    fun getSignosVitalesByUser(userId: String): Flow<List<SignosVitalesEntity>>
    
    @Query("SELECT * FROM signos_vitales WHERE userId = :userId ORDER BY fecha ASC")
    suspend fun getAllSignosVitales(userId: String): List<SignosVitalesEntity>
    
    @Query("SELECT * FROM signos_vitales WHERE userId = :userId ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestSignosVitales(userId: String): SignosVitalesEntity?
    
    @Query("SELECT peso FROM signos_vitales WHERE userId = :userId AND peso IS NOT NULL AND peso != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestPeso(userId: String): String?
    
    @Query("SELECT estatura FROM signos_vitales WHERE userId = :userId AND estatura IS NOT NULL AND estatura != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestEstatura(userId: String): String?
    
    @Query("SELECT frecuenciaCardiaca FROM signos_vitales WHERE userId = :userId AND frecuenciaCardiaca IS NOT NULL AND frecuenciaCardiaca != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestFrecuenciaCardiaca(userId: String): String?
    
    @Query("SELECT frecuenciaRespiratoria FROM signos_vitales WHERE userId = :userId AND frecuenciaRespiratoria IS NOT NULL AND frecuenciaRespiratoria != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestFrecuenciaRespiratoria(userId: String): String?
    
    @Query("SELECT presionSistolica FROM signos_vitales WHERE userId = :userId AND presionSistolica IS NOT NULL AND presionSistolica != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestPresionSistolica(userId: String): String?
    
    @Query("SELECT presionDiastolica FROM signos_vitales WHERE userId = :userId AND presionDiastolica IS NOT NULL AND presionDiastolica != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestPresionDiastolica(userId: String): String?
    
    @Query("SELECT saturacionOxigeno FROM signos_vitales WHERE userId = :userId AND saturacionOxigeno IS NOT NULL AND saturacionOxigeno != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestSaturacionOxigeno(userId: String): String?
    
    @Query("SELECT temperatura FROM signos_vitales WHERE userId = :userId AND temperatura IS NOT NULL AND temperatura != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestTemperatura(userId: String): String?
    
    @Query("SELECT glucosa FROM signos_vitales WHERE userId = :userId AND glucosa IS NOT NULL AND glucosa != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestGlucosa(userId: String): String?
    
    @Query("SELECT trigliceridos FROM signos_vitales WHERE userId = :userId AND trigliceridos IS NOT NULL AND trigliceridos != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestTrigliceridos(userId: String): String?
    
    @Query("SELECT urea FROM signos_vitales WHERE userId = :userId AND urea IS NOT NULL AND urea != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestUrea(userId: String): String?
    
    @Query("SELECT creatinina FROM signos_vitales WHERE userId = :userId AND creatinina IS NOT NULL AND creatinina != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestCreatinina(userId: String): String?
    
    @Query("SELECT hemoglobina FROM signos_vitales WHERE userId = :userId AND hemoglobina IS NOT NULL AND hemoglobina != '' ORDER BY fecha DESC LIMIT 1")
    suspend fun getLatestHemoglobina(userId: String): String?
    
    @Query("SELECT * FROM signos_vitales WHERE userId = :userId AND estatura IS NOT NULL ORDER BY fecha DESC")
    fun getEstaturaHistory(userId: String): Flow<List<SignosVitalesEntity>>
    
    @Query("SELECT * FROM signos_vitales WHERE userId = :userId AND peso IS NOT NULL ORDER BY fecha DESC")
    fun getPesoHistory(userId: String): Flow<List<SignosVitalesEntity>>
    
    @Query("SELECT * FROM signos_vitales WHERE userId = :userId AND glucosa IS NOT NULL ORDER BY fecha DESC")
    fun getGlucosaHistory(userId: String): Flow<List<SignosVitalesEntity>>
    
    @Delete
    suspend fun deleteSignosVitales(signosVitales: SignosVitalesEntity)
    
    @Query("DELETE FROM signos_vitales WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
} 