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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import io.github.sceneview.rememberOnGestureListener
import stream.techygrrrl.magic8ballrrr.ui.theme.CMYKDark100
import stream.techygrrrl.magic8ballrrr.ui.theme.CMYKPurrrple
import stream.techygrrrl.magic8ballrrr.ui.theme.Magic8BallrrrTheme
import techygrrrl.magic8ballrrr.Magic8Ballrrr

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val magic8Ballrrr = Magic8Ballrrr()

        setContent {
            Magic8BallrrrTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // State
                    var emojiText by remember { mutableStateOf("🎱") }
                    var answerText by remember {
                        mutableStateOf("Fling the magic 8-ball to answer your burning questions")
                    }

                    // SceneView
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
                        onGestureListener = rememberOnGestureListener(
                            onFling = { _, _, _, _ ->
                                val answer = magic8Ballrrr.ask("")

                                emojiText = answer.emoji
                                answerText = answer.text
                            }
                        ),
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
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        CMYKDark100,
                                        CMYKPurrrple,
                                    )
                                )
                            )
//                            .background(CMYKDark100)
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center),
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 8.dp,
                                    )
                                    .fillMaxWidth(),
                                color = Color.White,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                text = emojiText
                            )
                            Text(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 8.dp,
                                    )
                                    .fillMaxWidth(),
                                color = Color.White,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                text = answerText
                            )
                        }

                    }
                }
            }
        }
    }
}
