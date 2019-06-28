package com.example.storageapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.example.storageapp.models.Categoria
import com.example.storageapp.models.Usuario
import com.example.storageapp.repository.GastoRepository
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class ResultActivity : AppCompatActivity()
{
	private val gastoRepository = GastoRepository(this)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_result)

		findViewById<Button>(R.id.buttonRegresar)
			.setOnClickListener { finish() }

		//val usuario = usuarioService.cargarUsuarioExternal(openFileInput("usuario"))
		val usuario = intent.getSerializableExtra("usuario") as Usuario
		val totals = HashMap<Categoria, BigDecimal>()
		var total = ZERO
		Categoria.values().forEach { c ->
			val gastos = gastoRepository.findByUsuarioAndCategoria(usuario, c)

			var sum = BigDecimal.ZERO
			gastos.forEach { sum += it.cantidad }
			totals[c] = sum
			total += sum
		}

		findViewById<TextView>(R.id.textViewNombreResult).text = usuario.nombre
		findViewById<TextView>(R.id.textViewEdadResult).text = usuario.edad.toString()
		findViewById<TextView>(R.id.textViewComidaGasto).text = "$${totals[Categoria.COMIDA]}"
		findViewById<TextView>(R.id.textViewBebidaGasto).text = "$${totals[Categoria.BEBIDA]}"
		findViewById<TextView>(R.id.textViewTransporteGasto).text = "$${totals[Categoria.TRANSPORTE]}"
		findViewById<TextView>(R.id.textViewTotalResult).text = "$${total}"
	}
}
