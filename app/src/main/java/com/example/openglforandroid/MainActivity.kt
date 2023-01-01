package com.example.openglforandroid

import android.app.Activity
import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.openglforandroid.hockey_game.AirHokeyGameRenderer

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupGLSurfaceView()
    }

    override fun onResume() {
        super.onResume()
        getGLSurfaceView().onResume()
    }

    override fun onPause() {
        super.onPause()
        getGLSurfaceView().onPause()
    }

    private fun setupGLSurfaceView() {
        val surfaceView = getGLSurfaceView()
        if (isSupportedOpenGL()) {
            surfaceView.setEGLContextClientVersion(2)
            val renderer = AirHokeyGameRenderer(this)
            surfaceView.setRenderer(renderer)
            surfaceView.setOnTouchListener { v, event ->
                Log.e("godgod", "onTouch")
                renderer.onTouchEvent(event)
            }
        } else {
            Toast.makeText(this, "OpenGL 2.0 버전이 지원되지 않는 기기입니다.", Toast.LENGTH_LONG).show()
        }
    }

    private fun isSupportedOpenGL(): Boolean {
        val activityManager = getSystemService(Activity.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo

        return configurationInfo.reqGlEsVersion >= 0x20000
    }

    private fun getGLSurfaceView(): GLSurfaceView {
        return findViewById(R.id.surface_view)
    }
}
