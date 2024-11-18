package com.alonso.contactosroom.ent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contacto (
    @PrimaryKey
    var tlf: String = "",
    var nombre: String = "",
    var fto: Int = 0
)
