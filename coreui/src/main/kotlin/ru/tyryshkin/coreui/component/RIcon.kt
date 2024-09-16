package ru.tyryshkin.coreui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.tyryshkin.coreui.R
import ru.tyryshkin.coreui.theme.RTheme

@Composable
fun RIcon(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Icon(
        painter = painterResource(icon),
        tint = tint,
        modifier = modifier,
        contentDescription = null
    )
}

@Preview
@Composable
private fun RIconPreview() = RTheme {
    Column {
        RIcon(
            icon = R.drawable.ic_info
        )
    }
}
