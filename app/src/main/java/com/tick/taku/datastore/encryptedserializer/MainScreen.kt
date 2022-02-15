package com.tick.taku.datastore.encryptedserializer

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.app_name)) })
        },
        modifier = Modifier.fillMaxSize()
    ) {
        val focusManager = LocalFocusManager.current
        val state = viewModel.uiState.collectAsState()

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            val (labelRef, textFieldRef, btnRef) = createRefs()

            Text(
                text = state.value.text,
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(labelRef) {
                    width = Dimension.percent(0.7f)
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top, margin = 100.dp)
                }
            )

            var text by remember { mutableStateOf(state.value.text) }
            TextField(
                value = text,
                onValueChange = { text = it },
                maxLines = 1,
                modifier = Modifier.constrainAs(textFieldRef) {
                    width = Dimension.percent(0.7f)
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent, bias = 0.6f)
                }
            )

            Button(
                onClick = { viewModel.save(text) },
                modifier = Modifier.constrainAs(btnRef) {
                    width = Dimension.percent(0.3f)
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent, bias = 0.8f)
                }
            ) {
                Text(text = "Save")
            }
        }
    }
}