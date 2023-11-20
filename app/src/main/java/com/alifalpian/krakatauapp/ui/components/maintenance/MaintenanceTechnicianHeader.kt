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
import com.alifalpian.krakatauapp.domain.model.MaintenanceHistory
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.User
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.alifalpian.krakatauapp.ui.theme.ShimmerColor
import com.alifalpian.krakatauapp.util.printHour
import com.alifalpian.krakatauapp.util.toMaintenanceDateFormat
import com.valentinilk.shimmer.shimmer
import java.util.Date

@Composable
fun MaintenanceTechnicianHeader(
    modifier: Modifier = Modifier,
    technician: Resource<User>,
    maintenanceHistory: Resource<MaintenanceHistory>
) {
    if (technician is Resource.Loading || maintenanceHistory is Resource.Loading) {
        LoadingMaintenanceTechnicianHeader(
            modifier = modifier
        )
    }
    if (technician is Resource.Success && maintenanceHistory is Resource.Success) {
        SuccessMaintenanceTechnicianHeader(
            modifier = modifier,
            technician = technician.data,
            plantDuration = maintenanceHistory.data.plantDuration.toDate(),
            actualDuration = maintenanceHistory.data.actualDuration.toDate()
        )
    }
}

@Composable
private fun LoadingMaintenanceTechnicianHeader(
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
    }
}

@Composable
private fun SuccessMaintenanceTechnicianHeader(
    modifier: Modifier = Modifier,
    technician: User,
    plantDuration: Date,
    actualDuration: Date
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
    ) {
        MaintenanceTechnicianHeaderItem(label = "TEKNISI", value = technician.name)
        Spacer(modifier = Modifier.height(8.dp))
        MaintenanceTechnicianHeaderItem(label = "TGL DILAKUKAN", value = actualDuration.toMaintenanceDateFormat())
        Spacer(modifier = Modifier.height(8.dp))
        MaintenanceTechnicianHeaderItem(label = "JAM MULAI", value = plantDuration.printHour())
        Spacer(modifier = Modifier.height(8.dp))
        MaintenanceTechnicianHeaderItem(label = "JAM SELESAI", value = actualDuration.printHour())
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
fun PreviewMaintenanceTechnicianHeader() {
    PreventiveMaintenanceTheme {
        Surface {
            val technician = User(
                name = "Hasan Maulana Jhonson"
            )
//            MaintenanceTechnicianHeader(
//                modifier = Modifier.fillMaxWidth()
//                    .padding(16.dp),
//                technician = Resource.Success(technician),
//            )
        }
    }
}
