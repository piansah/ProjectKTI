package com.alifalpian.krakatauapp.ui.components.maintenance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.User
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.alifalpian.krakatauapp.ui.theme.ShimmerColor
import com.alifalpian.krakatauapp.util.toMaintenanceDateFormat
import com.valentinilk.shimmer.shimmer

@Composable
fun MaintenanceHeader(
    modifier: Modifier = Modifier,
    equipment: Resource<Equipment>,
    user: Resource<User>,
) {
    if (equipment is Resource.Loading || user is Resource.Loading) {
        LoadingMaintenanceHeader(
            modifier = modifier
        )
    }
    if (equipment is Resource.Success && user is Resource.Success) {
        SuccessMaintenanceHeader(
            equipment = equipment.data,
            user = user.data,
            modifier = modifier
        )
    }
}

@Composable
private fun LoadingMaintenanceHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .shimmer()
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(ShimmerColor)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(ShimmerColor)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(ShimmerColor)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(ShimmerColor)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(ShimmerColor)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(ShimmerColor)
        )
    }
}

@Composable
private fun SuccessMaintenanceHeader(
    modifier: Modifier = Modifier,
    equipment: Equipment,
    user: User
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
    ) {
        MaintenanceTechnicianHeaderItem(label = "ORDER", value = equipment.equipment.toString())
        Spacer(modifier = Modifier.height(8.dp))
        MaintenanceTechnicianHeaderItem(label = "TANGGAL", value = equipment.date.toDate().toMaintenanceDateFormat())
        Spacer(modifier = Modifier.height(8.dp))
        MaintenanceTechnicianHeaderItem(label = "INTERVAL", value = equipment.interval)
        Spacer(modifier = Modifier.height(8.dp))
        MaintenanceTechnicianHeaderItem(label = "PELAKSANA", value = equipment.execution)
        Spacer(modifier = Modifier.height(8.dp))
        MaintenanceTechnicianHeaderItem(label = "USER", value = user.name)
        Spacer(modifier = Modifier.height(8.dp))
        MaintenanceTechnicianHeaderItem(label = "LOKASI", value = equipment.location)
    }
}


@Composable
private fun MaintenanceTechnicianHeaderItem(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(2f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = ":",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(3f)
        )
    }
}

@Preview
@Composable
fun PreviewMaintenanceHeader() {
    PreventiveMaintenanceTheme {
        Surface {
            val equipment = Equipment(
                documentId = "user123",
                equipment = 2210043175,
                interval = "4 MON",
                execution = "PG IT",
                location = "Ruang Staff SEKPER (WTP)",
                description = "LAPTOP DELL LATITUDE 3420 SKP4",
            )
//            MaintenanceHeader(
//                equipment = Resource.Loading,
//                modifier = Modifier.fillMaxWidth()
//                    .padding(16.dp)
//            )
        }
    }
}
