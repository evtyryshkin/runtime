package ru.tyryshkin.coreui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.tyryshkin.coreui.theme.RTheme

@Composable
fun RBottomBar(
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    horizontalPaddingContent: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (showDivider) RDivider()
        Column(
            modifier = Modifier.padding(
                horizontal = horizontalPaddingContent,
                vertical = 8.dp
            )
        ) { content() }
    }
}

@Preview
@Composable
private fun Preview() = RTheme {
    RBottomBar {
        RButton(
            onClick = {},
            title = "Next"
        )
    }
}

@Preview
@Composable
private fun PreviewVerticalButtons() = RTheme {
    RBottomBar {
        RButton(
            onClick = {},
            title = "Next"
        )
        RSpacer(height = 8.dp)
        RButton(
            onClick = {},
            title = "Cancel",
            type = ButtonType.SECONDARY
        )
    }
}
