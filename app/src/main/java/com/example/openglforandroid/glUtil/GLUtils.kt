package com.example.openglforandroid.glUtil

import android.opengl.GLES20
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

private const val INT_BYTE_SIZE = 4
private const val FLOAT_BYTE_SIZE = 4

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

inline fun <T> runGL(block: () -> T) : T {
    val result = block()
    val error = GLES20.glGetError()
    if (error != GLES20.GL_NO_ERROR) {
        val msg = ": glError 0x" + Integer.toHexString(error)
        Log.e("godgod", msg)
        throw RuntimeException(msg)
    }
    return result
}
