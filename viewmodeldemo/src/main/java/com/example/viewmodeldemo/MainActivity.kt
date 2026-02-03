package com.example.viewmodeldemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodeldemo.ui.theme.ViewModelDemoTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewModelDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScreenSetup()
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(viewModel: DemoViewModel = viewModel()) {
    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        inputText = viewModel.inputText,
        result = viewModel.result,
        onTextChange = { viewModel.inputText = it },
        convertTemp = { viewModel.convertTemp() },
        switchChange = { viewModel.switchChange() }
    )
}

@Composable
fun MainScreen(
    isFahrenheit: Boolean,
    inputText: String,
    result: String,
    onTextChange: (String) -> Unit,
    convertTemp: () -> Unit,
    switchChange: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Temperature Converter",
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        InputRow(
            isFahrenheit = isFahrenheit,
            textState = inputText,
            onTextChange = onTextChange,
            switchChange = switchChange
        )

        Text(
            result,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        Button(onClick = convertTemp) {
            Text("Convert Temperature")
        }
    }
}

@Composable
fun InputRow(
    isFahrenheit: Boolean,
    textState: String,
    onTextChange: (String) -> Unit,
    switchChange: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = isFahrenheit,
            onCheckedChange = { switchChange() }
        )

        OutlinedTextField(
            value = textState,
            onValueChange = onTextChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            label = { Text("Enter temperature") },
            modifier = Modifier.padding(10.dp),
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_ac_unit_24),
                    contentDescription = "frost",
                    modifier = Modifier.size(40.dp)
                )
            }
        )

        Crossfade(
            targetState = isFahrenheit,
            animationSpec = tween(2000)
        ) { visible ->
            when (visible) {
                true -> Text("\u2109", style = MaterialTheme.typography.headlineSmall)
                false -> Text("\u2103", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview(model: DemoViewModel = viewModel()) {
    ViewModelDemoTheme {
        MainScreen(
            isFahrenheit = model.isFahrenheit,
            inputText = model.inputText,
            result = model.result,
            onTextChange = { model.inputText = it },
            convertTemp = { model.convertTemp() },
            switchChange = { model.switchChange() }
        )
    }
}