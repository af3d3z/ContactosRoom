package com.alonso.contactosroom.dal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alonso.contactosroom.ent.Contacto

@Dao
interface ContactosDAO {
    @Query("SELECT * FROM contacts")
    suspend fun getAll(): List<Contacto>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacto: Contacto)
    @Update
    suspend fun update(contacto: Contacto)
}