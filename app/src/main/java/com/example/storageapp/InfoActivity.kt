package com.example.storageapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.storageapp.models.Usuario
import com.example.storageapp.service.UsuarioService

class InfoActivity : AppCompatActivity()
{
	private val usuarioService = UsuarioService()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_info)

		val usuario = usuarioService.cargarUsuario()
		//val usuario = usuarioService.cargarUsuarioInternal(openFileInput("usuario"))
		//val usuario = usuarioService.cargarUsuarioExternal()
		if (usuario != null) {
			findViewById<TextView>(R.id.editTextNombre).text = usuario.nombre
			findViewById<TextView>(R.id.editTextEdad).text = usuario.edad.toString()
		}

		findViewById<Button>(R.id.buttonNuevoGasto)
			.setOnClickListener { startUserActivity(GastosActivity::class.java) }

		findViewById<Button>(R.id.buttonVerGastos)
			.setOnClickListener { startUserActivity(ResultActivity::class.java) }

		//ExternalStorageTools().getPublicDocumentStorageDir()
	}

	private fun startUserActivity(activity: Class<*>)
	{
		val nombre = findViewById<TextView>(R.id.editTextNombre).text
		val edad = findViewById<TextView>(R.id.editTextEdad).text
		if (nombre.isNotEmpty() && edad.isNotEmpty()) {
			usuarioService.guardarUsuario(
				Usuario(nombre = nombre.toString(), edad = Integer.parseInt(edad.toString())))
			startActivity(Intent(this, activity))
		}
		else Toast
			.makeText(applicationContext, "No se ha especificado usuario o edad", Toast.LENGTH_SHORT)
			.show()
	}
}
