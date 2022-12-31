package com.example.openglforandroid.glUtil

import android.opengl.GLES20
import android.util.Log
import android.widget.Toast

class Shader(sourceCode: String, type: Type) {
    val shaderId : Int

    enum class Type(val glValue : Int) {
        FRAGMENT(GLES20.GL_FRAGMENT_SHADER),
        VERTEX(GLES20.GL_VERTEX_SHADER);
    }

    init {
        shaderId = compileShader(sourceCode, type)
    }

    private fun compileShader(sourceCode: String, type: Type) : Int {
        val shaderId = runGL { GLES20.glCreateShader(type.glValue) }
        if(shaderId == 0) {
            Log.e("godgod", "could not create new shader.")
        }

        runGL { GLES20.glShaderSource(shaderId, sourceCode) }
        runGL { GLES20.glCompileShader(shaderId) }
        val compiledInfoArr = IntArray(1)
        GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compiledInfoArr, 0)
        return if(compiledInfoArr.first() == 0) {
            Log.e("godgod", "Could not compile shader ${type.name}:")
            Log.e("godgod", " " + GLES20.glGetShaderInfoLog(shaderId))
            GLES20.glDeleteShader(shaderId)
            0
        } else {
            shaderId
        }
    }

    override fun toString(): String {
        return "shaderId = $shaderId \n"
    }
}


