package com.alifalpian.krakatauapp.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alifalpian.krakatauapp.R
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.User
import com.alifalpian.krakatauapp.presentation.destinations.HomeEmployeeScreenDestination
import com.alifalpian.krakatauapp.presentation.destinations.HomeTechnicianScreenDestination
import com.alifalpian.krakatauapp.presentation.destinations.LoginScreenDestination
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauButton
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauOutlinedTextField
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauOutlinedTextFieldType
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private const val TAG = "LoginScreen"

@ExperimentalFoundationApi
@RootNavGraph(start = true)
@Destination
@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val loginUiState by loginViewModel.loginUiState.collectAsState()
    val email = loginUiState.email
    val password = loginUiState.password
    val loginResult = loginUiState.loginResult
    val loggedUser = loginUiState.loggedUser
    val loginButtonEnabled by loginViewModel.loginButtonEnabled.collectAsState(initial = false)

    val loginLoadingState by loginViewModel.loginLoadingState.collectAsState(initial = false)

    var uid by remember { mutableStateOf("") }

    val infoDialogUseCase = rememberUseCaseState()

    var messageDialog: String by remember { mutableStateOf("") }

    val navigateToHomeScreen: (User) -> Unit = { user ->
        if (user.type.contains("teknisi", true)) {
            navigator.navigate(HomeTechnicianScreenDestination()) {
                popUpTo(LoginScreenDestination.route) {
                    inclusive = true
                }
            }
        } else {
            navigator.navigate(HomeEmployeeScreenDestination()) {
                popUpTo(LoginScreenDestination.route) {
                    inclusive = true
                }
            }
        }
    }

    val signInWithEmailAndPassword: () -> Unit = {
        loginViewModel.signInWithEmailAndPassword(email, password)
    }

    LaunchedEffect(key1 = loginResult) {
        if (loginResult is Resource.Error) {
            messageDialog = loginResult.error.toString()
            infoDialogUseCase.show()
        }
        if (loginResult is Resource.Success) {
            uid = loginResult.data.user?.uid ?: ""
            loginViewModel.getUser(uid)
        }
    }

    LaunchedEffect(key1 = loggedUser) {
        if (loggedUser is Resource.Error) {
            messageDialog = loggedUser.error.toString()
            infoDialogUseCase.show()
        }
        if (loggedUser is Resource.Success) {
            navigateToHomeScreen(loggedUser.data)
        }
    }

    InfoDialog(
        state = infoDialogUseCase,
        selection = InfoSelection(
            positiveButton = SelectionButton(
                text = "Oke"
            ),
            onPositiveClick = {
                infoDialogUseCase.finish()
            }
        ),
        body = InfoBody.Default(bodyText = messageDialog)
    )

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = modifier
                    .width(width = 147.dp)
                    .height(height = 36.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            KrakatauOutlinedTextField(
                value = loginUiState.email,
                onValueChanged = loginViewModel::onEmailChange,
                label = "Email",
                modifier = Modifier.padding(horizontal = 70.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            KrakatauOutlinedTextField(
                value = loginUiState.password,
                onValueChanged = loginViewModel::onPasswordChange,
                label = "Password",
                type = KrakatauOutlinedTextFieldType.Password,
                modifier = Modifier.padding(horizontal = 70.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            KrakatauButton(
                title = "Login",
                onClicked = signInWithEmailAndPassword,
                enabled = loginButtonEnabled,
                loading = loginLoadingState
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewLoginScreen() {
    PreventiveMaintenanceTheme {
        Surface {
            LoginScreen()
        }
    }
}
