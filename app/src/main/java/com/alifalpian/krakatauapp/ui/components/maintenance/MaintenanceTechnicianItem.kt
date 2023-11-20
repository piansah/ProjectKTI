package com.alifalpian.krakatauapp.ui.components.maintenance

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.MaintenanceEquipment
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.alifalpian.krakatauapp.util.toMaintenanceDateFormat
import com.valentinilk.shimmer.shimmer

@Composable
fun MaintenanceTechnicianItem(
    modifier: Modifier = Modifier,
    equipment: Equipment,
    onClicked: (Equipment) -> Unit = {}
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
                text = equipment.equipment.toString(),
                lineHeight = 1.67.em,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.1.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = equipment.description,
                lineHeight = 1.67.em,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.1.sp
            )
            if (equipment.technicianName.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Teknisi : ${equipment.technicianName}",
                    lineHeight = 1.67.em,
                    fontSize = 12.sp,
                    letterSpacing = 0.1.sp
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = equipment.date.toDate().toMaintenanceDateFormat(),
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
            if (equipment.maintenanceStatus.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                val background = if (equipment.maintenanceStatus.contains("accept", ignoreCase = true)) {
                    MaterialTheme.colorScheme.primary
                } else if (equipment.maintenanceStatus.contains("reject", ignoreCase = true)) {
                    MaterialTheme.colorScheme.error
                } else {
                    Color.Black.copy(alpha = 0.6f)
                }
                Text(
                    text = equipment.maintenanceStatus,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.1.sp
                    ),
                    modifier = Modifier
                        .background(background, CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ShimmerMaintenanceTechnicianItem(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .shimmer()
            .height(100.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color(0xffe9e9e9))
            .padding(vertical = 8.dp, horizontal = 12.dp)
    )
}

@Preview
@Composable
fun PreviewMaintenanceTechnicianItem() {
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
//            LazyColumn(
//                contentPadding = PaddingValues(all = 32.dp),
//                verticalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                items(items = dummyMaintenanceEquipments, key = { it.id }) {
//                    val type = if (it.id.toInt() % 2 == 0) MaintenanceTechnicianItemType.AllEquipment else MaintenanceTechnicianItemType.Approved
////                    MaintenanceTechnicianItem(equipment = it, type = type)
//                    ShimmerMaintenanceTechnicianItem()
//                }
//            }
            Text(
                text = "Accept",
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.1.sp
                ),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}
