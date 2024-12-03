package gaur.himanshu.apikeyencryption

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gaur.himanshu.apikeyencryption.remote.CustomMessage
import gaur.himanshu.apikeyencryption.remote.RetrofitInstance
import gaur.himanshu.apikeyencryption.ui.theme.ApiKeyEncryptionTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val MY_IP = ""

class MainActivity : ComponentActivity() {

    private val apiService by lazy { RetrofitInstance(this).apiService }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiKeyEncryptionTheme {
                val scope = rememberCoroutineScope()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LaunchedEffect(key1 = Unit) {
                        scope.launch(Dispatchers.IO) {
                            Log.d("TAGGGGGGGG", "onCreate: ${apiService.connect()}")
                        }
                    }
                    Greeting(
                        name = "Himanshu",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApiKeyEncryptionTheme {
        Greeting("Android")
    }
}