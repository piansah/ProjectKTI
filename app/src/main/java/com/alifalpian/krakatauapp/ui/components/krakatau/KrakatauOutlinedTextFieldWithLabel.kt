package com.alifalpian.krakatauapp.ui.components.krakatau

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.alifalpian.krakatauapp.util.emptyString

@ExperimentalMaterial3Api
@Composable
fun KrakatauOutlinedTextFieldWithLabel(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    placeholder: String = emptyString(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enabled: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            placeholder = { Text(placeholder) },
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
            keyboardOptions = keyboardOptions,
            enabled = enabled
        )
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun PreviewKrakatauOutlinedTextFieldWithLabel() {
    PreventiveMaintenanceTheme {
        Surface {
            var value by remember { mutableStateOf("") }
            KrakatauOutlinedTextFieldWithLabel(
                value = value,
                onValueChanged = { value = it },
                label = "Description",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}