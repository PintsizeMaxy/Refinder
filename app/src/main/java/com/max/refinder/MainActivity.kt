package com.max.refinder

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.max.refinder.ui.theme.RefinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RefinderTheme {
                val navController = rememberNavController()
                val locationPermissionsState =
                    rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
                if (locationPermissionsState.status.isGranted) {
                    NavHost(navController = navController, startDestination = "map") {
                        composable("map") { MapScreen(viewModel = hiltViewModel()) }
                    }
                } else {
                    Surface(Modifier.fillMaxSize()) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val textToShow =
                                if (locationPermissionsState.status.shouldShowRationale) {
                                    R.string.permissions_request_needed
                                } else {
                                    R.string.permission_request_initial
                                }
                            Text(
                                text = stringResource(textToShow),
                                style = MaterialTheme.typography.titleLarge
                            )
                            Button(onClick = { locationPermissionsState.launchPermissionRequest() }) {
                                Text(stringResource(R.string.request_permissions))
                            }
                        }
                    }
                }
            }
        }
    }
}