package com.chia.cheng.arapplication

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.chia.cheng.arapplication.databinding.ActivityMainBinding
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

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var modelRenderabe: ModelRenderable? = null
    private var currentListener: Scene.OnUpdateListener? = null
    private var texture: Texture? = null
    private var isAdded = false
    private var faceNodeMap: HashMap<AugmentedFace, AugmentedFaceNode> = HashMap()
    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView
    private var faceNode : AugmentedFaceNode? = null
    private lateinit var  customArFragment: FilterFragment
    private var sceneView: ArSceneView ?=null
    private var scene : Scene ?=null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val modelLink =
            "https://github.com/yudiz-solutions/runtime_ar_android/raw/master/model/model.gltf"
        val modelLink2 =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf"
        customArFragment =
            supportFragmentManager.findFragmentById(R.id.arFragment) as FilterFragment
        img1 = findViewById(R.id.img1)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)
        img1.setOnClickListener {
            //removeNode()
           // addObject(Uri.parse(modelLink), null)
            //addNodeToScene()
            faceNodeMap.clear()
            try {
                faceNode?.setParent(null)
            }catch (e: Exception){

            }
            swapFace(R.drawable.anony_face)
        }
        img2.setOnClickListener {
           // removeNode()
            //addObject(Uri.parse(modelLink2),null, 0.1f)
            // addNodeToScene()
            faceNodeMap.clear()
            try {
                faceNode?.setParent(null)
            }catch (e: Exception){

            }
            swapFace(R.drawable.bicycle_face)
        }
        img3.setOnClickListener {
            faceNodeMap.clear()
            try {
                faceNode?.setParent(null)
            }catch (e: Exception){

            }
            swapFace(R.drawable.fox_face_mesh_texture)
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

    private fun swapFace(resource: Int){
        Texture.builder()
            .setSource(this, resource)
            .build()
            .thenAccept {
                texture = it
            }.exceptionally {
                Toast.makeText(this, "error loading model", Toast.LENGTH_SHORT).show()
                return@exceptionally null
            }
    }

    private fun addObject(uri: Uri?, drawableRes: Int?, scale: Float?=null){
        removeNode()
        if(uri!=null){
            ModelRenderable.builder()
                .setSource(
                    this, RenderableSource.builder().setSource(
                        this,
                        uri,
                        RenderableSource.SourceType.GLTF2
                    )
                        .setScale(scale?:0.5f).build()
                )
                .setRegistryId(uri.toString())
                .build()
                .thenAccept {
                    this.modelRenderabe = it
                    this.modelRenderabe?.isShadowCaster = false
                    this.modelRenderabe?.isShadowReceiver = false
                }.exceptionally {
                    Toast.makeText(this, "error loading model", Toast.LENGTH_SHORT).show()
                    return@exceptionally null
                }
        }
        if(drawableRes!=null){
            ModelRenderable.builder()
            .setSource(this, R.raw.fox_face)
            .build()
            .thenAccept {
                this.modelRenderabe = it
                this.modelRenderabe?.isShadowCaster = false
                this.modelRenderabe?.isShadowReceiver = false
            }.exceptionally {
                Toast.makeText(this, "error loading model", Toast.LENGTH_SHORT).show()
                return@exceptionally null
            }
        }



    }

    private fun addNodeToScene(){
        customArFragment.arSceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        customArFragment.arSceneView.scene.addOnUpdateListener { frameTime ->
            if(modelRenderabe == null || texture ==null ){
                return@addOnUpdateListener
            }
            var frame = customArFragment.arSceneView.arFrame;
            assert(frame!=null)
            val augmentedFaces = frame?.getUpdatedTrackables(AugmentedFace::class.java)?:return@addOnUpdateListener
            for(augmentFace in augmentedFaces){
                if(isAdded) return@addOnUpdateListener
                val augmentedFaceMode = AugmentedFaceNode(augmentFace)
                augmentedFaceMode.setParent(customArFragment.arSceneView.scene)
                augmentedFaceMode.faceRegionsRenderable = modelRenderabe

                faceNodeMap[augmentFace] = augmentedFaceMode
                isAdded = true


                // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
                val iterator: MutableIterator<Map.Entry<AugmentedFace, AugmentedFaceNode>> =
                    faceNodeMap.entries.iterator()
                val (face, node) = iterator.next()
                while (face.trackingState == TrackingState.STOPPED) {
                    node.setParent(null)
                    iterator.remove()
                }
            }
        }
    }

    private fun removeNode() {
        var frame = customArFragment.arSceneView.arFrame;
        assert(frame!=null)
        val augmentedFaces = frame?.getUpdatedTrackables(AugmentedFace::class.java)?:return
        for(augmentFace in augmentedFaces){
            // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
            for ((face, node) in faceNodeMap) {
                customArFragment.arSceneView.scene.removeChild(node);
                node.setParent(null)
                node.renderable = null
            }
        }
//        customArFragment.arSceneView.scene.addOnUpdateListener(null)
        customArFragment.arSceneView.scene.removeOnUpdateListener {
            customArFragment.onUpdate(it)
        }
        modelRenderabe = null
        isAdded = false

    }

}
