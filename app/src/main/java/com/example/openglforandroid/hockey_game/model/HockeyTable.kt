package com.example.openglforandroid.hockey_game.model

import android.opengl.GLES20
import com.example.openglforandroid.base.ElementDrawer
import com.example.openglforandroid.glUtil.ShaderProgram
import com.example.openglforandroid.glUtil.floatBufferOf
import com.example.openglforandroid.glUtil.intBufferOf
import com.example.openglforandroid.glUtil.runGL

class HockeyTable : ElementDrawer {

    // 삼각형 두개로 사각형을 만든다.
    private val tableVertices = floatBufferOf(
        -0.5f, 0.5f, 0f,
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0.5f, 0.5f, 0f,
        -0.5f, 0f, 0f,
        0.5f, 0f, 0f,
    )

    private val tableVertexIndices = intBufferOf(
        0, 1, 2, 0, 2, 3
    )

    private val tableLineVertexIndices = intBufferOf(
        4, 5
    )

    override fun draw(program: ShaderProgram) {
        val vertexPointer = runGL { program.getAttributePointer("v_Position") }
        val colorPointer = runGL { program.getUniformPointer("u_Color") }
        runGL { GLES20.glUniform4f(colorPointer, 1.0f, 1.0f, 1.0f, 1.0f) }
        runGL { GLES20.glVertexAttribPointer(vertexPointer, 3, GLES20.GL_FLOAT, false, 0, tableVertices) } // vertex정보들을 vertexArray에게 전달한다.
        runGL { GLES20.glEnableVertexAttribArray(vertexPointer) } // vertexArray를 활성화 한다.
        runGL { GLES20.glDrawElements(GLES20.GL_TRIANGLES, tableVertexIndices.capacity(), GLES20.GL_UNSIGNED_INT, tableVertexIndices) }
        runGL { GLES20.glUniform4f(colorPointer, 1.0f, 0.0f, 0.0f, 1.0f) }
        runGL { GLES20.glDrawElements(GLES20.GL_LINES, tableLineVertexIndices.capacity(), GLES20.GL_UNSIGNED_INT, tableLineVertexIndices) }
    }
}
