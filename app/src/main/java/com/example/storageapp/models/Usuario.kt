package com.example.storageapp.models

import java.io.Serializable

class Usuario(
	var id: Long = 0,
	val nombre: String,
	val edad: Int) : Serializable
{
	override fun toString(): String {
		return "Usuario(id=$id, nombre='$nombre', edad=$edad)"
	}
}
