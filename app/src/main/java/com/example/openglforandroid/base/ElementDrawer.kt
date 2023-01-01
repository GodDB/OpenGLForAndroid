package com.example.openglforandroid.base

import com.example.openglforandroid.glUtil.ShaderProgram

interface ElementDrawer {
    fun onSurfaceChanged(width: Int, height: Int, program: ShaderProgram)
    fun draw(program: ShaderProgram)
}
