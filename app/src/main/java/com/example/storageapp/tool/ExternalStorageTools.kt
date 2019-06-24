package com.example.storageapp.tool

import android.os.Environment
import java.io.File

class ExternalStorageTools
{
	/* Checks if external storage is available for read and write */
	fun isExternalStorageWritable(): Boolean {
		return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
	}

	/* Checks if external storage is available to at least read */
	fun isExternalStorageReadable(): Boolean {
		return Environment.getExternalStorageState() in
				setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
	}

	fun getPublicDocumentStorageDir(): File = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
}
