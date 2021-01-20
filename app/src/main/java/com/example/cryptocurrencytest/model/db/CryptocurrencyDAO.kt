package com.example.cryptocurrencytest.model.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.cryptocurrencytest.model.entity.CryptocurrencyDataDB
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CryptocurrencyDAO {

    @Query("SELECT * FROM cryptocurrency")
    fun getDataList(): Observable<List<CryptocurrencyDataDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(cryptocurrencyData: CryptocurrencyDataDB): Completable

    @Update
    fun updateData(list: List<CryptocurrencyDataDB>)

    @Query("DELETE FROM cryptocurrency")
    fun delete()
}