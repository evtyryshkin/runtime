package ru.tyryshkin.coreui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tyryshkin.coreui.R
import ru.tyryshkin.coreui.theme.RTheme
import ru.tyryshkin.coreui.theme.Typography

@Composable
fun RErrorPlaceholder(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    painter: Painter? = null,
    painterColor: Color? = null,
    onActionClick: (() -> Unit)? = null,
    onActionLabel: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (painter != null) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painter,
                contentDescription = null,
                colorFilter = painterColor?.run { ColorFilter.tint(painterColor) }
            )
            RSpacer(height = 32.dp)
        }
        if (title != null) {
            RText(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = Typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        if (subtitle != null) {
            if (title != null) RSpacer(height = 8.dp)
            RText(
                modifier = Modifier.fillMaxWidth(),
                text = subtitle,
                style = Typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        onActionClick?.let {
            RSpacer(height = 200.dp)
            RButton(
                title = onActionLabel ?: stringResource(R.string.button_retry),
                onClick = onActionClick
            )
        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
private fun RErrorPlaceholderPreview() = RTheme {
    Column {
        RErrorPlaceholder(
            title = "Something went wrong!",
            modifier = Modifier,
            onActionClick = {}
        )
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
private fun RErrorPlaceholderWihImagePreview() = RTheme {
    Column {
        RErrorPlaceholder(
            title = "Something went wrong!",
            subtitle = "Try to reload page",
            painter = painterResource(R.drawable.error_no_location),
            modifier = Modifier
        )
    }
}
