package com.example.openglforandroid.glUtil

import android.opengl.GLES20
import android.opengl.Matrix
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

const val INT_BYTE_SIZE = 4
const val FLOAT_BYTE_SIZE = 4

fun floatBufferOf(vararg value: Float): FloatBuffer {
    return ByteBuffer
        .allocateDirect(value.size * FLOAT_BYTE_SIZE) // native 메모리 할당 사이즈
        .order(ByteOrder.nativeOrder()) // 왼쪽부터 채울것인지? 오른쪽부터 채울것인지? 시스템이 알아서 함
        .asFloatBuffer()
        .apply {
            put(value) // 데이터 전달
            position(0)
        }
}

fun intBufferOf(vararg value: Int): IntBuffer {
    return ByteBuffer
        .allocateDirect(value.size * INT_BYTE_SIZE) // native 메모리 할당 사이즈
        .order(ByteOrder.nativeOrder()) // 왼쪽부터 채울것인지? 오른쪽부터 채울것인지? 시스템이 알아서 함
        .asIntBuffer()
        .apply {
            put(value) // 데이터 전달
            position(0)
        }
}

fun FloatArray.toBuffer() : FloatBuffer {
    return ByteBuffer
        .allocateDirect(this.size * FLOAT_BYTE_SIZE) // native 메모리 할당 사이즈
        .order(ByteOrder.nativeOrder()) // 왼쪽부터 채울것인지? 오른쪽부터 채울것인지? 시스템이 알아서 함
        .asFloatBuffer()
        .apply {
            put(this@toBuffer) // 데이터 전달
            position(0)
        }
}

fun createIdentity4Matrix() : FloatArray {
    return FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
    }
}

inline fun <T> runGL(block: () -> T): T {
    val result = block()
    val error = GLES20.glGetError()
    if (error != GLES20.GL_NO_ERROR) {
        val msg = ": glError 0x" + Integer.toHexString(error)
        Log.e("godgod", msg)
        throw RuntimeException(msg)
    }
    return result
}
