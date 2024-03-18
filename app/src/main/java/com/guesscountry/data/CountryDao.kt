package com.guesscountry.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCountry(country: CountryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("SELECT COUNT(*) FROM countries")
    suspend fun getCountOfCountries(): Int

    @Query("SELECT * FROM countries WHERE name LIKE '%' || :searchTerm || '%'")
    suspend fun searchByName(searchTerm: String): List<CountryEntity>

    @Query("SELECT * FROM countries WHERE code LIKE '%' || :searchTerm || '%'")
    suspend fun searchByCode(searchTerm: String): List<CountryEntity>

    @Query("SELECT * FROM countries WHERE id = :searchId")
    suspend fun searchById(searchId: Long): List<CountryEntity>

    @Query("SELECT * FROM countries")
    suspend fun getAllCountries(): List<CountryEntity>

    @Query("SELECT * FROM countries WHERE id NOT IN (:excludedIds) ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCountry(excludedIds: List<Int>): CountryEntity
}