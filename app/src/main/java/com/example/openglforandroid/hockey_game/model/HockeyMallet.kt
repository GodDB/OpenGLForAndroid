package com.example.openglforandroid.hockey_game.model

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import com.example.openglforandroid.base.ElementDrawer
import com.example.openglforandroid.glUtil.FLOAT_BYTE_SIZE
import com.example.openglforandroid.glUtil.ShaderProgram
import com.example.openglforandroid.glUtil.createIdentity4Matrix
import com.example.openglforandroid.glUtil.floatBufferOf
import com.example.openglforandroid.glUtil.intBufferOf
import com.example.openglforandroid.glUtil.runGL
import com.example.openglforandroid.glUtil.toBuffer
import com.example.openglforandroid.hockey_game.shape.Circle
import com.example.openglforandroid.hockey_game.shape.CircleSylinder

class HockeyMallet(private val context: Context) : ElementDrawer {

    private val topCircle = Circle(radius = 1f, direction = Circle.Direction.TOP)
    private val circleSylinder = CircleSylinder()
    private val bottomCircle = Circle(radius = 1f, direction = Circle.Direction.BOTTOM)

    private val topCircleModelTransform = kotlin.run {
        val modelTransform = createIdentity4Matrix()
        Matrix.translateM(modelTransform, 0, 0f, 0.05f, -3f)
        Matrix.scaleM(modelTransform, 0, 0.1f, 0.1f, 0.1f)
        Matrix.rotateM(modelTransform, 0, 25f, 1f, 0f, 0f)
        modelTransform.toBuffer()
    }

    private val sylinderModelTransform = kotlin.run {
        val modelTransform = createIdentity4Matrix()
        Matrix.translateM(modelTransform, 0, 0f, 0f, -3f)
        Matrix.scaleM(modelTransform, 0, 0.1f, 0.1f, 0.1f)
        Matrix.rotateM(modelTransform, 0, 25f, 1f, 0f, 0f)
        modelTransform.toBuffer()
    }

    private val bottomCircleModelTransform = kotlin.run {
        val modelTransform = createIdentity4Matrix()
        Matrix.translateM(modelTransform, 0, 0f, -0.05f, -3f)
        Matrix.scaleM(modelTransform, 0, 0.1f, 0.1f, 0.1f)
        Matrix.rotateM(modelTransform, 0, 25f, 1f, 0f, 0f)
        modelTransform.toBuffer()
    }

    override fun onSurfaceChanged(width: Int, height: Int, program: ShaderProgram) {
        topCircle.onSurfaceChanged(width, height, program)
        circleSylinder.onSurfaceChanged(width, height, program)
        bottomCircle.onSurfaceChanged(width, height, program)
    }

    override fun draw(program: ShaderProgram) {
        program.updateUniform4f("u_Color", 1f, 0f, 0f, 1f)
        program.updateUniformMatrix4f("u_Model", topCircleModelTransform)
        topCircle.draw(program)
        program.updateUniform4f("u_Color", 1f, 1f, 0f, 1f)
        program.updateUniformMatrix4f("u_Model", sylinderModelTransform)
        circleSylinder.draw(program)
        program.updateUniform4f("u_Color", 1f, 0f, 0f, 1f)
        program.updateUniformMatrix4f("u_Model", bottomCircleModelTransform)
        bottomCircle.draw(program)
    }
}
