package com.toteat.toteatds.components.messageview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.welcome_greeting_prefix
import designsystemmobile.toteatds.generated.resources.welcome_greeting_suffix
import designsystemmobile.toteatds.generated.resources.welcome_icon_description
import org.jetbrains.compose.resources.stringResource

/**
 * A generic welcome message component.
 *
 * @param imageVector The vector image to display.
 * @param userName The name of the user to welcome.
 * @param message The secondary message to display.
 * @param modifier The modifier to apply to this layout.
 * @param testTag The test tag for UI testing.
 */
@Composable
fun WelcomeMessage(
    imageVector: ImageVector,
    userName: String,
    message: String,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val prefix = stringResource(Res.string.welcome_greeting_prefix)
    val suffix = stringResource(Res.string.welcome_greeting_suffix)
    val greetingText = "$prefix $userName$suffix"
    val iconDescription = stringResource(Res.string.welcome_icon_description)

    Column(
        modifier = modifier
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .semantics { contentDescription = greetingText },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            imageVector = imageVector,
            contentDescription = iconDescription,
            modifier = Modifier.size(85.dp).padding(top = 10.dp)
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_icon") else Modifier)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = greetingText,
            style = MaterialTheme.typography.bodyLargeRegular.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_greeting") else Modifier)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.extended.neutral400,
            modifier = Modifier.padding(bottom = 10.dp)
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_message") else Modifier)
        )
    }
}
