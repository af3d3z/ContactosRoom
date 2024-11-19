package com.alonso.contactosroom

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alonso.contactosroom.MainActivity.Companion.coroutine
import com.alonso.contactosroom.ent.Contacto
import kotlinx.coroutines.launch


@Composable
    fun ItemList(modifier: PaddingValues) {
    var itemContacto = remember { mutableStateListOf<Contacto>() }
        LaunchedEffect(Unit) {
            coroutine.launch {
                itemContacto.clear()
                itemContacto.addAll(MainActivity.db.contactosDao().getAll())
                Log.d(":::CR", "${itemContacto.size}")
            }
        }
        Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            if (itemContacto.isNotEmpty()) {
                LazyColumn (
                    modifier = Modifier.fillMaxSize().padding(modifier),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(itemContacto.size) { index ->
                        Contactos(contacto = itemContacto[index])
                    }
                }
            }else {
                Text("La lista de contactos está vacía.", textAlign = TextAlign.Center, fontSize = 18.sp)
            }
        }
    }

    /*
    * @Composable
    fun ItemList(itemContacto: List<Contacto>, modifier: Modifier) {
        LazyColumn (modifier.fillMaxWidth()) {	// produce una lista de desplazamiento vertical,
            /*items(itemContacto.size) { index ->
                Contactos(contacto = itemContacto[index])
            }}*/
            items(itemContacto.chunked(2)) { chunk ->
                Row(modifier = Modifier.fillMaxWidth()){
                    chunk.forEach{ contacto ->
                        Contactos(contacto)
                    }
                }

            }
          }
    }*/


    @Composable
    fun Contactos(contacto: Contacto) {
        Log.d(":::CR", "Contactos reached!")
        val ctx = LocalContext.current
        var foto = R.drawable.ic_launcher_foreground
        var showPhone by remember {
            mutableStateOf(false)
        }
        when (contacto.fto){
            0-> foto = R.drawable.cat_man
            1-> foto = R.drawable.cat_woman
            2-> foto = R.drawable.cat_nb
        }
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(foto),
                modifier = Modifier.size(82.dp).padding(8.dp),
                contentDescription = "Foto del contacto ${contacto.nombre}",
            )
            Column {
                Text(modifier = Modifier.fillMaxWidth(), text = contacto.nombre, fontSize = 23.sp, textAlign = TextAlign.Center)
                Button(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, end = 10.dp),
                    colors = ButtonColors(
                        containerColor = Color(0xFF5E73A9),
                        contentColor = androidx.compose.ui.graphics.Color.White,
                        disabledContentColor = androidx.compose.ui.graphics.Color.Gray,
                        disabledContainerColor = androidx.compose.ui.graphics.Color.Gray
                    ),
                    onClick = {makeCall(contacto.tlf, ctx)}) {
                    Icon(Icons.Default.Call, contentDescription = "Botón llamada", modifier = Modifier.padding(end = 5.dp))
                    Text("Llamar")
                }
            }
        }
    }

    private fun makeCall(phoneNumber: String, ctx: Context) {
        val phoneUri = Uri.parse("tel:$phoneNumber")
        val intent = Intent(Intent.ACTION_DIAL, phoneUri)

        try{
            ctx.startActivity(intent)
        }catch(s :SecurityException) {
            Toast.makeText(ctx, "An error ocurred", Toast.LENGTH_SHORT).show()
        }
    }
