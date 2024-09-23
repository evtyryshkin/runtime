package ru.tyryshkin.coreui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tyryshkin.coreui.R
import ru.tyryshkin.coreui.theme.RTheme
import ru.tyryshkin.coreui.theme.Typography

@Composable
fun REmptyPlaceholder(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            RIcon(
                modifier = Modifier.size(80.dp),
                icon = R.drawable.ic_info,
                tint = MaterialTheme.colorScheme.primary
            )
            RSpacer(height = 20.dp)
            RText(
                text = title,
                style = Typography.titleLarge
            )
        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
private fun Preview() = RTheme {
    REmptyPlaceholder(
        title = "Empty content"
    )
}
