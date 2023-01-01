package com.example.openglforandroid.hockey_game

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import com.example.openglforandroid.R
import com.example.openglforandroid.base.ElementDrawer
import com.example.openglforandroid.glUtil.FileReader
import com.example.openglforandroid.glUtil.ShaderProgram
import com.example.openglforandroid.glUtil.Shader
import com.example.openglforandroid.glUtil.floatBufferOf
import com.example.openglforandroid.glUtil.runGL
import com.example.openglforandroid.hockey_game.model.HockeyMallet
import com.example.openglforandroid.hockey_game.model.HockeyTable
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class AirHokeyGameRenderer(context: Context) : GLSurfaceView.Renderer {

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
        HockeyTable(),
        HockeyMallet()
    )

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.e("godgod", "onSurfaceCreated")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.e("godgod", "onSurfaceChanged")
        runGL { GLES20.glViewport(0, 0, width, height) }
        program
    }

    override fun onDrawFrame(gl: GL10?) {
        // Log.e("godgod", "onDrawFrame")
        runGL { GLES20.glClearColor(0f, 0f, 0f, 0f) }
        runGL { GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_COLOR_BUFFER_BIT) }
        program.activate()
        models.forEach {
            it.draw(program)
        }
    }
}
