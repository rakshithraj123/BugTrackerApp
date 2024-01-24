package com.app.bugtrackerapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.squareup.picasso.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@AndroidEntryPoint
open class MainActivity : ComponentActivity(){
    val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray,

                    ) {
                    var description by remember { mutableStateOf(TextFieldValue("")) }
                    Column(modifier = Modifier.padding(10.dp)) {

                        PhotoPickerDemoScreen()
                        Description(
                            description,
                            onValueChange = {
                                 description = it
                                 viewModel.description =  it.text
                                },
                        )
                        Submit {
                            runSheet()
                            description = TextFieldValue("")
                        }
                    }

                }
            }
        }
        handleSendIntent(intent)
    }

    @Composable
    private fun Submit(onSubmit: () -> Unit) {
        Button(
            onClick = {
                onSubmit()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor =  Color.Blue,
                contentColor = Color.White
            ),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            Text("Submit")
        }
    }


    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun Description(userNameText: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
        val keyboardController = LocalSoftwareKeyboardController.current
        BasicTextField(
            value = userNameText,
            singleLine = true,
            onValueChange = { onValueChange(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            textStyle = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .height(100.dp)
                        .background(Color.White)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {

                    if (userNameText.text.isEmpty()) {
                        HintText(getString(R.string.discription))
                    }
                    innerTextField()
                }
            },
            modifier = Modifier.padding(bottom = 10.dp)

        )
    }

    @Composable
    fun HintText(hintText: String) {
        Text(
            text = hintText,
            style = TextStyle(
                color = Color.Gray,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
            )
        )
    }

    fun Context.createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            externalCacheDir      /* directory */
        )
        return image
    }

    @Preview()
    @Composable
    fun PhotoPickerDemoScreen() {
        //The URI of the photo that the user has picked
        var photoUri: Uri? by remember { mutableStateOf(null) }
        val context = LocalContext.current
        val file = context.createImageFile()
        val takePictureUri = FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            BuildConfig.APPLICATION_ID + ".provider", file
        )

        //The launcher we will use for the PickVisualMedia contract.
        //When .launch()ed, this will display the photo picker.
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            //When the user has selected a photo, its URI is returned here
            photoUri = uri
            viewModel.imageUri = photoUri
        }
        val cameraLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { uri ->
                photoUri = takePictureUri
                viewModel.imageUri = photoUri
            }

        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                cameraLauncher.launch(takePictureUri)
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()) {


            Button(
                onClick = {
                    //On button press, launch the photo picker
                    launcher.launch(
                        PickVisualMediaRequest(
                        //Here we request only photos. Change this to .ImageAndVideo if you want videos too.
                        //Or use .VideoOnly if you only want videos.
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor =  Color.Blue,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .align(CenterHorizontally)
                    .padding(top = 40.dp)
            ) {
                Text("Select Photo")
            }

            Button(
                onClick = {
                    //On button press, launch the photo picker
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, "android.permission.CAMERA")
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(takePictureUri)
                    } else {
                        // Request a permission
                        permissionLauncher.launch("android.permission.CAMERA")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor =  Color.Blue,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .align(CenterHorizontally)
                    .padding(top = 40.dp)
            ) {
                Text("Take Photo")
            }

            if (photoUri != null) {
                //Use Coil to display the selected image
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = photoUri)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(6.0.dp, Color.Gray),
                    contentScale = ContentScale.Crop
                )
            }
        }

        val lifecycleOwner = LocalLifecycleOwner.current
        viewModel._sharedImage.observe(lifecycleOwner,{
            photoUri = it
            viewModel.imageUri = it
        })
    }
    fun runSheet() {
        GlobalScope.launch {
              viewModel.addBug()
        }
    }


    @Override
    override fun onNewIntent(intent : Intent) {
        super.onNewIntent(intent);
        handleSendIntent(intent);
    }

    private fun handleSendIntent(intent: Intent) {
        val action = intent.action
        val type = intent.type
        if (Intent.ACTION_SEND == action && type != null) {
            val imageUri : Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
            } else {
                intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
            }
            if (imageUri != null) {
               viewModel._sharedImage.postValue(imageUri)
            }
        }
    }

}