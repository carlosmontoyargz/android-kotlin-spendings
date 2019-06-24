package com.example.storageapp.service

import android.os.Environment
import com.example.storageapp.models.Usuario
import com.example.storageapp.tool.ExternalStorageTools
import java.io.*

class UsuarioService
{
	private val strgTool = ExternalStorageTools()

	fun guardarUsuario(usuario: Usuario) =
		if (strgTool.isExternalStorageWritable()) {
			val file = File(Environment.getExternalStorageDirectory(), "user.txt")
			file.createNewFile()
			BufferedWriter(FileWriter(file)).use {
				it.write(usuario.nombre.plus("|").plus(usuario.edad))
			}
		} else null

	fun cargarUsuario() : Usuario? =
		if (strgTool.isExternalStorageReadable()) {
			val file = File(Environment.getExternalStorageDirectory(), "user.txt")
			if (file.exists()) {
				BufferedReader(FileReader(file)).use {
					val split = it.readLine().split("|")
					Usuario(nombre = split[0], edad = Integer.parseInt(split[1]))
				}
			} else null
		} else null

	fun cargarUsuarioExternal() : Usuario? =
		if (strgTool.isExternalStorageReadable()) {
			val userFiles = strgTool
				.getPublicDocumentStorageDir()
				.listFiles(FileFilter { it.name == "usuario.txt" })

			if (userFiles.isNotEmpty()) {
				val line = BufferedReader(FileReader(userFiles[0])).readLine()
				if (line != null) {
					val split = line.split("|")
					Usuario(nombre = split[0], edad = Integer.parseInt(split[1]))
				} else null
			} else null
		} else null

	fun guardarUsuarioExternal(usuario: Usuario) {
		if (strgTool.isExternalStorageWritable()) {
			val usuarioFile = strgTool
				.getPublicDocumentStorageDir()
				.resolve("usuario.txt")
			usuarioFile.createNewFile()

			BufferedWriter(FileWriter(usuarioFile)).use {
				it.write(usuario.nombre.plus("|").plus(usuario.edad))
			}
		}
	}
}
