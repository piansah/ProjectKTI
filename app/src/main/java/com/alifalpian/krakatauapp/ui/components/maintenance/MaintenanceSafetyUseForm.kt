package com.alifalpian.krakatauapp.ui.components.maintenance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.R
import com.alifalpian.krakatauapp.domain.model.MaintenanceSafetyUse
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauOutlinedTextFieldWithLabel
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme

enum class MaintenanceSafetyUseFormType {
    Technician, Employee
}

@ExperimentalMaterial3Api
@Composable
fun MaintenanceSafetyUseForm(
    modifier: Modifier = Modifier,
    maintenanceSafetyUse: Resource<List<MaintenanceSafetyUse>>,
    type: MaintenanceSafetyUseFormType = MaintenanceSafetyUseFormType.Technician,
    onAddButtonClicked: () -> Unit = {},
    onMaintenanceFormChange: (Int, MaintenanceSafetyUse) -> Unit = { _, _ -> },
    enabled: Boolean = false
) {
    if (maintenanceSafetyUse is Resource.Success) {
        val tools = maintenanceSafetyUse.data
        val onDescriptionChanged: (Int, String) -> Unit = { index, description ->
            val maintenanceTools = tools[index].copy(
                description = description
            )
            onMaintenanceFormChange(index, maintenanceTools)
        }
        val onQuantityChanged: (Int, Int) -> Unit = { index, quantity ->
            val maintenanceTools = tools[index].copy(
                quantity = quantity
            )
            onMaintenanceFormChange(index, maintenanceTools)
        }
        val onUoMChanged: (Int, Int) -> Unit = { index, unitOfMeasurement ->
            val maintenanceTools = tools[index].copy(
                unitOfMeasurement = unitOfMeasurement
            )
            onMaintenanceFormChange(index, maintenanceTools)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFE3E3E3))
                .then(modifier)
        ) {
            MaintenanceSafetyUseFormHeader(
                type = type,
                onAddButtonClicked = onAddButtonClicked,
                enabled = enabled
            )
            tools.forEachIndexed { index, tool ->
                Spacer(modifier = Modifier.height(14.dp))
                KrakatauOutlinedTextFieldWithLabel(
                    value = tool.description,
                    onValueChanged = { onDescriptionChanged(index, it) },
                    label = "Description",
                    enabled = enabled
                )
                Spacer(modifier = Modifier.height(10.dp))
                KrakatauOutlinedTextFieldWithLabel(
                    value = tool.quantity.toString(),
                    onValueChanged = {
                        val number = if (it.isNotEmpty()) it.toInt() else 0
                        onQuantityChanged(index, number)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = "Quantity",
                    enabled = enabled
                )
                Spacer(modifier = Modifier.height(10.dp))
                KrakatauOutlinedTextFieldWithLabel(
                    value = tool.unitOfMeasurement.toString(),
                    onValueChanged = {
                        val number = if (it.isNotEmpty()) it.toInt() else 0
                        onUoMChanged(index, number)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = "Unit of Measurement",
                    enabled = enabled
                )
                if (index != tools.lastIndex) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Divider(thickness = 2.dp)
                }
            }
        }
    }
}

@Composable
fun MaintenanceSafetyUseFormHeader(
    modifier: Modifier = Modifier,
    type: MaintenanceSafetyUseFormType = MaintenanceSafetyUseFormType.Technician,
    onAddButtonClicked: () -> Unit = {},
    enabled: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "PENGGUNAAN SAFETY",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        if (type == MaintenanceSafetyUseFormType.Technician) {
            IconButton(
                onClick = onAddButtonClicked,
                modifier = Modifier.offset(x = 16.dp),
                enabled = enabled
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_circle_outline_24),
                    contentDescription = "Add form",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewMaintenanceSafetyUseForm() {
    PreventiveMaintenanceTheme {
        Surface {
            val tools = (1..3).map {
                MaintenanceSafetyUse(
                    documentId = it.toString(),
                    description = "Obeng",
                    quantity = 1,
                    unitOfMeasurement = 1
                )
            }
//            MaintenanceSafetyUseForm(
//                tools = tools,
//                modifier = Modifier.padding(16.dp)
//            )
        }
    }
}