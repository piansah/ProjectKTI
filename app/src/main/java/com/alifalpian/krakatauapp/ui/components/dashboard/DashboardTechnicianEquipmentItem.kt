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
import com.alifalpian.krakatauapp.domain.model.TechnicianDashboardEquipment
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme

@Composable
fun DashboardTechnicianEquipmentItem(
    modifier: Modifier = Modifier,
    equipment: TechnicianDashboardEquipment,
    onClicked: (TechnicianDashboardEquipment) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 16.dp))
            .clickable { onClicked(equipment) }
            .background(Color(0xFFE9E9E9))
            .padding(vertical = 16.dp, horizontal = 32.dp),
    ) {
        Text(
            text = equipment.name,
            lineHeight = 2.em,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Jumlah : ${equipment.count}",
            lineHeight = 2.33.em,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Total Maintenance : ${equipment.maintenanceCount}",
            lineHeight = 2.33.em,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun PreviewDashboardTechnicianEquipmentItem() {
    PreventiveMaintenanceTheme {
        Surface {
            val dummyTechnicianDashboardEquipments = (1..10).map {
                TechnicianDashboardEquipment(
                    id = it.toString(),
                    name = "Komputer",
                    count = 654,
                    maintenanceCount = 354
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(all = 32.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = dummyTechnicianDashboardEquipments, key = { it.id }) {
                    DashboardTechnicianEquipmentItem(equipment = it)
                }
            }
        }
    }
}
