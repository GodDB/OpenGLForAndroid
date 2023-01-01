package com.example.openglforandroid.glUtil

import android.opengl.Matrix
import android.util.Log
import android.view.MotionEvent
import java.nio.FloatBuffer

class Camera() {
    private val matrix = createIdentity4Matrix()

    private var pos: Vector3D = Vector3D.createZero()
    val posX: Float
        get() = pos.x
    val posY: Float
        get() = pos.y
    val posZ: Float
        get() = pos.z

    private var up: Vector3D = Vector3D(0f, 1f, 0f)
    val upX: Float
        get() = up.x
    val upY: Float
        get() = up.y
    val upZ: Float
        get() = up.z

    private var front: Vector3D = Vector3D(0f, 0f, -1f)
    val frontX: Float
        get() = front.x
    val frontY: Float
        get() = front.y
    val frontZ: Float
        get() = front.z

    private var pressedX: Float = 0f
    private var pressedY: Float = 0f

    private var width: Float = 0f
    private var height: Float = 0f

    private val _at: Vector3D = Vector3D.createZero()
    private val at: Vector3D
        get() = _at.apply {
            x = pos.x + front.x
            y = pos.y + front.y
            z = pos.z + front.z
        }

    fun setScreenWidth(width: Float) {
        this.width = width
    }

    fun setScreenHeight(height: Float) {
        this.height = height
    }

    fun updatePosition(x: Float, y: Float, z: Float) {
        pos.apply {
            this.x = x
            this.y = y
            this.z = z
        }
    }


    fun updateUp(x: Float, y: Float, z: Float) {
        up.apply {
            this.x = x
            this.y = y
            this.z = z
        }
    }

    fun updateFront(x: Float, y: Float, z: Float) {
        front.apply {
            this.x = x
            this.y = y
            this.z = z
        }
    }

    fun getMatrix(): FloatBuffer {
        Matrix.setIdentityM(matrix, 0)
        Matrix.setLookAtM(
            matrix, // matrix
            0, // offset
            posX, // eyeX
            posY, // eyeY
            posZ, // eyeZ
            at.x, // centerX
            at.y, // centerY
            at.z, // centerZ
            upX, // upX
            upY, // upY
            upZ // upZ
        )
        return matrix.toBuffer()
    }

    fun onTouchEvent(event: MotionEvent) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                pressedX = event.rawX
                pressedY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = event.rawX - pressedX
                val deltaY = event.rawY - pressedY
                pos.x -= (deltaX / width)
                pos.y += (deltaY / height)
                pressedX = event.rawX
                pressedY = event.rawY
                Log.e("godgod", "$deltaX  ${deltaY}  ${pos.x}  ${pos.y}")
            }
            MotionEvent.ACTION_UP -> {
                pressedX = 0f
                pressedY = 0f
            }
            MotionEvent.ACTION_CANCEL -> {
                pressedX = 0f
                pressedY = 0f
            }
        }
    }
}
