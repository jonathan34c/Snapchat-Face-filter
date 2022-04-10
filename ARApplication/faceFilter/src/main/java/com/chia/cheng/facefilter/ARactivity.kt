package com.chia.cheng.facefilter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.chia.cheng.facefilter.databinding.ActivityAractivityBinding
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.AugmentedFaceNode


class ARactivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAractivityBinding

    private var texture: Texture? = null
    private var faceNodeMap: HashMap<AugmentedFace, AugmentedFaceNode> = HashMap()
    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView
    private var faceNode : AugmentedFaceNode? = null
    private lateinit var  customArFragment: FilterFragment
    private var sceneView: ArSceneView?=null
    private var scene : Scene?=null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_aractivity)

        customArFragment =
            supportFragmentManager.findFragmentById(R.id.arFragment) as FilterFragment
        val linearView = findViewById<LinearLayout>(R.id.arlist)
        val list = intent.getStringArrayExtra("list")
        val packageName = intent.getStringExtra("packageName")
        val resType = intent.getStringExtra("resType")
        if(list == null){
            finish()
        }else{
            for(item in list){
                val view = layoutInflater.inflate(R.layout.view_btn, null)
                val id = resources.getIdentifier(item,resType, packageName)
                val drawable = resources.getDrawable(id)

                if(drawable==null){
                    continue
                }else{
                    val bitmap = (drawable as BitmapDrawable).bitmap
                    val imageView = view.findViewById<ImageView>(R.id.img_btn)
                    imageView.setImageDrawable(drawable)
                    view.setOnClickListener {
                        faceNodeMap.clear()
                        try {
                            faceNode?.setParent(null)
                        }catch (e: Exception){

                        }
                        swapFace(bitmap)
                    }
                    linearView.addView(view)
                }

            }
        }




        sceneView = customArFragment.arSceneView
        sceneView?.cameraStreamRenderPriority= Renderable.RENDER_PRIORITY_FIRST
        scene =sceneView?.scene

        scene?.addOnUpdateListener {frametime->
            if(texture == null){
                return@addOnUpdateListener
            }
            var faceList = sceneView?.session?.getAllTrackables(AugmentedFace::class.java)?:return@addOnUpdateListener
            for(face in faceList){
                if(!faceNodeMap.containsKey(face)){
                    faceNode = AugmentedFaceNode(face)
                    faceNode?.setParent(scene)
                    faceNode?.faceMeshTexture = texture
                    faceNodeMap.put(face, faceNode!!)
                }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun swapFace(drawable: Bitmap){
        Texture.builder()
            .setSource(drawable)
            .build()
            .thenAccept {
                texture = it
            }.exceptionally {
                Toast.makeText(this, "error loading model", Toast.LENGTH_SHORT).show()
                return@exceptionally null
            }
    }
}