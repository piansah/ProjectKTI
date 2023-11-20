package com.alifalpian.krakatauapp.ui.components.maintenance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.alifalpian.krakatauapp.ui.theme.ShimmerColor
import com.valentinilk.shimmer.shimmer

@Composable
fun MaintenanceFormHeader(
    modifier: Modifier = Modifier,
    equipment: Resource<Equipment>
) {
    when (equipment) {
        is Resource.Success -> MaintenanceFormHeaderSuccess(equipment = equipment.data, modifier = modifier)
        else -> MaintenanceFormHeaderLoading(modifier)
    }
}

@Composable
private fun MaintenanceFormHeaderLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .background(color = Color(0xFFEDEDED))
            .shimmer()
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(20.dp)
                .background(ShimmerColor)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(16.dp)
                .background(ShimmerColor)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(16.dp)
                .background(ShimmerColor)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(16.dp)
                .background(ShimmerColor)
        )
        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Composable
private fun MaintenanceFormHeaderSuccess(
    modifier: Modifier = Modifier,
    equipment: Equipment
) {
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
        MaintenanceContentRowItem(label = "EQUIPMENT", text = equipment.equipment.toString())
        Spacer(modifier = Modifier.height(8.dp))
        MaintenanceContentRowItem(label = "ALAT/KODE", text = equipment.description)
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "URAIAN PEKERJAAN :",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Composable
private fun MaintenanceContentRowItem(
    label: String,
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f)
        )
        Text(text = ":")
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            modifier = Modifier.weight(3f),
            lineHeight = 16.sp
        )
    }
}

@Preview
@Composable
fun PreviewMaintenanceFormHeader() {
    PreventiveMaintenanceTheme {
        Surface {
            val equipment = Resource.Loading
            MaintenanceFormHeader(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                equipment = equipment
            )
        }
    }
}

