package com.alifalpian.krakatauapp.ui.components.krakatau

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KrakatauSmallButton(
    modifier: Modifier = Modifier,
    text: String,
    onClicked: () -> Unit,
    contentColor: Color = Color.White,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp,
        color = contentColor,
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClicked() }
            .background(containerColor)
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )

}