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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alonso.contactosroom.ent.Contacto


@Composable
    fun ItemList(itemContacto: List<Contacto>, modifier: PaddingValues) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            //if (itemContacto.isNotEmpty()) {
                LazyVerticalGrid (
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth().padding(modifier),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(itemContacto.size) { index ->
                        Contactos(contacto = itemContacto[index])
                    }
                }
            /*}else {
                Text("La lista de contactos está vacía.", textAlign = TextAlign.Center, fontSize = 18.sp)
            }*/
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
        Card(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                AnimatedVisibility(visible = !showPhone) {
                    Image(
                        painter = painterResource(foto), // Load the image
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .weight(1f)
                            .align(Alignment.CenterHorizontally)
                            .clickable { showPhone = !showPhone }
                            .size(115.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(10.dp).weight(1f))
                    Text(modifier = Modifier.fillMaxWidth().weight(2f).align(Alignment.CenterHorizontally), text = contacto.nombre, textAlign = TextAlign.Center)
                }
                AnimatedVisibility(visible = showPhone ) {
                    ClickableText(
                        text = AnnotatedString(contacto.nombre),
                        onClick = { showPhone = !showPhone },
                        style = androidx.compose.ui.text.TextStyle(
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxSize().padding(bottom = 20.dp)
                    )
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                        onClick = { makeCall(contacto.tlf, ctx) }){
                        Text(contacto.tlf)
                    }

                }

            }
        }
    }

    private fun makeCall(phoneNumber: String, ctx: Context) {
        val phoneUri = Uri.parse("tel:" + phoneNumber)
        val intent = Intent(Intent.ACTION_DIAL, phoneUri)

        try{
            ctx.startActivity(intent)
        }catch(s :SecurityException) {
            Toast.makeText(ctx, "An error ocurred", Toast.LENGTH_SHORT).show()
        }
    }
