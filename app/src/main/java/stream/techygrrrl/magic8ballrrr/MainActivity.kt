package stream.techygrrrl.magic8ballrrr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import stream.techygrrrl.magic8ballrrr.ui.theme.CMYKDark100
import stream.techygrrrl.magic8ballrrr.ui.theme.CMYKPurrrple
import stream.techygrrrl.magic8ballrrr.ui.theme.Magic8BallrrrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            Magic8BallrrrTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val engine = rememberEngine()
                    val modelLoader = rememberModelLoader(engine)
                    val environmentLoader = rememberEnvironmentLoader(engine)

                    val cameraNode = rememberCameraNode(engine).apply {
                        position = Position(
                            z = 4.0f
                        )
                    }
                    val centerNode = rememberNode(engine).addChildNode(cameraNode)
                    /*
                    val cameraTransition = rememberInfiniteTransition(label = "CameraTransition")
                    val cameraRotation by cameraTransition.animateRotation(
                        initialValue = Rotation(y = 0.0f),
                        targetValue = Rotation(y = 360.0f),
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 7.seconds.toInt(DurationUnit.MILLISECONDS))
                        )
                    )
                    */

                    Scene(
                        modifier = Modifier
                            .fillMaxHeight(0.50f)
                            .fillMaxWidth(),
                        engine = engine,
                        modelLoader = modelLoader,
                        cameraNode = cameraNode,
                        childNodes = listOf(
                            centerNode,
                            rememberNode {
                                ModelNode(
                                    modelInstance = modelLoader.createModelInstance(
                                        assetFileLocation = "models/eight_ball.glb"
                                    ),
                                    centerOrigin = Position(
                                        y = -0.50f,
                                    ),
                                    scaleToUnits = 0.40f
                                )
                            }
                        ),
                        environment = environmentLoader.createHDREnvironment(
                            assetFileLocation = "environments/gradient.hdr"
                        )!!,
                        /*onFrame = {
                            centerNode.rotation = cameraRotation
                            cameraNode.lookAt(centerNode)
                        }*/
                    )

                    Box(
                        modifier = Modifier
                            .background(Brush.verticalGradient(listOf(
                                CMYKDark100,
                                CMYKPurrrple,
                            )))
//                            .background(CMYKDark100)
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        Text(
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .align(Alignment.Center),
                            text = "Hello, world!"
                        )
                    }
                }
            }
        }
    }
}
