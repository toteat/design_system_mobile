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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.theme.extended
import org.jetbrains.compose.resources.DrawableResource // Import DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * A generic welcome message component.
 *
 * @param imageResource The type-safe resource of the SVG to display.
 * @param userName The name of the user to welcome.
 * @param message The secondary message to display.
 */
@Composable
fun WelcomeMessage(
    imageVector: ImageVector, // El tipo es correcto
    userName: String,
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // La corrección clave está aquí
        Image(
            imageVector = imageVector,
            contentDescription = "Welcome Icon",
            modifier = Modifier.size(85.dp).padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¡Bienvenido $userName!",
            style = MaterialTheme.typography.bodyLargeRegular.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.extended.neutral400,
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}