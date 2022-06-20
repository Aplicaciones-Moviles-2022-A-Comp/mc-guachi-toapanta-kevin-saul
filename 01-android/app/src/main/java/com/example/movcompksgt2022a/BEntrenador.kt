package com.example.movcompksgt2022a

class BEntrenador(
    val nombre:String?,
    val descripcion:String?
) {
    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }
}