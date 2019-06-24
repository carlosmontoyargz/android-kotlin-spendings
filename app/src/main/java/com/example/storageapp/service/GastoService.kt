package com.example.storageapp.service

import android.os.Environment
import com.example.storageapp.models.Categoria
import com.example.storageapp.models.Usuario
import com.example.storageapp.tool.ExternalStorageTools
import java.io.*
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class GastoService
{
	private val strgTool = ExternalStorageTools()

	fun guardarGasto(cantidad: BigDecimal, categoria: Categoria, usuario: Usuario)
	{
		if (strgTool.isExternalStorageWritable()) {
			val file = File(Environment.getExternalStorageDirectory(),
				"gastos-${usuario.nombre}-${categoria.name}")
			file.createNewFile()
			BufferedWriter(FileWriter(file, true)).use {
				it.write(cantidad.toString().plus("|"))
			}
		}
	}

	fun cargarGasto(categoria: Categoria, usuario: Usuario) : BigDecimal =
		if (strgTool.isExternalStorageReadable()) {
			val file = File(Environment.getExternalStorageDirectory(),
				"gastos-${usuario.nombre}-${categoria.name}")
			if (file.exists()) {
				BufferedReader(FileReader(file)).use {
					var total = ZERO
					it.readLine()
						.split("|")
						.forEach { g -> if (g.isNotEmpty()) total += BigDecimal(g) }
					total
				}
			} else ZERO
		} else ZERO
}
