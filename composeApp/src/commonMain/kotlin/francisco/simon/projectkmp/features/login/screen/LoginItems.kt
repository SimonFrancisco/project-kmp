package francisco.simon.projectkmp.features.login.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.ic_visibility
import projectkmp.composeapp.generated.resources.ic_visibility_off
import projectkmp.composeapp.generated.resources.login_button_text
import projectkmp.composeapp.generated.resources.login_username
import projectkmp.composeapp.generated.resources.login_label
import projectkmp.composeapp.generated.resources.login_password

@Composable
internal fun LoginLabel() {
    Text(
        text = stringResource(Res.string.login_label).uppercase(),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
internal fun UsernameTextField(
    username: String,
    onUsernameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = username,
        onValueChange = {
            onUsernameChange(it)
        },
        label = {
            Text(
                text = stringResource(Res.string.login_username)
            )
        },
        placeholder = {
            Text(
                text = stringResource(Res.string.login_username)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            showKeyboardOnFocus = true
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
internal fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = password,
        onValueChange = {
            onPasswordChange(it)
        },
        label = {
            Text(
                text = stringResource(Res.string.login_password)
            )
        },
        placeholder = {
            Text(
                text = stringResource(Res.string.login_password)
            )
        },
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) {
                        vectorResource(Res.drawable.ic_visibility)
                    } else {
                        vectorResource(Res.drawable.ic_visibility_off)
                    },
                    contentDescription = null
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            showKeyboardOnFocus = true
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
internal fun LoginButton(
    onClick: () -> Unit,
    isActive: Boolean
) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            contentColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        enabled = isActive,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(Res.string.login_button_text),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
