package com.example.openglforandroid

import android.app.Activity
import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.openglforandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val surfaceView: GLSurfaceView by lazy {
        GLSurfaceView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(surfaceView)
        setupGLSurfaceView()
    }

    override fun onResume() {
        super.onResume()
        surfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        surfaceView.onPause()
    }

    private fun setupGLSurfaceView() {
        if (isSupportedOpenGL()) {
            surfaceView.setEGLContextClientVersion(2)
            surfaceView.setRenderer(SurfaceRenderer())
        } else {
            Toast.makeText(this, "OpenGL 2.0 버전이 지원되지 않는 기기입니다.", Toast.LENGTH_LONG).show()
        }
    }

    private fun isSupportedOpenGL(): Boolean {
        val activityManager = getSystemService(Activity.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo

        return configurationInfo.reqGlEsVersion >= 0x20000
    }
}
