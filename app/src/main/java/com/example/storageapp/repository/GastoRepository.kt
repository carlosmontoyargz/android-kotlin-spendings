package com.example.storageapp.repository

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.storageapp.db.StorageContract
import com.example.storageapp.db.StorageDbHelper
import com.example.storageapp.models.Categoria
import com.example.storageapp.models.Gasto
import com.example.storageapp.models.Usuario

class GastoRepository(context: Context)
{
	private val dbHelper = StorageDbHelper(context)
	private val projection = arrayOf(
		BaseColumns._ID,
		StorageContract.Gasto.COLUMN_NAME_CANTIDAD,
		StorageContract.Gasto.COLUMN_NAME_CATEGORIA,
		StorageContract.Gasto.COLUMN_NAME_USUARIO_ID)

	fun insert(gasto: Gasto) : Gasto
	{
		gasto.id = dbHelper.writableDatabase?.insert(
			StorageContract.Gasto.TABLE_NAME,
			null,
			ContentValues().apply {
				put(StorageContract.Gasto.COLUMN_NAME_CANTIDAD, gasto.cantidad.toString())
				put(StorageContract.Gasto.COLUMN_NAME_CATEGORIA, gasto.categoria.name)
				put(StorageContract.Gasto.COLUMN_NAME_USUARIO_ID, gasto.usuarioId)
			})!!
		Log.i("DB", "Gasto guardado :  $gasto")
		return gasto
	}

	fun findByUsuarioAndCategoria(usuario: Usuario, categoria: Categoria) : List<Gasto> {
		//val selection = "${StorageContract.Usuario.COLUMN_NAME_NOMBRE} = ?"
		//val selectionArgs = arrayOf("Carlos")
		val cursor = dbHelper.readableDatabase.query(
			StorageContract.Gasto.TABLE_NAME,   // The table to query
			this.projection,            	// The array of columns to return (pass null to get all)
			"${StorageContract.Gasto.COLUMN_NAME_USUARIO_ID} = ? " +
					"AND ${StorageContract.Gasto.COLUMN_NAME_CATEGORIA} = ?",//selection,             	// The columns for the WHERE clause
			arrayOf(usuario.id.toString(), categoria.name),//selectionArgs,       	// The values for the WHERE clause
			null,          // don't group the rows
			null,           	// don't filter by row groups
			null // The sort order
		)
		val gastos = mutableListOf<Gasto>()
		with(cursor) {
			while (moveToNext())
				gastos.add(Gasto(
					id = getLong(getColumnIndex(BaseColumns._ID)),
					cantidad = getString(getColumnIndexOrThrow(StorageContract.Gasto.COLUMN_NAME_CANTIDAD))
						.toBigDecimal(),
					categoria = Categoria.valueOf(
						getString(getColumnIndexOrThrow(StorageContract.Gasto.COLUMN_NAME_CATEGORIA))),
					usuarioId = getLong(getColumnIndex(StorageContract.Gasto.COLUMN_NAME_USUARIO_ID))))
		}
		Log.i("DB", gastos.toString())
		return gastos
	}
}
