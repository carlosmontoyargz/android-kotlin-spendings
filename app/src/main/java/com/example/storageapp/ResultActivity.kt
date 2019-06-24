package com.example.storageapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.storageapp.models.Categoria
import com.example.storageapp.service.GastoService
import com.example.storageapp.service.UsuarioService
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class ResultActivity : AppCompatActivity()
{
	private val usuarioService = UsuarioService()
	private val gastoService = GastoService()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_result)

		//val usuario = usuarioService.cargarUsuarioExternal(openFileInput("usuario"))
		val usuario = usuarioService.cargarUsuario()
		val totals = HashMap<Categoria, BigDecimal>()
		var total = ZERO
		Categoria.values().forEach { c ->
			val gasto = gastoService.cargarGasto(c, usuario!!)
			totals[c] = gasto
			total += gasto
		}

		findViewById<TextView>(R.id.textViewNombreResult).text = usuario?.nombre
		findViewById<TextView>(R.id.textViewEdadResult).text = usuario?.edad.toString()
		findViewById<TextView>(R.id.textViewComidaGasto).text = "$${totals[Categoria.COMIDA]}"
		findViewById<TextView>(R.id.textViewBebidaGasto).text = "$${totals[Categoria.BEBIDA]}"
		findViewById<TextView>(R.id.textViewTransporteGasto).text = "$${totals[Categoria.TRANSPORTE]}"
		findViewById<TextView>(R.id.textViewTotalResult).text = "$${total}"
	}
}
