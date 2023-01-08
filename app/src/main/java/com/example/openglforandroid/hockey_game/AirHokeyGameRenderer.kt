package com.example.openglforandroid.hockey_game

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import android.view.MotionEvent
import com.example.openglforandroid.R
import com.example.openglforandroid.base.ElementDrawer
import com.example.openglforandroid.glUtil.Camera
import com.example.openglforandroid.glUtil.FileReader
import com.example.openglforandroid.glUtil.Shader
import com.example.openglforandroid.glUtil.ShaderProgram
import com.example.openglforandroid.glUtil.createIdentity4Matrix
import com.example.openglforandroid.glUtil.runGL
import com.example.openglforandroid.glUtil.toBuffer
import com.example.openglforandroid.hockey_game.model.HockeyMallet
import com.example.openglforandroid.hockey_game.model.HockeyTable
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class AirHokeyGameRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private val tableProgram by lazy {
        runGL {
            val vertexShaderSourceCode = FileReader.readFile(context, R.raw.vertexshader)
            val fragmentShaderSourceCode = FileReader.readFile(context, R.raw.fragmentshader)
            ShaderProgram(
                vertexShader = Shader(vertexShaderSourceCode, Shader.Type.VERTEX),
                fragmentShader = Shader(fragmentShaderSourceCode, Shader.Type.FRAGMENT)
            )
        }
    }

    private val malletProgram by lazy {
        runGL {
            val vertexShaderSourceCode = FileReader.readFile(context, R.raw.mallet_vertexshader)
            val fragmentShaderSourceCode = FileReader.readFile(context, R.raw.mallet_fragmentshader)
            ShaderProgram(
                vertexShader = Shader(vertexShaderSourceCode, Shader.Type.VERTEX),
                fragmentShader = Shader(fragmentShaderSourceCode, Shader.Type.FRAGMENT)
            )
        }
    }

    /** 이곳에 모델 추가 하시오. */
    private val models by lazy {
        listOf<Pair<ElementDrawer, ShaderProgram>>(
            HockeyTable(context) to tableProgram,
            HockeyMallet(context) to malletProgram
        )
    }

    private val camera = Camera()

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.e("godgod", "onSurfaceCreated")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.e("godgod", "onSurfaceChanged")
        runGL { GLES20.glViewport(0, 0, width, height) }
        runGL { GLES20.glEnable(GL10.GL_CULL_FACE) } // 벡터 외적이 후면을 바라보는 부분 제거
        runGL { GLES20.glEnable(GL10.GL_DEPTH_TEST) } // z버퍼 생성
        camera.setScreenWidth(width.toFloat())
        camera.setScreenHeight(height.toFloat())
        val perspectiveMatrix = createIdentity4Matrix()
        Matrix.perspectiveM(
            perspectiveMatrix, // matrix
            0, // offset
            45.0f, // fovy
            width.toFloat() / height.toFloat(),
            2f, // near
            10f // far
        )
        models.forEach {
            val (drawer: ElementDrawer, program: ShaderProgram) = it
            program.bind()
            program.updateUniformMatrix4f("u_Perspective", perspectiveMatrix.toBuffer())
            drawer.onSurfaceChanged(width, height, program)
            program.unbind()
        }
    }

    override fun onDrawFrame(gl: GL10?) {
        // Log.e("godgod", "onDrawFrame")
        runGL { GLES20.glClearColor(0f, 0f, 0f, 0f) }
        runGL { GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_COLOR_BUFFER_BIT) }
        models.forEach {
            val (drawer: ElementDrawer, program: ShaderProgram) = it
            program.bind()
            program.updateUniformMatrix4f("u_Camera", camera.getMatrix())
            drawer.draw(program)
            program.unbind()
        }
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        camera.onTouchEvent(event)
        return true
    }
}
