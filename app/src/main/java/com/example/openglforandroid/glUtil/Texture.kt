package com.example.openglforandroid.glUtil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import androidx.annotation.DrawableRes
import com.example.openglforandroid.base.GLESBufferBinder

class Texture(context: Context, @DrawableRes imageRes: Int) : GLESBufferBinder{

    val bufferId: Int

    init {
        bufferId = genTextureBuffer()
        val bitmap = genBitmap(context, imageRes)
        loadTextureBuffer(bufferId, bitmap)
    }

    private fun genTextureBuffer(): Int {
        // textureBuffer 생성
        val textureObjectIds = IntArray(1)
        runGL { GLES20.glGenTextures(1, textureObjectIds, 0) }
        if (textureObjectIds.first() == 0) {
            throw java.lang.RuntimeException("not generated texture")
        }
        return textureObjectIds.first()
    }

    private fun genBitmap(context: Context, imageRes: Int): Bitmap {
        val bitmapOptions = BitmapFactory.Options().apply {
            this.inScaled = false // 원본 이미지로 달라!
        }

        return BitmapFactory.decodeResource(context.resources, imageRes, bitmapOptions) ?: throw java.lang.RuntimeException("not generated bitmap")
    }

    private fun loadTextureBuffer(bufferId: Int, bitmap: Bitmap) {
        // texture buffer activate
        runGL { GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bufferId) }
        // texture 옵션 지정 (mipmap)
        runGL { GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR) }
        runGL { GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR) }
        runGL { GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT) }
        runGL { GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT) }
        // texture buffer에 bitmap load
        runGL { GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0) }
        // bitmap release (native memory 제거 및 gc로 부터 제거되라고)
        bitmap.recycle()
        // mipmap 생성
        runGL { GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D) }
        // texture 해제 (로드 완료됬으므로 해제)
        unbind()
    }

    override fun bind() {
        runGL { GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bufferId) }
    }

    override fun unbind() {
        runGL { GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0) }
    }
}
