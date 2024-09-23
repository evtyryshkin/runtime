package ru.tyryshkin.coreui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.tyryshkin.coreui.R
import ru.tyryshkin.coreui.theme.RTheme

@Composable
fun RImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    Image(
        modifier = modifier,
        painter = painter,
        contentScale = contentScale,
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
private fun RImagePreview() = RTheme {
    Column {
        RImage(
            painter = painterResource(R.drawable.photo)
        )
    }
}
