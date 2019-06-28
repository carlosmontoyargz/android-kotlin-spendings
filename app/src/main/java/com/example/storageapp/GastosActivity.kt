package com.example.storageapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.example.storageapp.models.Categoria.*
import com.example.storageapp.models.Gasto
import com.example.storageapp.models.Usuario
import com.example.storageapp.repository.GastoRepository
import java.math.BigDecimal

class GastosActivity : AppCompatActivity()
{
	private var categoria = COMIDA
	private val gastoRepository = GastoRepository(this)

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

		val usuario = intent.getSerializableExtra("usuario") as Usuario
		findViewById<Button>(R.id.buttonGuardarGasto)
            .setOnClickListener {
                gastoRepository.insert(Gasto(
					cantidad = BigDecimal(findViewById<TextView>(R.id.editTextGasto).text.toString()),
					categoria = categoria,
					usuarioId = usuario.id))
				finish()
            }

		findViewById<Button>(R.id.buttonCancelar)
			.setOnClickListener { finish() }
    }
}
