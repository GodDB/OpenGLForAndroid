package com.example.openglforandroid.glUtil


data class Vector3D(var x: Float, var y: Float, var z: Float) {

    companion object {
        fun createZero(): Vector3D = Vector3D(0f, 0f, 0f)
    }
}
