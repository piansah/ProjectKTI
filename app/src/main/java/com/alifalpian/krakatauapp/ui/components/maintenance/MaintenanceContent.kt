package com.alifalpian.krakatauapp.ui.components.maintenance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.MaintenanceCheckPoint
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauOutlinedTextField
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme

enum class MaintenanceContentType {
    Technician, Employee
}

@ExperimentalMaterial3Api
@Composable
fun MaintenanceContent(
    modifier: Modifier = Modifier,
    equipment: Resource<Equipment>,
    maintenanceCheckPoints: List<MaintenanceCheckPoint>,
    type: MaintenanceContentType = MaintenanceContentType.Technician
) {
    if (equipment is Resource.Success) {
        MaintenanceContentSuccess(
            modifier = modifier,
            equipment = equipment.data,
            type = type,
            maintenanceCheckPoints = maintenanceCheckPoints
        )
    }
}

@Composable
fun MaintenanceContentLoading(
    modifier: Modifier = Modifier
) {
    
}

@ExperimentalMaterial3Api
@Composable
fun MaintenanceContentSuccess(
    modifier: Modifier,
    equipment: Equipment,
    type: MaintenanceContentType,
    maintenanceCheckPoints: List<MaintenanceCheckPoint>,
) {
    val checkListEnabled = remember {
        type == MaintenanceContentType.Technician
    }
    Column(
        modifier = Modifier
            .background(color = Color(0xFFEDEDED))
            .then(modifier)
    ) {
        Text(
            text = "RESULT PREVENTIVE MAINTENANCE",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
//        MaintenanceContentRowItem(label = "EQUIPMENT", text = equipment.equipment)
        Spacer(modifier = Modifier.height(8.dp))
//        MaintenanceContentRowItem(label = "ALAT/KODE", text = equipment.description)
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "URAIAN PEKERJAAN :",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "PREVENTIVE PC DAN LAPTOP",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        maintenanceCheckPoints.forEachIndexed { index, preventiveCheckList ->
            Spacer(modifier = Modifier.height(4.dp))
            PreventiveCheckListItem(
                item = preventiveCheckList,
                onCheckedChange = {},
                modifier = Modifier.padding(horizontal = 8.dp),
                enabled = checkListEnabled
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (index != maintenanceCheckPoints.lastIndex) {
                Divider()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "DURASI PLANT : 1.0",
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "DURASI AKTUAL : 3.0",
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        if (type == MaintenanceContentType.Technician) {
            KrakatauOutlinedTextField(value = "", onValueChanged = {}, trailingLabel = "Jam")
        }
    }
}





@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewMaintenanceContent() {
    PreventiveMaintenanceTheme {
        Surface {
            val equipment = Resource.Success(
                Equipment(
                    documentId = "12319814917",
                    equipment = 2210043175,
                    interval = "4 MON",
                    execution = "PG IT",
                    location = "Ruang Staff SEKPER (WTP)",
                    description = "LAPTOP DELL LATITUDE 3420 SKP4",
                    type = "laptop"
                )
            )
            val maintenanceCheckPoints = (1..10).map {
                MaintenanceCheckPoint(
                    id = it.toString(),
                    text = "Periksa komponen komputer pastikan tidak ada kerusakan"
                )
            }
            MaintenanceContent(
                modifier = Modifier.padding(16.dp),
                equipment = equipment,
                maintenanceCheckPoints = maintenanceCheckPoints
            )
        }
    }
}
