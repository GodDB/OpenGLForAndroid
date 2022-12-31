package com.example.openglforandroid.glUtil

import android.content.Context
import androidx.annotation.RawRes
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object FileReader {

    fun readFile(context : Context, @RawRes res : Int) : String {
        val body = StringBuilder()
        val inputStream : InputStream = context.resources.openRawResource(res)
        val inputStreamReader : InputStreamReader = InputStreamReader(inputStream)

        val bufferedReader : BufferedReader = BufferedReader(inputStreamReader)

        var nextLine : String? = null
        val reader = {
            nextLine = bufferedReader.readLine()
            nextLine
        }
        while (reader() != null) {
            body.append(nextLine)
            body.append("\n")
        }
        return body.toString()
    }
}
