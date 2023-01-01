package com.example.openglforandroid.glUtil

import android.opengl.GLES20
import android.util.Log
import java.nio.FloatBuffer

class ShaderProgram(
    val vertexShader: Shader,
    val fragmentShader: Shader
) {
    val programId: Int

    init {
        programId = createProgram(vertexShader, fragmentShader)
    }

    val pointerMap: HashMap<String, Int> = hashMapOf()

    private fun createProgram(vertexShader: Shader, fragmentShader: Shader): Int {
        var program = runGL { GLES20.glCreateProgram() }
        if (program == 0) {
            Log.e("godgod", "Could not create program")
        }

        runGL { GLES20.glAttachShader(program, vertexShader.shaderId) }
        runGL { GLES20.glAttachShader(program, fragmentShader.shaderId) }

        runGL { GLES20.glLinkProgram(program) }
        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e("godgod", "Could not link program: ")
            Log.e("godgod", GLES20.glGetProgramInfoLog(program))
            GLES20.glDeleteProgram(program)
            program = 0
        }
        return program
    }

    fun getAttributePointer(key: String): Int {
        var result = pointerMap[key]
        if (result == null) {
            val pointer = GLES20.glGetAttribLocation(programId, key)
            pointerMap[key] = pointer
            result = pointerMap[key]
        }
        if (result == -1) {
            throw java.lang.RuntimeException("not valid key")
        }
        return checkNotNull(result)
    }

    fun updateAttribute4f(key: String, x: Float, y: Float, z: Float, w: Float) {

    }

    fun updateAttribute3f(key: String, x: Float, y: Float, z: Float) {

    }

    fun getUniformPointer(key: String): Int {
        var result = pointerMap[key]
        if (result == null) {
            val pointer = runGL { GLES20.glGetUniformLocation(programId, key) }
            pointerMap[key] = pointer
            result = pointerMap[key]
        }
        if (result == -1) {
            throw java.lang.RuntimeException("not valid key")
        }
        return checkNotNull(result)
    }

    fun updateUniformMatrix4f(key: String, buffer: FloatBuffer) {
        val pointer = getUniformPointer(key)
        runGL { GLES20.glUniformMatrix4fv(pointer, 1, false, buffer) }
    }

    fun updateUniform4f(key: String, x: Float, y: Float, z: Float, w: Float) {
        val pointer = getUniformPointer(key)
        runGL { GLES20.glUniform4f(pointer, x, y, z, w) }
    }

    fun updateUniform3f(key: String, x: Float, y: Float, z: Float) {
        val pointer = getUniformPointer(key)
        runGL { GLES20.glUniform3f(pointer, x, y, z) }
    }

    fun activate() {
        runGL { GLES20.glUseProgram(programId) }
    }

    fun deactivate() {
        runGL { GLES20.glUseProgram(0) }
    }
}
