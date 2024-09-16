package ru.tyryshkin.coreui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import ru.tyryshkin.core.test.TestTag
import ru.tyryshkin.coreui.R
import ru.tyryshkin.coreui.theme.RTheme
import ru.tyryshkin.coreui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(
                    modifier = Modifier.testTag(TestTag.Component.AppBar.BUTTON_BACK),
                    onClick = onBackClick
                ) {
                    RIcon(
                        icon = R.drawable.ic_back,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        title = {
            RText(
                text = title,
                style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = TopAppBarDefaults.topAppBarColors().navigationIconContentColor,
            actionIconContentColor = TopAppBarDefaults.topAppBarColors().actionIconContentColor,
            scrolledContainerColor = TopAppBarDefaults.topAppBarColors().scrolledContainerColor
        )
    )
}

@Preview(backgroundColor = 0xffffff, showBackground = true)
@Composable
private fun RTopAppBarPreview() = RTheme {
    RTopAppBar(
        title = "Title",
        onBackClick = {}
    )
}
