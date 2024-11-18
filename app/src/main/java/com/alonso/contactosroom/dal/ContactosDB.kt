package com.alonso.contactosroom.dal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alonso.contactosroom.ent.Contacto

@Database(entities = [Contacto::class], version = 1, exportSchema = true)
abstract class ContactosDB: RoomDatabase() {
    abstract fun contactosDao(): ContactosDAO
}