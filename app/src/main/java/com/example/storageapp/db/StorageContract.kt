package com.example.storageapp.db

import android.provider.BaseColumns

object StorageContract
{
	object Usuario : BaseColumns
	{
		const val TABLE_NAME = "usuario"
		const val COLUMN_NAME_NOMBRE = "nombre"
		const val COLUMN_NAME_EDAD = "edad"
	}
	object Gasto : BaseColumns
	{
		const val TABLE_NAME = "gasto"
		const val COLUMN_NAME_CANTIDAD = "cantidad"
		const val COLUMN_NAME_CATEGORIA = "categoria"
		const val COLUMN_NAME_USUARIO_ID = "usuario_id"
	}
}
