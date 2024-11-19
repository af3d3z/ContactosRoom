package com.alonso.contactosroom

import android.graphics.Outline
import android.provider.CalendarContract
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alonso.contactosroom.ent.Contacto
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun AgregarContacto(navController: NavController){
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    var tlf by rememberSaveable { mutableStateOf("") }
    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            label = {Text("Nombre")},
            value = nombre,
            onValueChange = { nombre = it }
        )
        OutlinedTextField(
            label = {Text("Apellidos")},
            value = apellidos,
            onValueChange = { apellidos = it }
        )
        OutlinedTextField(
            label = {Text("Teléfono")},
            value = tlf,
            onValueChange = { tlf = it }
        )
        Text("Género:", fontSize = 18.sp, modifier = Modifier.padding(10.dp))
        Row  {
            Button(modifier = Modifier.padding(5.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF5E73A9),
                    contentColor = androidx.compose.ui.graphics.Color.White,
                    disabledContentColor = androidx.compose.ui.graphics.Color.Gray,
                    disabledContainerColor = androidx.compose.ui.graphics.Color.Gray
                ),
                onClick = {
                runBlocking {
                    MainActivity.coroutine.launch {
                        addContact(nombre = "$nombre $apellidos", tlf = tlf, gen = 0)
                    }
                }
            }) { Text("Masculino") }
            Button(modifier = Modifier.padding(5.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF5E73A9),
                    contentColor = androidx.compose.ui.graphics.Color.White,
                    disabledContentColor = androidx.compose.ui.graphics.Color.Gray,
                    disabledContainerColor = androidx.compose.ui.graphics.Color.Gray
                ),
                onClick = {
                runBlocking {
                    MainActivity.coroutine.launch {
                        addContact(nombre = "$nombre $apellidos", tlf = tlf, gen = 1)
                    }
                }
            }) { Text("Femenino") }
            Button(
                modifier = Modifier.padding(5.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF5E73A9),
                    contentColor = androidx.compose.ui.graphics.Color.White,
                    disabledContentColor = androidx.compose.ui.graphics.Color.Gray,
                    disabledContainerColor = androidx.compose.ui.graphics.Color.Gray
                ),
                onClick = {
                runBlocking {
                    MainActivity.coroutine.launch {
                        addContact(nombre = "$nombre $apellidos", tlf = tlf, gen = 2)
                    }
                }
            }) { Text("Otro") }
        }
        Spacer(modifier = Modifier.height(100.dp))
        TextButton(onClick = {navController.navigate("lista")}) {
            Text("Volver", fontSize = 20.sp, color = Color(0xff5E73A9))
        }
    }
}

suspend fun addContact(nombre: String, tlf: String, gen: Int){
    var contacto = Contacto(nombre = nombre, tlf = tlf, fto = gen)
    Log.d(":::CR", "${contacto.nombre} ${contacto.tlf} ${contacto.fto}")
    MainActivity.db.contactosDao().insert(contacto = contacto)
}