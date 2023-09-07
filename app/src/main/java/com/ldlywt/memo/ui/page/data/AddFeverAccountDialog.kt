package com.ldlywt.memo.ui.page.data

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RssFeed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.ldlywt.memo.R
import com.ldlywt.memo.ui.component.RYDialog
import com.ldlywt.memo.ui.component.RYOutlineTextField

@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun AddFeverAccountDialog(
    navController: NavHostController,
    showCloudConfigDialog: Boolean,
    onDismissRequest: () -> Unit = {},
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var serverUrl by rememberSaveable { mutableStateOf("https://dav.jianguoyun.com/dav/") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    RYDialog(
        modifier = Modifier.padding(horizontal = 10.dp),
        visible = showCloudConfigDialog,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            focusManager.clearFocus()
            onDismissRequest()
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.RssFeed,
                contentDescription = null,
            )
        },
        title = {
            Text(
                text = stringResource(R.string.cloud_data_config),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                RYOutlineTextField(
                    value = serverUrl,
                    onValueChange = { serverUrl = it },
                    label = stringResource(R.string.server_url),
                    placeholder = "https://dav.jianguoyun.com/dav/",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                )
                Spacer(modifier = Modifier.height(10.dp))
                RYOutlineTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = stringResource(R.string.username),
                    placeholder = stringResource(R.string.username),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
                Spacer(modifier = Modifier.height(10.dp))
                RYOutlineTextField(
                    value = password,
                    onValueChange = { password = it },
                    isPassword = true,
                    label = stringResource(R.string.password),
                    placeholder = stringResource(R.string.password),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        },
        confirmButton = {
            TextButton(
                enabled = serverUrl.isNotBlank() && username.isNotEmpty() && password.isNotEmpty(),
                onClick = {
                    focusManager.clearFocus()
                }
            ) {
                Text(stringResource(R.string.add))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    focusManager.clearFocus()
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
    )
}
