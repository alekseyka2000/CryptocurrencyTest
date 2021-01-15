package com.example.cryptocurrencytest.model.db

import androidx.room.*
import com.example.cryptocurrencytest.model.entity.CryptocurrencyDataDB

@Dao
interface CryptocurrencyDAO {

    @Query("SELECT * FROM cryptocurrency")
    fun getDataList(): List<CryptocurrencyDataDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(list: List<CryptocurrencyDataDB>)

    @Update
    fun updateData(list: List<CryptocurrencyDataDB>)

    @Query("DELETE FROM cryptocurrency")
    fun delete()
}