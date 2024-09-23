package ru.tyryshkin.coreui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tyryshkin.coreui.theme.RTheme

@Composable
fun RRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    RadioButton(
        modifier = modifier,
        selected = selected,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
private fun RRadioButtonPreview() = RTheme {
    Column {
        RRadioButton(
            selected = true,
            onClick = { }
        )
        RSpacer(height = 16.dp)
        RRadioButton(
            selected = false,
            onClick = { }
        )
    }
}
