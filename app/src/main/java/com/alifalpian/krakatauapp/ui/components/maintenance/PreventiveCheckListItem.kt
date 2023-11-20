package com.alifalpian.krakatauapp.ui.components.maintenance

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.domain.model.MaintenanceCheckPoint
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme

@Composable
fun PreventiveCheckListItem(
    modifier: Modifier = Modifier,
    item: MaintenanceCheckPoint,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.size(16.dp),
            enabled = enabled
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 16.sp
        )
    }
}

@Preview
@Composable
fun PreviewPreventiveCheckListItem() {
    PreventiveMaintenanceTheme {
        Surface {
            val item = MaintenanceCheckPoint(
                text = "Periksa komponen komputer pastikan tidak ada kerusakan",
                id = "fwefw"
            )
            PreventiveCheckListItem(
                item = item,
                onCheckedChange = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
