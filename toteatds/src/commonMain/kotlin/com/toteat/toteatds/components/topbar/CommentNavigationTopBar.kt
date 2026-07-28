package com.toteat.toteatds.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.icons.ArrowBackIconButton
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.comment_navigation_badge_description
import designsystemmobile.toteatds.generated.resources.comment_navigation_semantic_label
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val BadgeShape = RoundedCornerShape(50)
private val BadgeMinHeight = 30.dp
private val BadgeMaxWidth = 140.dp
private val BackButtonSize = 36.dp
private val BackButtonIconSize = 22.dp

/**
 * Top bar for comment / messaging screens (e.g. "Comunicación cocina").
 *
 * Uses the primary brand color as container to differentiate the conversation context from the
 * regular dark navigation bars, a circular back button on the left and an optional white badge
 * next to the title with the reference of the conversation (table, order, etc.).
 *
 * @param title Screen title, rendered centered next to the badge.
 * @param onNavigateBackClick Invoked when the back button is tapped.
 * @param modifier Modifier applied to the root container.
 * @param badgeText Reference shown in the trailing badge (e.g. "Mesa S7"). Pass `null` to hide it.
 * @param testTag Optional test tag for UI testing. Derived tags: `_back`, `_title`, `_badge`.
 */
@Composable
fun CommentNavigationTopBar(
    title: String,
    onNavigateBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    badgeText: String? = null,
    testTag: String = ""
) {
    val semanticLabel = stringResource(Res.string.comment_navigation_semantic_label, title)

    ToteatTopBar(
        modifier = modifier,
        semanticLabel = semanticLabel,
        containerColor = MaterialTheme.colorScheme.primary,
        leftComponent = {
            ArrowBackIconButton(
                onNavigateBackClick = onNavigateBackClick,
                size = BackButtonSize,
                iconSize = BackButtonIconSize,
                testTag = if (testTag.isNotEmpty()) "${testTag}_back" else ""
            )
        },
        centerComponent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .basicMarquee(iterations = Int.MAX_VALUE)
                        .semantics { heading() }
                        .then(
                            if (testTag.isNotEmpty()) {
                                Modifier.setTestTag("${testTag}_title")
                            } else {
                                Modifier
                            }
                        )
                )
                if (badgeText != null) {
                    CommentNavigationTopBarBadge(
                        text = badgeText,
                        testTag = if (testTag.isNotEmpty()) "${testTag}_badge" else ""
                    )
                }
            }
        },
        testTag = testTag
    )
}

/**
 * White pill badge used inside [CommentNavigationTopBar] to show the conversation reference.
 */
@Composable
fun CommentNavigationTopBarBadge(
    text: String,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val description = stringResource(Res.string.comment_navigation_badge_description, text)

    Box(
        modifier = modifier
            .heightIn(min = BadgeMinHeight)
            .widthIn(max = BadgeMaxWidth)
            .clip(BadgeShape)
            .background(MaterialTheme.colorScheme.onPrimary)
            .semantics {
                role = Role.Image
                contentDescription = description
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@Preview
private fun CommentNavigationTopBarPreview() {
    ToteatTheme {
        CommentNavigationTopBar(
            title = "Comunicación cocina",
            badgeText = "Mesa S7",
            onNavigateBackClick = {}
        )
    }
}

@Composable
@Preview
private fun CommentNavigationTopBarNoBadgePreview() {
    ToteatTheme {
        CommentNavigationTopBar(
            title = "Comunicación cocina",
            onNavigateBackClick = {}
        )
    }
}

@Composable
@Preview
private fun CommentNavigationTopBarLongTitlePreview() {
    ToteatTheme {
        Column {
            CommentNavigationTopBar(
                title = "Comunicación con la cocina del segundo piso",
                badgeText = "Mesa S7",
                onNavigateBackClick = {}
            )
            CommentNavigationTopBar(
                title = "Comunicación cocina",
                badgeText = "Mesa del salón principal",
                onNavigateBackClick = {}
            )
        }
    }
}
