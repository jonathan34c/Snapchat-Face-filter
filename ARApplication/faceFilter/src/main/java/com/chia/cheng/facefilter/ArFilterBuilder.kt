package com.chia.cheng.facefilter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Parcel
import java.io.Serializable

class ArFilterBuilder {
    constructor()

    fun start(context: Context, packageName: String, resType: String, list: Array<String>){
        if(list ==null){
            return
        }
        var intent = Intent(context, ARactivity::class.java)
        intent.putExtra("list", list)
        intent.putExtra("packageName", packageName)
        intent.putExtra("resType", resType)
        context.startActivity(intent)
    }
}


class ARData :Serializable{
    var drawble: Drawable?=null
    var name: String?=null
    constructor(drawable: Drawable)
}