package com.alifalpian.krakatauapp.ui.components.krakatau

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alifalpian.krakatauapp.R
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.alifalpian.krakatauapp.util.emptyString

enum class KrakatauOutlinedTextFieldType {
    Default, Password
}

@ExperimentalMaterial3Api
@Composable
fun KrakatauOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String = emptyString(),
    label: String = emptyString(),
    trailingLabel: String = emptyString(),
    type: KrakatauOutlinedTextFieldType = KrakatauOutlinedTextFieldType.Default
) {
    var visualTransformation by remember {
        mutableStateOf(
            if (type == KrakatauOutlinedTextFieldType.Default) VisualTransformation.None else PasswordVisualTransformation()
        )
    }
    var passwordVisible by remember {
        mutableStateOf(true)
    }
    val passwordIcon = remember(key1 = passwordVisible) {
        if (passwordVisible) {
            R.drawable.ic_baseline_visibility_24
        } else {
            R.drawable.ic_baseline_visibility_off_24
        }
    }
    val onTogglePasswordClicked: () -> Unit = {
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        passwordVisible = !passwordVisible
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text(
                text = label,
                color = Color(0xff4d4d4d)
            )
        },
        placeholder = { Text(placeholder) },
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
        trailingIcon = {
            if (type == KrakatauOutlinedTextFieldType.Default) {
                Text(text = trailingLabel)
            } else {
                IconButton(onClick = onTogglePasswordClicked) {
                    Icon(
                        painter = painterResource(id = passwordIcon),
                        contentDescription = null
                    )
                }
            }
        },
        visualTransformation = visualTransformation
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun PreviewKrakatauOutlinedTextField() {
    PreventiveMaintenanceTheme {
        Surface {
            var value by remember { mutableStateOf("") }
            KrakatauOutlinedTextField(
                value = value,
                onValueChanged = { value = it },
                placeholder = "Email",
                modifier = Modifier.padding(16.dp),
                type = KrakatauOutlinedTextFieldType.Password
            )
        }
    }
}