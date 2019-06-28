package com.example.storageapp.repository

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.storageapp.db.StorageContract
import com.example.storageapp.db.StorageDbHelper
import com.example.storageapp.models.Usuario

class UsuarioRepository(context: Context)
{
	private val dbHelper = StorageDbHelper(context)
	private val projection = arrayOf(
		BaseColumns._ID,
		StorageContract.Usuario.COLUMN_NAME_NOMBRE,
		StorageContract.Usuario.COLUMN_NAME_EDAD)

	fun findAll() : List<Usuario> {
		//val selection = "${StorageContract.Usuario.COLUMN_NAME_NOMBRE} = ?"
		//val selectionArgs = arrayOf("Carlos")
		val cursor = dbHelper.readableDatabase.query(
			StorageContract.Usuario.TABLE_NAME,   // The table to query
			this.projection,            	// The array of columns to return (pass null to get all)
			null,//selection,             	// The columns for the WHERE clause
			null,//selectionArgs,       	// The values for the WHERE clause
			null,          // don't group the rows
			null,           	// don't filter by row groups
			"${StorageContract.Usuario.COLUMN_NAME_EDAD} DESC" // The sort order
		)
		val usuarios = mutableListOf<Usuario>()
		with(cursor) {
			while (moveToNext()) {
				usuarios.add(
					Usuario(
						id = getLong(getColumnIndex(BaseColumns._ID)),
						nombre = getString(getColumnIndexOrThrow(StorageContract.Usuario.COLUMN_NAME_NOMBRE)),
						edad = getInt(getColumnIndexOrThrow(StorageContract.Usuario.COLUMN_NAME_EDAD))
					)
				)
			}
		}
		Log.i("DB", usuarios.toString())
		return usuarios
	}

	fun saveByName(usuario: Usuario): Usuario
	{
		val usuarioTemp = findByNombre(usuario.nombre)
		return if (usuarioTemp == null)
			insert(usuario)
		else {
			usuario.id = usuarioTemp.id
			update(usuario)
		}
	}

	private fun insert(usuario: Usuario) : Usuario
	{
		usuario.id = dbHelper.writableDatabase?.insert(
			StorageContract.Usuario.TABLE_NAME,
			null,
			ContentValues().apply {
				put(StorageContract.Usuario.COLUMN_NAME_NOMBRE, usuario.nombre)
				put(StorageContract.Usuario.COLUMN_NAME_EDAD, usuario.edad)
			})!!
		Log.i("DB", "Usuario guardado :  $usuario")
		return usuario
	}

	private fun update(usuario: Usuario) : Usuario
	{
		val count = dbHelper.writableDatabase.update(
			StorageContract.Usuario.TABLE_NAME,
			ContentValues().apply {
				put(StorageContract.Usuario.COLUMN_NAME_NOMBRE, usuario.nombre)
				put(StorageContract.Usuario.COLUMN_NAME_EDAD, usuario.edad)
			},
			"${BaseColumns._ID} = ?",
			arrayOf(usuario.id.toString())
		)
		if (count > 0)
			Log.i("DB", "Usuario actualizado : $usuario")
		return findById(usuario.id)!!
	}

	fun delete(usuario: Usuario)
	{
		// DELETE DB
		val selection = "${BaseColumns._ID} = ?"
		val selectionArgs = arrayOf(usuario.id.toString())
		val deletedRows = dbHelper.writableDatabase?.delete(
			StorageContract.Usuario.TABLE_NAME,
			selection,
			selectionArgs)
		if (deletedRows != null && deletedRows > 0)
			Log.i("DB", "Deleted user: $usuario")
	}

	fun findById(id : Long) : Usuario?
	{
		val cursor = dbHelper.readableDatabase.query(
			StorageContract.Usuario.TABLE_NAME,   // The table to query
			this.projection,            	// The array of columns to return (pass null to get all)
			"${BaseColumns._ID} = ?", // The columns for the WHERE clause
			arrayOf(id.toString()),      	// The values for the WHERE clause
			null,          // don't group the rows
			null,           	// don't filter by row groups
			null              	// The sort order
		)
		val usuario : Usuario? = with(cursor) {
			if (moveToNext()) {
				Usuario(id = getLong(getColumnIndex(BaseColumns._ID)),
					nombre = getString(getColumnIndexOrThrow(StorageContract.Usuario.COLUMN_NAME_NOMBRE)),
					edad = getInt(getColumnIndexOrThrow(StorageContract.Usuario.COLUMN_NAME_EDAD)))
			}
			else null
		}
		Log.i("DB", usuario.toString())
		return usuario
	}

	fun findByNombre(nombre: String) : Usuario?
	{
		val cursor = dbHelper.readableDatabase.query(
			StorageContract.Usuario.TABLE_NAME,   // The table to query
			this.projection,            	// The array of columns to return (pass null to get all)
			"${StorageContract.Usuario.COLUMN_NAME_NOMBRE} = ?", // The columns for the WHERE clause
			arrayOf(nombre),      	// The values for the WHERE clause
			null,          // don't group the rows
			null,           	// don't filter by row groups
			null              	// The sort order
		)
		val usuario : Usuario? = with(cursor) {
			if (moveToNext()) {
				Usuario(id = getLong(getColumnIndex(BaseColumns._ID)),
					nombre = getString(getColumnIndexOrThrow(StorageContract.Usuario.COLUMN_NAME_NOMBRE)),
					edad = getInt(getColumnIndexOrThrow(StorageContract.Usuario.COLUMN_NAME_EDAD)))
			}
			else null
		}
		Log.i("DB", usuario.toString())
		return usuario
	}
}
