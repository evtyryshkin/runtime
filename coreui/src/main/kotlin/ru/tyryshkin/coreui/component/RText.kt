package ru.tyryshkin.coreui.component

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.tyryshkin.coreui.theme.RTheme
import ru.tyryshkin.coreui.theme.Typography

@Composable
fun RText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = Typography.bodyLarge,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
        textAlign = textAlign
    )
}

@Preview(backgroundColor = 0xffffff, showBackground = true)
@Composable
private fun RDefaultTextPreview() = RTheme {
    RText(text = "Default text")
}

@Preview(backgroundColor = 0xffffff, showBackground = true)
@Composable
private fun ROnPrimaryTextPreview() = RTheme {
    RText(
        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
        text = "OnPrimary Text",
        color = MaterialTheme.colorScheme.onPrimary
    )
}
