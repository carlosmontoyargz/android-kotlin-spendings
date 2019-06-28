package com.example.storageapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class StorageDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
	companion object {
		const val DATABASE_NAME = "StorageApp.db"
		const val DATABASE_VERSION = 5
	}

	override fun onCreate(db: SQLiteDatabase) {
		db.execSQL(SQL_CREATE_USUARIO)
		db.execSQL(SQL_CREATE_GASTO)
	}
	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES)
		onCreate(db)
	}
	override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		onUpgrade(db, oldVersion, newVersion)
	}
}

private const val SQL_CREATE_USUARIO =
		"CREATE TABLE ${StorageContract.Usuario.TABLE_NAME} (" +
		"	${BaseColumns._ID} INTEGER PRIMARY KEY," +
		"	${StorageContract.Usuario.COLUMN_NAME_NOMBRE} TEXT," +
		"	${StorageContract.Usuario.COLUMN_NAME_EDAD} INTEGER" +
		")"
private const val SQL_CREATE_GASTO =
		"CREATE TABLE ${StorageContract.Gasto.TABLE_NAME} (" +
		"	${BaseColumns._ID} INTEGER PRIMARY KEY," +
		"	${StorageContract.Gasto.COLUMN_NAME_CANTIDAD} TEXT," +
		"	${StorageContract.Gasto.COLUMN_NAME_CATEGORIA} TEXT," +
		"	${StorageContract.Gasto.COLUMN_NAME_USUARIO_ID} INTEGER" +
		")"

private const val SQL_DELETE_ENTRIES =
		"DROP TABLE IF EXISTS ${StorageContract.Usuario.TABLE_NAME};" +
		"DROP TABLE IF EXISTS ${StorageContract.Gasto.TABLE_NAME};"
