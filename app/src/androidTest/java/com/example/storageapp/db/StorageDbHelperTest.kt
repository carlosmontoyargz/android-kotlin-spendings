package com.example.storageapp.db

import android.content.ContentValues
import android.provider.BaseColumns
import android.support.test.InstrumentationRegistry
import com.example.storageapp.models.Usuario
import org.junit.Test

class StorageDbHelperTest
{
	@Test
	fun testDB() {
		val dbHelper = StorageDbHelper(InstrumentationRegistry.getInstrumentation().context)

		// INSERT DB
		val dbWr = dbHelper.writableDatabase
		val values = ContentValues().apply {
			put(StorageContract.Usuario.COLUMN_NAME_NOMBRE, "Carlos")
			put(StorageContract.Usuario.COLUMN_NAME_EDAD, "23")
		}
		val newRowId = dbWr?.insert(StorageContract.Usuario.TABLE_NAME, null, values)
		println(newRowId)

		// READ DB
		val dbRd = dbHelper.readableDatabase
		val projection = arrayOf(
			BaseColumns._ID,
			StorageContract.Usuario.COLUMN_NAME_NOMBRE,
			StorageContract.Usuario.COLUMN_NAME_EDAD)
		val selection = "${StorageContract.Usuario.COLUMN_NAME_NOMBRE} = ?"
		val selectionArgs = arrayOf("Carlos")
		val sortOrder = "${StorageContract.Usuario.COLUMN_NAME_EDAD} DESC"

		val cursor = dbRd.query(
			StorageContract.Usuario.TABLE_NAME,   // The table to query
			projection,            	// The array of columns to return (pass null to get all)
			selection,             	// The columns for the WHERE clause
			selectionArgs,       	// The values for the WHERE clause
			null,          // don't group the rows
			null,           	// don't filter by row groups
			sortOrder              	// The sort order
		)
		val usuarios = mutableListOf<Usuario>()
		with(cursor) {
			while (moveToNext()) {
				usuarios.add(
					Usuario(
						id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
						nombre = getString(getColumnIndexOrThrow(StorageContract.Usuario.COLUMN_NAME_NOMBRE)),
						edad = getInt(getColumnIndexOrThrow(StorageContract.Usuario.COLUMN_NAME_EDAD))
					)
				)
			}
		}
		println(usuarios)
	}
}
