package com.toteat.toteatds.components.topbar

import androidx.compose.foundation.basicMarquee
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.toteat.toteatds.components.icons.ArrowBackIconButton
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BackNavigationTopBar(
    title: String,
    onNavigateBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ToteatTopBar(
        modifier = modifier,
        semanticLabel = "Barra de navegación con retroceso: $title",
        leftComponent = {
            ArrowBackIconButton(onNavigateBackClick = onNavigateBackClick)
        },
        centerComponent = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f, fill = false)
                    .basicMarquee(iterations = Int.MAX_VALUE)
                    .semantics {
                        heading()
                    }
            )
        }
    )
}

@Composable
@Preview
private fun BackNavigationTopBarPreview() {
    ToteatTheme {
        BackNavigationTopBar(
            title = "Mesa B1",
            onNavigateBackClick = {}
        )
    }
}

@Composable
@Preview
private fun BackNavigationTopBarLongTitlePreview() {
    ToteatTheme {
        BackNavigationTopBar(
            title = "Mesa principal con nombre muy largo",
            onNavigateBackClick = {}
        )
    }
}

@Composable
@Preview
private fun BackNavigationTopBarVeryLongTitlePreview() {
    ToteatTheme {
        BackNavigationTopBar(
            title = "Mesa principal con nombre súper largo que debería mostrarse con marquee y adaptarse al espacio",
            onNavigateBackClick = {}
        )
    }
}

