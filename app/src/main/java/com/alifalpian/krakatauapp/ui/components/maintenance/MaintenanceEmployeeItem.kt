package com.alifalpian.krakatauapp.ui.components.maintenance

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.domain.model.MaintenanceEquipment
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme

@Composable
fun MaintenanceEmployeeItem(
    modifier: Modifier = Modifier,
    equipment: MaintenanceEquipment,
    onClicked: (MaintenanceEquipment) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable { onClicked(equipment) }
            .background(color = Color(0xffe9e9e9))
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = equipment.order,
                lineHeight = 1.67.em,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.1.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = equipment.equipmentName,
                lineHeight = 1.67.em,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.1.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Teknisi : ${equipment.technicianName}",
                lineHeight = 1.67.em,
                fontSize = 12.sp,
                letterSpacing = 0.1.sp
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = equipment.date,
                lineHeight = 1.67.em,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.1.sp,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = equipment.interval,
                lineHeight = 1.67.em,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.1.sp,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = equipment.location,
                lineHeight = 1.67.em,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.1.sp,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
fun PreviewMaintenanceEmployeeItem() {
    PreventiveMaintenanceTheme {
        Surface {
            val dummyMaintenanceEquipments = (1..10).map {
                MaintenanceEquipment(
                    id = it.toString(),
                    order = "2210043175",
                    date = "09/12/2023",
                    interval = "4 MON",
                    execution = "PG IT",
                    location = "Ruang Staff SEKPER (WTP)",
                    equipmentName = "LAPTOP DELL LATITUDE 3420 SKP4",
                    technicianName = "Hasan Maulana"
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(all = 32.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = dummyMaintenanceEquipments, key = { it.id }) {
                    MaintenanceEmployeeItem(equipment = it)
                }
            }
        }
    }
}
