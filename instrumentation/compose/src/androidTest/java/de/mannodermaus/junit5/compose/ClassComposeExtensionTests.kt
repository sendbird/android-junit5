package de.mannodermaus.junit5.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(AndroidComposeExtension::class)
class ClassComposeExtensionTests {

    @ValueSource(
        strings = [
            "click me",
            "touch me",
            "jfc it actually works"
        ]
    )
    @ParameterizedTest
    fun test(buttonLabel: String, extension: AndroidComposeExtension) {
        extension.runComposeTest {
            setContent {
                Column {
                    var counter by remember { mutableStateOf(0) }

                    Text(text = "Clicked: $counter")
                    Button(onClick = { counter++ }) {
                        Text(text = buttonLabel)
                    }
                }
            }

            onNodeWithText("Clicked: 0").assertIsDisplayed()
            onNodeWithText(buttonLabel).performClick()
            onNodeWithText("Clicked: 1").assertIsDisplayed()
        }
    }
}
