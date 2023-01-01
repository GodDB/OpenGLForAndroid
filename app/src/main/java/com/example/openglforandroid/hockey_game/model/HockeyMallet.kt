package com.example.openglforandroid.hockey_game.model

import android.opengl.GLES20
import com.example.openglforandroid.base.ElementDrawer
import com.example.openglforandroid.glUtil.FLOAT_BYTE_SIZE
import com.example.openglforandroid.glUtil.ShaderProgram
import com.example.openglforandroid.glUtil.floatBufferOf
import com.example.openglforandroid.glUtil.intBufferOf
import com.example.openglforandroid.glUtil.runGL

class HockeyMallet : ElementDrawer {

    private val vertices = floatBufferOf(
        0f , -0.25f, 0f,
        0f, 0.25f, 0f
    )

    private val vertexIndices = intBufferOf(
        0, 1
    )

    override fun onSurfaceChanged(width: Int, height: Int, program: ShaderProgram) {

    }

    override fun draw(program: ShaderProgram) {
        val vertexPointer = runGL { program.getAttributePointer("v_Position") }
        runGL { GLES20.glVertexAttribPointer(vertexPointer, 3, GLES20.GL_FLOAT, false, 3 * FLOAT_BYTE_SIZE, vertices) } // vertex정보들을 vertexArray에게 전달한다.
        runGL { GLES20.glEnableVertexAttribArray(vertexPointer) } // vertexArray를 활성화 한다.
        runGL { GLES20.glDrawElements(GLES20.GL_POINTS, vertexIndices.capacity(), GLES20.GL_UNSIGNED_INT, vertexIndices) }
        runGL { GLES20.glEnableVertexAttribArray(0) } // vertexArray를 비 활성화 한다.
    }
}
