package com.example.androidstudio_koala_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidstudio_koala_template.ui.theme.AndroidStudioKoalaTemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidStudioKoalaTemplateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CombinedComponents(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun CombinedComponents(modifier: Modifier = Modifier) {
    // Estats compartits entre components
    val iconList = listOf(
        "Home" to Icons.Default.Home,
        "Star" to Icons.Default.Star,
        "Favorite" to Icons.Default.Favorite,
        "Info" to Icons.Default.Info,
        "LocationOn" to Icons.Default.LocationOn,
        "Edit" to Icons.Default.Edit
    )

    var selectedIcon by remember { mutableStateOf(iconList.first()) }
    var sliderValue by remember { mutableStateOf(50f) }
    var displayedIcon by remember { mutableStateOf(selectedIcon) }
    var displayedValue by remember { mutableStateOf(sliderValue.toInt()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
            IconDropdown(selectedIcon) { newIcon ->
                selectedIcon = newIcon
            }
            Spacer(modifier = Modifier.height(400.dp))

            CustomRangeSlider(sliderValue) { newValue ->
                sliderValue = newValue
            }
        }
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                displayedIcon = selectedIcon
                displayedValue = sliderValue.toInt()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp)
        ) {
            Text(text = "Enviar")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = displayedIcon.second,
                        contentDescription = displayedIcon.first,
                        tint = Color.Yellow,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = "${displayedValue}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


@Composable
fun IconDropdown(
    selectedIcon: Pair<String, ImageVector>,
    onIconSelected: (Pair<String, ImageVector>) -> Unit
) {
    val iconList = listOf(
        "Home" to Icons.Default.Home,
        "Star" to Icons.Default.Star,
        "Favorite" to Icons.Default.Favorite,
        "Info" to Icons.Default.Info,
        "LocationOn" to Icons.Default.LocationOn,
        "Edit" to Icons.Default.Edit

    )

    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = selectedIcon.second,
                contentDescription = selectedIcon.first,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = selectedIcon.first)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
        ) {
            iconList.forEach { icon ->
                DropdownMenuItem(
                    text = { Text(icon.first) },
                    onClick = {
                        onIconSelected(icon)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = icon.second,
                            contentDescription = icon.first
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun CustomRangeSlider(
    sliderValue: Float,
    onValueChange: (Float) -> Unit
) {
    var minNumber by remember { mutableStateOf("0") }
    var maxNumber by remember { mutableStateOf("100") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = minNumber,
                onValueChange = { if (it.toIntOrNull() != null) minNumber = it },
                label = { Text("Mínim") },
                modifier = Modifier.width(120.dp)
            )
            OutlinedTextField(
                value = maxNumber,
                onValueChange = { if (it.toIntOrNull() != null) maxNumber = it },
                label = { Text("Màxim") },
                modifier = Modifier.width(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val min = minNumber.toFloatOrNull() ?: 0f
        val max = maxNumber.toFloatOrNull() ?: 100f

        if (min < max) {
            Text(
                text = "Valor actual: ${sliderValue.toInt()}",
                style = MaterialTheme.typography.bodyMedium
            )
            Slider(
                value = sliderValue,
                onValueChange = onValueChange,
                valueRange = min..max,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = "El mínim ha de ser inferior al màxim.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CombinedPreview() {
    AndroidStudioKoalaTemplateTheme {
        CombinedComponents()
    }
}
