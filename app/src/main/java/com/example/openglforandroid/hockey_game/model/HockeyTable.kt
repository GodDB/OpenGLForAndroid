package com.example.openglforandroid.hockey_game.model

import android.opengl.GLES20
import com.example.openglforandroid.base.ElementDrawer
import com.example.openglforandroid.glUtil.FLOAT_BYTE_SIZE
import com.example.openglforandroid.glUtil.ShaderProgram
import com.example.openglforandroid.glUtil.floatBufferOf
import com.example.openglforandroid.glUtil.intBufferOf
import com.example.openglforandroid.glUtil.runGL

class HockeyTable : ElementDrawer {

    // 삼각형 두개로 사각형을 만든다.
    private val tableVertices = floatBufferOf(
        // xyz rgb
        -0.5f, 0.5f, 0f, 1f, 1f, 1f,
        -0.5f, -0.5f, 0f, 0.5f, 0.5f, 0.5f,
        0.5f, -0.5f, 0f, 0f, 0f, 0f,
        0.5f, 0.5f, 0f, 1.0f, 1.0f, 1.0f,
        -0.5f, 0f, 0f, 1.0f, 1.0f, 1.0f,
        0.5f, 0f, 0f, 1.0f, 1.0f, 1.0f,
    )

    private val tableVertexIndices = intBufferOf(
        0, 1, 2, 0, 2, 3
    )

    private val tableLineVertexIndices = intBufferOf(
        4, 5
    )

    override fun draw(program: ShaderProgram) {
        val vertexPointer = runGL { program.getAttributePointer("v_Position") }
        val colorPointer = runGL { program.getAttributePointer("v_Color") }
        setupVertices(vertexPointer)
        setupColor(colorPointer)
        runGL { GLES20.glDrawElements(GLES20.GL_TRIANGLES, tableVertexIndices.capacity(), GLES20.GL_UNSIGNED_INT, tableVertexIndices) }
        runGL { GLES20.glDrawElements(GLES20.GL_LINES, tableLineVertexIndices.capacity(), GLES20.GL_UNSIGNED_INT, tableLineVertexIndices) }
        runGL { GLES20.glEnableVertexAttribArray(0) } // vertexArray를 비활성화 한다.
    }

    private fun setupVertices(pointerId: Int) {
        tableVertices.position(0)
        runGL { GLES20.glVertexAttribPointer(pointerId, 3, GLES20.GL_FLOAT, false, 6 * FLOAT_BYTE_SIZE, tableVertices) } // vertex정보들을 vertexArray에게 전달한다.
        runGL { GLES20.glEnableVertexAttribArray(pointerId) } // vertexArray를 활성화 한다.
    }

    private fun setupColor(pointerId: Int) {
        tableVertices.position(3)
        runGL { GLES20.glVertexAttribPointer(pointerId, 3, GLES20.GL_FLOAT, false, 6 * FLOAT_BYTE_SIZE, tableVertices) } // vertex정보들을 vertexArray에게 전달한다.
        runGL { GLES20.glEnableVertexAttribArray(pointerId) } // vertexArray를 활성화 한다.
    }
}
