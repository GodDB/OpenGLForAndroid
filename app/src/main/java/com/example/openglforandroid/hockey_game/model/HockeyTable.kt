package com.example.openglforandroid.hockey_game.model

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import com.example.openglforandroid.R
import com.example.openglforandroid.base.ElementDrawer
import com.example.openglforandroid.glUtil.FLOAT_BYTE_SIZE
import com.example.openglforandroid.glUtil.ShaderProgram
import com.example.openglforandroid.glUtil.Texture
import com.example.openglforandroid.glUtil.createIdentity4Matrix
import com.example.openglforandroid.glUtil.floatBufferOf
import com.example.openglforandroid.glUtil.intBufferOf
import com.example.openglforandroid.glUtil.runGL
import com.example.openglforandroid.glUtil.toBuffer

class HockeyTable(private val context: Context) : ElementDrawer {

    // 삼각형 두개로 사각형을 만든다.
    private val tableVertices = floatBufferOf(
        // location - xyz / texture-location xy
        0f, 0f, 0f, 0.5f, 0.5f,
        -0.5f, -0.8f, 0f, 0f, 0.9f,
        0.5f, -0.8f, 0f, 1f, 0.9f,
        0.5f, 0.8f, 0f, 1f, 0.1f,
        -0.5f, 0.8f, 0f, 0f, 0.1f,
    )

    private val tableVertexIndices = intBufferOf(
        0, 1, 2,
        3, 4, 1,
        0, 2, 3
    )

    private val texture by lazy { Texture(context, R.drawable.hockey_board) }


    override fun onSurfaceChanged(width: Int, height: Int, program: ShaderProgram) {
        val modelTransform = createIdentity4Matrix()
        Matrix.translateM(modelTransform, 0, 0f, 0f, -3f)
        Matrix.rotateM(modelTransform, 0, -60f, 1f, 0f, 0f)
        program.updateUniformMatrix4f("u_Model", modelTransform.toBuffer())
    }

    override fun draw(program: ShaderProgram) {
        texture.bind()
        val vertexPointer = runGL { program.getAttributePointer("v_Position") }
        val texturePosPointer = runGL { program.getAttributePointer("v_Texture_Position") }

        setupVertices(vertexPointer)
        setupTexture(texturePosPointer)
        runGL { GLES20.glDrawElements(GLES20.GL_TRIANGLES, tableVertexIndices.capacity(), GLES20.GL_UNSIGNED_INT, tableVertexIndices) }
        runGL { GLES20.glEnableVertexAttribArray(0) } // vertexArray를 비활성화 한다.
        texture.unbind()
    }

    private fun setupVertices(pointerId: Int) {
        tableVertices.position(0)
        runGL { GLES20.glVertexAttribPointer(pointerId, 3, GLES20.GL_FLOAT, false, 5 * FLOAT_BYTE_SIZE, tableVertices) } // vertex정보들을 vertexArray에게 전달한다.
        runGL { GLES20.glEnableVertexAttribArray(pointerId) } // vertexArray를 활성화 한다.
    }

    private fun setupTexture(pointerId: Int) {
        tableVertices.position(3)
        runGL { GLES20.glVertexAttribPointer(pointerId, 2, GLES20.GL_FLOAT, false, 5 * FLOAT_BYTE_SIZE, tableVertices) } // vertex정보들을 vertexArray에게 전달한다.
        runGL { GLES20.glEnableVertexAttribArray(pointerId) } // vertexArray를 활성화 한다.
    }
}
