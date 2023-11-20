package com.alifalpian.krakatauapp.ui.components.maintenance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.domain.model.MaintenanceCheckPoint
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.alifalpian.krakatauapp.ui.theme.ShimmerColor
import com.valentinilk.shimmer.shimmer

@Composable
fun MaintenanceFormCheckList(
    modifier: Modifier = Modifier,
    maintenanceCheckPoints: Resource<List<MaintenanceCheckPoint>>,
    checkListEnabled: Boolean = false,
    onCheckedChanged: (MaintenanceCheckPoint, Int) -> Unit = {_, _ -> }
) {
    if (maintenanceCheckPoints is Resource.Loading) {
        LoadingMaintenanceFormCheckList(modifier = modifier)
    }
    if (maintenanceCheckPoints is Resource.Success) {
        SuccessMaintenanceFormCheckList(
            maintenanceCheckPoints = maintenanceCheckPoints.data,
            checkListEnabled = checkListEnabled,
            onCheckedChanged = onCheckedChanged,
            modifier = modifier
        )
    }
}

@Composable
private fun LoadingMaintenanceFormCheckList(
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
                .fillMaxWidth(0.7f)
                .height(20.dp)
                .background(ShimmerColor)
        )
        (0..9).forEach {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(ShimmerColor)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(16.dp)
                .background(ShimmerColor)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(16.dp)
                .background(ShimmerColor)
        )
    }
}

@Composable
private fun SuccessMaintenanceFormCheckList(
    modifier: Modifier = Modifier,
    maintenanceCheckPoints: List<MaintenanceCheckPoint>,
    checkListEnabled: Boolean = false,
    onCheckedChanged: (MaintenanceCheckPoint, Int) -> Unit
) {

    Column(
        modifier = Modifier
            .background(color = Color(0xFFEDEDED))
            .then(modifier)
    ) {
        Text(
            text = "PREVENTIVE PC DAN LAPTOP",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        maintenanceCheckPoints.forEachIndexed { index, preventiveCheckList ->
            Spacer(modifier = Modifier.height(8.dp))
            val onCheckedChangedItem: (Boolean) -> Unit = {
                val maintenanceCheckPoint = preventiveCheckList.copy(isChecked = it)
                onCheckedChanged(maintenanceCheckPoint, index)
            }
            PreventiveCheckListItem(
                item = preventiveCheckList,
                onCheckedChange = onCheckedChangedItem,
                modifier = Modifier.padding(horizontal = 8.dp),
                enabled = checkListEnabled
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (index != maintenanceCheckPoints.lastIndex) {
                Divider()
            }
        }
    }
}

@Preview
@Composable
fun PreviewMaintenanceFormCheckList() {
    PreventiveMaintenanceTheme {
        Surface {
            val maintenanceCheckPoints = Resource.Loading
            MaintenanceFormCheckList(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                maintenanceCheckPoints = maintenanceCheckPoints,
                onCheckedChanged = { _, _ -> }
            )
        }
    }
}
