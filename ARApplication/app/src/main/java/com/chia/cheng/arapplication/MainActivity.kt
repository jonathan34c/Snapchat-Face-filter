package com.chia.cheng.arapplication

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.chia.cheng.arapplication.databinding.ActivityMainBinding
import com.google.ar.core.AugmentedFace
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.AugmentedFaceNode

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var modelRenderabe: ModelRenderable? = null
    private var texture: Texture? = null
    private  var isAdded = false
    private var faceNodeMap : HashMap<AugmentedFace, AugmentedFaceNode> = HashMap();

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       setContentView(R.layout.activity_main)
        val customArFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as FilterFragment
        ModelRenderable.builder()
            .setSource(this, R.raw.fox_face)
            .build()
            .thenAccept {
                this.modelRenderabe = it
                this.modelRenderabe?.isShadowCaster= false
                this.modelRenderabe?.isShadowReceiver = false
            }.exceptionally {
                Toast.makeText(this, "error loading model", Toast.LENGTH_SHORT).show()
                return@exceptionally null
            }

        Texture.builder()
            .setSource(this, R.drawable.fox_face_mesh_texture)
            .build()
            .thenAccept{
                this.texture = it
            }.exceptionally {
                Toast.makeText(this, "error loading model", Toast.LENGTH_SHORT).show()
                return@exceptionally null
            }

        customArFragment.arSceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        customArFragment.arSceneView.scene.addOnUpdateListener { frameTime ->
            if(modelRenderabe == null || texture ==null ){
                return@addOnUpdateListener
            }
            var frame = customArFragment.arSceneView.arFrame;
            assert(frame!=null)
            val augmentedFaces = frame!!.getUpdatedTrackables(AugmentedFace::class.java)
            for(augmentFace in augmentedFaces){
                if(isAdded) return@addOnUpdateListener
                val augmentedFaceMode = AugmentedFaceNode(augmentFace)
                augmentedFaceMode.setParent(customArFragment.arSceneView.scene)
                augmentedFaceMode.faceRegionsRenderable = modelRenderabe
                augmentedFaceMode.faceMeshTexture = texture
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
}