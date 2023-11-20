package com.alifalpian.krakatauapp.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.domain.model.EmployeeDashboardEquipment
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme

@Composable
fun DashboardEmployeeEquipmentItem(
    modifier: Modifier = Modifier,
    equipment: EmployeeDashboardEquipment,
    onClicked: (EmployeeDashboardEquipment) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 16.dp))
            .clickable { onClicked(equipment) }
            .background(Color(0xFFE9E9E9))
            .padding(all = 16.dp),
    ) {
        Text(
            text = equipment.order,
            lineHeight = 1.67.em,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.1.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = equipment.equipmentName,
            lineHeight = 1.25.em,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.1.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = equipment.location,
            lineHeight = 1.25.em,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.1.sp
        )
    }
}

@Preview
@Composable
fun PreviewDashboardEmployeeEquipmentItem() {
    PreventiveMaintenanceTheme {
        Surface {
            val dummyEmployeeDashboardEquipments = (1..10).map {
                EmployeeDashboardEquipment(
                    id = it.toString(),
                    order = "2210003221",
                    location = "Ruang Staff SEKPER (WTP)",
                    equipmentName = "PRINTER INKJET EPSON L3250 SIMK"
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(all = 32.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = dummyEmployeeDashboardEquipments, key = { it.id }) {
                    DashboardEmployeeEquipmentItem(equipment = it)
                }
            }
        }
    }
}
