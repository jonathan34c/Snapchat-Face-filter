package com.chia.cheng.arapplication

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.chia.cheng.arapplication.databinding.ActivityMainBinding
import com.chia.cheng.facefilter.ARData
import com.chia.cheng.facefilter.ArFilterBuilder
import com.google.ar.core.AugmentedFace
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
       var button = findViewById<Button>(R.id.launcbtn)
        button.setOnClickListener {
//            var drawable1 = resources.getDrawable(R.drawable.bicycle_face)
//            val bitmap1 = (drawable1 as BitmapDrawable).bitmap
//            var parcel1= Parcel.obtain()
//            parcel1.writeParcelable(bitmap1, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
//
//            var drawable2 = resources.getDrawable(R.drawable.fox_face_mesh_texture)
//            val bitmap2 = (drawable2 as BitmapDrawable).bitmap
//            var parcel2= Parcel.obtain()
//            parcel2.writeParcelable(bitmap2, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
//
//            var drawable3 = resources.getDrawable(R.drawable.anony_face)
//            val bitmap3 = (drawable3 as BitmapDrawable).bitmap
//            var parcel3= Parcel.obtain()
//            parcel3.writeParcelable(bitmap3, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
//
            var list = arrayOf("bicycle_face", "fox_face_mesh_texture","anony_face" )

            val builder = ArFilterBuilder();

            builder.start(this ,"com.chia.cheng.arapplication", "drawable", list)
        }
    }

}
