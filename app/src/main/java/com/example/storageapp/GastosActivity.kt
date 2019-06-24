package com.example.storageapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.example.storageapp.models.Categoria.*
import com.example.storageapp.service.GastoService
import com.example.storageapp.service.UsuarioService
import java.math.BigDecimal

class GastosActivity : AppCompatActivity()
{
	private var categoria = COMIDA
	private val usuarioService = UsuarioService()
	private val gastoService = GastoService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastos)

		findViewById<RadioButton>(R.id.radioButtonComida).isChecked = true
		findViewById<RadioButton>(R.id.radioButtonComida)
			.setOnClickListener { categoria = COMIDA }

		findViewById<RadioButton>(R.id.radioButtonBebida)
			.setOnClickListener { categoria = BEBIDA }

		findViewById<RadioButton>(R.id.radioButtonTransporte)
			.setOnClickListener { categoria = TRANSPORTE }

		val usuario = usuarioService.cargarUsuario() ?: return
		findViewById<Button>(R.id.buttonGuardarGasto)
            .setOnClickListener {
                gastoService.guardarGasto(
					BigDecimal(findViewById<TextView>(R.id.editTextGasto).text.toString()),
					categoria, usuario)
                finish()
            }
    }
}
