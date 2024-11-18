package com.alonso.contactosroom

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.alonso.contactosroom.dal.ContactosDB
import com.alonso.contactosroom.ent.Contacto
import com.alonso.contactosroom.ui.theme.ContactosRoomTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var db: ContactosDB
        lateinit var coroutine: CoroutineScope
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            db = Room.databaseBuilder(
                applicationContext,
                ContactosDB::class.java,
                "contactos-db"
            ).build()
            coroutine = rememberCoroutineScope()
            val listaContactos by remember<MutableState<List<Contacto>>> { mutableStateOf(listOf<Contacto>()) }
            LaunchedEffect(Unit) {
                coroutine.launch {
                    listaContactos = db.contactosDao().getAll()
                    Log.d(":::CR", "${listaContactos.size}")
                }
            }
            val navController = rememberNavController()
            NavHost(
                navController,
                startDestination = "lista"
            ){
                composable("lista"){
                    ContactosRoomTheme {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            floatingActionButton = {
                                FloatingActionButton(
                                    onClick = { navController.navigate("agregarContactos")},
                                    modifier = Modifier
                                        .padding(16.dp) // Padding opcional para separarlo de los bordes
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                                }
                            },
                            // Posicionamos el FAB en la parte inferior derecha
                            floatingActionButtonPosition = FabPosition.End, // Alinea el FAB a la derecha
                            ) { innerPadding ->
                            Text("Contactos", fontSize = 36.sp, modifier = Modifier.padding(innerPadding))
                            Log.d(":::CR", "Size: ${listaContactos.size}")
                            ItemList(listaContactos, innerPadding)
                        }

                    }
                }
                composable("agregarContactos") {
                    Scaffold { innerPadding ->
                        Text("Añadir contacto", fontSize = 36.sp, modifier = Modifier.padding(innerPadding))
                        AgregarContacto()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContactosRoomTheme {
        Greeting("Android")
    }
}