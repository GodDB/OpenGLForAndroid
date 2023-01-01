package com.example.openglforandroid.hockey_game

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import com.example.openglforandroid.R
import com.example.openglforandroid.base.ElementDrawer
import com.example.openglforandroid.glUtil.FileReader
import com.example.openglforandroid.glUtil.ShaderProgram
import com.example.openglforandroid.glUtil.Shader
import com.example.openglforandroid.glUtil.createIdentity4Matrix
import com.example.openglforandroid.glUtil.floatBufferOf
import com.example.openglforandroid.glUtil.runGL
import com.example.openglforandroid.glUtil.toBuffer
import com.example.openglforandroid.hockey_game.model.HockeyMallet
import com.example.openglforandroid.hockey_game.model.HockeyTable
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class AirHokeyGameRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private val program by lazy {
        runGL {
            val vertexShaderSourceCode = FileReader.readFile(context, R.raw.vertexshader)
            val fragmentShaderSourceCode = FileReader.readFile(context, R.raw.fragmentshader)
            ShaderProgram(
                vertexShader = Shader(vertexShaderSourceCode, Shader.Type.VERTEX),
                fragmentShader = Shader(fragmentShaderSourceCode, Shader.Type.FRAGMENT)
            )
        }
    }

    /** 이곳에 모델 추가 하시오. */
    private val models = listOf<ElementDrawer>(
        HockeyTable(context),
        HockeyMallet(context)
    )

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.e("godgod", "onSurfaceCreated")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.e("godgod", "onSurfaceChanged")
        runGL { GLES20.glViewport(0, 0, width, height) }
        program.bind()
        val cameraTransform = createIdentity4Matrix()
        val matrix = createIdentity4Matrix()
        Matrix.perspectiveM(
            matrix, // matrix
            0, // offset
            45.0f, // fovy
            width.toFloat() / height.toFloat(),
            2f, // near
            10f // far
        )
        program.updateUniformMatrix4f("u_Perspective", matrix.toBuffer())
        program.updateUniformMatrix4f("u_Camera", cameraTransform.toBuffer())

        models.forEach {
            it.onSurfaceChanged(width, height, program)
        }
        program.unbind()
    }

    override fun onDrawFrame(gl: GL10?) {
        // Log.e("godgod", "onDrawFrame")
        program.bind()
        runGL { GLES20.glClearColor(0f, 0f, 0f, 0f) }
        runGL { GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_COLOR_BUFFER_BIT) }
        models.forEach {
            it.draw(program)
        }
        program.unbind()
    }
}
