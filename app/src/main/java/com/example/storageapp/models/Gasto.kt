package com.example.storageapp.models

import java.math.BigDecimal

class Gasto(
	var id: Long = 0,
	val cantidad: BigDecimal,
	val categoria: Categoria,
	val usuarioId: Long)
{
	override fun toString(): String {
		return "Gasto(id=$id, cantidad=$cantidad, categoria=$categoria, usuarioId=$usuarioId)"
	}
}