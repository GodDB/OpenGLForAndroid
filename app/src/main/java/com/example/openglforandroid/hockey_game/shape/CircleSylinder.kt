package com.example.openglforandroid.hockey_game.shape

import android.opengl.GLES20
import com.example.openglforandroid.base.ElementDrawer
import com.example.openglforandroid.glUtil.FLOAT_BYTE_SIZE
import com.example.openglforandroid.glUtil.ShaderProgram
import com.example.openglforandroid.glUtil.runGL
import com.example.openglforandroid.glUtil.toBuffer
import kotlin.math.cos
import kotlin.math.sin

class CircleSylinder(
    private val radius: Float = 1f,
    private val height: Float = 1f,
    private val centerX: Float = 0f,
    private val centerY: Float = 0f,
    private val centerZ: Float = 0f
) : ElementDrawer {

    private val pointSize: Int = 100

    private val vertices = kotlin.run {
        val floatArray = mutableListOf<Float>()
        for (i in 0..pointSize) {
            val radian = (i.toFloat() / pointSize.toFloat()) * (2 * Math.PI)
            // 해당 라디안의 점 구해서 넣기
            floatArray.add(centerX + (radius * cos(radian).toFloat())) // x
            floatArray.add(centerY - (height / 2) ) // y
            floatArray.add(centerZ + (radius * sin(radian).toFloat()))

            // 해당 라디안의 점 구해서 넣기
            floatArray.add(centerX + (radius * cos(radian).toFloat())) // x
            floatArray.add(centerY + (height / 2) ) // y
            floatArray.add(centerZ + (radius * sin(radian).toFloat()))
        }
        floatArray.toBuffer()
    }

    private val vertexIndices = kotlin.run {
        (0..(pointSize * 2) + 1).toList().toBuffer()
    }

    override fun onSurfaceChanged(width: Int, height: Int, program: ShaderProgram) {
    }

    override fun draw(program: ShaderProgram) {
        val vertexPointer = runGL { program.getAttributePointer("v_Position") }
        runGL { GLES20.glVertexAttribPointer(vertexPointer, 3, GLES20.GL_FLOAT, false, 3 * FLOAT_BYTE_SIZE, vertices) } // vertex정보들을 vertexArray에게 전달한다.
        runGL { GLES20.glEnableVertexAttribArray(vertexPointer) } // vertexArray를 활성화 한다.
        runGL { GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, vertexIndices.capacity(), GLES20.GL_UNSIGNED_INT, vertexIndices) }
        runGL { GLES20.glEnableVertexAttribArray(0) } // vertexArray를 비 활성화 한다.
    }
}
