package io.github.sergeyboboshko.ceppb.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.sergeyboboshko.composeentity.daemons.ColorPalette
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import kotlin.math.roundToInt
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.gson.Gson
import io.github.sergeyboboshko.ceppb.R
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppSettings() {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    // Варіанти, які кольори можна змінювати
    val options = listOf(
        "text", "primary", "secondary", "background", "button",
        "buttonText", "selectedBackground"
    )

    var selectedKey by remember { mutableStateOf("background") }

    // Поточна палітра

    var currentPalette = GlobalColors.currentPalette
    LaunchedEffect(Unit) {
        val cpalette = loadPalette(context)
        if (cpalette!=null){
            GlobalColors.currentPalette = cpalette
        }
    }
    // Дістаємо поточний колір залежно від вибору
    val currentColor = remember(selectedKey) {
        when (selectedKey) {
            "text" -> currentPalette.text
            "primary" -> currentPalette.primary
            "secondary" -> currentPalette.secondary
            "background" -> currentPalette.background
            "button" -> currentPalette.button
            "buttonText" -> currentPalette.buttonText
            "selectedBackground" -> currentPalette.selectedBackground
            else -> Color.Gray
        }
    }

    // Стани повзунків
    var red by remember { mutableStateOf(currentColor.red * 255f) }
    var green by remember { mutableStateOf(currentColor.green * 255f) }
    var blue by remember { mutableStateOf(currentColor.blue * 255f) }
    var useWhenAppOpening by remember { mutableStateOf(isUseCustomPalettePreference(context)) }

    // Новий колір
    val newColor = Color(red / 255f, green / 255f, blue / 255f)

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text("🎨 ${stringResource(R.string.whatAdjasting)}:")
        }
        item {
            FlowRow {
                // Вибір елементу палітри

                options.forEach { key ->
                    StyledButton(
                        onClick = {
                            selectedKey = key
                            val col = when (key) {
                                "text" -> currentPalette.text
                                "primary" -> currentPalette.primary
                                "secondary" -> currentPalette.secondary
                                "background" -> currentPalette.background
                                "button" -> currentPalette.button
                                "buttonText" -> currentPalette.buttonText
                                "selectedBackground" -> currentPalette.selectedBackground
                                else -> Color.Gray
                            }
                            red = col.red * 255f
                            green = col.green * 255f
                            blue = col.blue * 255f
                        },
                        modifier = Modifier.weight(1f),
                        backgroundColor = if (selectedKey == key)
                            Color.Yellow
                        else
                            GlobalColors.currentPalette.button // <--- ✅ тут динамічний фон
                    ) {
                        Text(
                            text = key,
                            color = GlobalColors.currentPalette.buttonText // 🔸 ще й текст можна зробити залежним
                        )
                    }
                }
            }

        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = useWhenAppOpening,
                    onCheckedChange = {
                        useWhenAppOpening = it
                        saveUseCustomPalettePreference(context, it)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp)) // для відступу між прапорцем і текстом
                Text("Use when application starting")
            }

        }
        item {
            Text("🔴 Red: ${red.roundToInt()}")
            Slider(
                value = red, onValueChange = { red = it }, valueRange = 0f..255f,
                onValueChangeFinished = {
                    applyNewColorToPalette(selectedKey, newColor)
                    //onColorChanged(selectedKey, newColor)
                    savePalette(context, GlobalColors.currentPalette)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Text("🟢 Green: ${green.roundToInt()}")
            Slider(
                value = green, onValueChange = { green = it }, valueRange = 0f..255f,
                onValueChangeFinished = {
                    applyNewColorToPalette(selectedKey, newColor)
                    savePalette(context, GlobalColors.currentPalette)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Text("🔵 Blue: ${blue.roundToInt()}")
            Slider(
                value = blue, onValueChange = { blue = it }, valueRange = 0f..255f,
                onValueChangeFinished = {
                    applyNewColorToPalette(selectedKey, newColor)
                    savePalette(context, GlobalColors.currentPalette)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            FlowRow {
                Card(
                    Modifier
                        .background(GlobalColors.currentPalette.background)
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Preview Text",
                        modifier = Modifier.background(GlobalColors.currentPalette.background),
                        color = GlobalColors.currentPalette.text
                    )
                }

                Card(
                    Modifier
                        .background(GlobalColors.currentPalette.background)
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Preview Background",
                        modifier = Modifier.background(GlobalColors.currentPalette.background),
                        color = GlobalColors.currentPalette.text
                    )
                }

                Card(
                    Modifier
                        .background(GlobalColors.currentPalette.selectedBackground)
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Preview Selected",
                        modifier = Modifier.background(GlobalColors.currentPalette.selectedBackground),
                        color = GlobalColors.currentPalette.text
                    )
                }
            }
        }
    }

    /////////////////////////////////////////////////


//    var saveJob by remember { mutableStateOf<Job?>(null) }
//
//    // Коли колір змінюється — оновлюємо локальну палітру і запускаємо debounce збереження
//    fun onColorChanged(key: String, color: Color) {
//        // Застосовуємо палітру миттєво (навіть якщо прапорець вимкнений)
//        applyNewColorToPalette(key, color)
//
//        //// Застосовуємо палітру миттєво (навіть якщо прапорець вимкнений)
//        //GlobalColors.customizePalette(localPalette)
//
//        // Відклали збереження на 700 мс (debounce)
//        saveJob?.cancel()
//        saveJob = scope.launch {
//            delay(700)
//            savePalette(context, GlobalColors.currentPalette)
//        }
//    }
}

val PREF_NAME = "color_prefs"
val PALETTE_KEY = "custom_palette"
private const val USE_CUSTOM_PALETTE_KEY = "use_custom_palette"

fun savePalette(context: Context, palette: ColorPalette) {
    val dto = palette.toDTO()
    val json = Gson().toJson(dto)
    context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(PALETTE_KEY, json)
        .apply()
}

fun loadPalette(context: Context): ColorPalette? {
    val json = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        .getString(PALETTE_KEY, null) ?: return null
    val dto = Gson().fromJson(json, ColorPaletteDTO::class.java)
    return dto.toColorPalette()
}

fun applyNewColorToPalette(key: String, color: Color) {
    val p = GlobalColors.currentPalette
    val newPalette = ColorPalette(
        primary = if (key == "primary") color else p.primary,
        secondary = if (key == "secondary") color else p.secondary,
        background = if (key == "background") color else p.background,
        selectedBackground = if (key == "selectedBackground") color else p.selectedBackground,
        text = if (key == "text") color else p.text,
        button = if (key == "button") color else p.button,
        buttonText = if (key == "buttonText") color else p.buttonText,
        captionFonColor = p.captionFonColor,
        editBackgroundColor = p.editBackgroundColor,
        topAreaTextColor = p.topAreaTextColor,
        topAreaBackgroundColor = p.topAreaBackgroundColor,
        bottomAreaTextColor = p.bottomAreaTextColor,
        bottomAreaBackgroundColor = p.bottomAreaBackgroundColor
    )

    GlobalColors.customizePalette(newPalette)
}

//the class adapter for my prreferences
@Serializable
data class ColorPaletteDTO(
    val primary: Long,
    val secondary: Long,
    val background: Long,
    val selectedBackground: Long,
    val text: Long,
    val button: Long,
    val buttonText: Long,
    val captionFonColor: Long,
    val editBackgroundColor: Long,
    val topAreaTextColor: Long,
    val topAreaBackgroundColor: Long,
    val bottomAreaTextColor: Long,
    val bottomAreaBackgroundColor: Long
)


fun ColorPalette.toDTO(): ColorPaletteDTO = ColorPaletteDTO(
    primary.value.toLong(),
    secondary.value.toLong(),
    background.value.toLong(),
    selectedBackground.value.toLong(),
    text.value.toLong(),
    button.value.toLong(),
    buttonText.value.toLong(),
    captionFonColor.value.toLong(),
    editBackgroundColor.value.toLong(),
    topAreaTextColor.value.toLong(),
    topAreaBackgroundColor.value.toLong(),
    bottomAreaTextColor.value.toLong(),
    bottomAreaBackgroundColor.value.toLong()
)

fun ColorPaletteDTO.toColorPalette(): ColorPalette = ColorPalette(
    primary = Color(primary.toULong()),
    secondary = Color(secondary.toULong()),
    background = Color(background.toULong()),
    selectedBackground = Color(selectedBackground.toULong()),
    text = Color(text.toULong()),
    button = Color(button.toULong()),
    buttonText = Color(buttonText.toULong()),
    captionFonColor = Color(captionFonColor.toULong()),
    editBackgroundColor = Color(editBackgroundColor.toULong()),
    topAreaTextColor = Color(topAreaTextColor.toULong()),
    topAreaBackgroundColor = Color(topAreaBackgroundColor.toULong()),
    bottomAreaTextColor = Color(bottomAreaTextColor.toULong()),
    bottomAreaBackgroundColor = Color(bottomAreaBackgroundColor.toULong())
)

fun isUseCustomPalettePreference(context: Context): Boolean {
    val res = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        .getBoolean(USE_CUSTOM_PALETTE_KEY, false) ?: return false
    return res
}

fun saveUseCustomPalettePreference(context: Context, newVal: Boolean): Unit {
    context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        .edit()
        .putBoolean(USE_CUSTOM_PALETTE_KEY, newVal)
        .apply()
}

fun initialCustomColorPalette(context: Context){
    if (isUseCustomPalettePreference(context)){
        var cpalette = loadPalette(context)
        if (cpalette==null){
            cpalette=GlobalColors.currentPalette
        }
       GlobalColors.customizePalette(cpalette)
    }
}

