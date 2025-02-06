package com.ldlywt.memo.ui.page.memos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.ldlywt.memo.R
import com.ldlywt.memo.ext.string
import com.ldlywt.memo.ui.page.common.LocalRootNavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage() {
    val context = LocalContext.current

    var searchText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var isWholeWordSearch by remember { mutableStateOf(false) }

    val rootNavController = LocalRootNavController.current
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(end = 15.dp)
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            value = searchText,
                            onValueChange = { searchText = it },
                            singleLine = true,
                            placeholder = { Text(R.string.search.string) },
                            shape = ShapeDefaults.ExtraLarge,
                            trailingIcon = {
                                TextButton(
                                    shape = RoundedCornerShape(28.0.dp),
                                    onClick = {
                                        isWholeWordSearch = !isWholeWordSearch
                                    },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = if (isWholeWordSearch) Color.Green else Color(
                                            ContextCompat.getColor(context, R.color.fontColor)
                                        )
                                    ),
                                    modifier = Modifier.size(56.dp)

                                ) {

                                    Text(text = R.string.whole_word_search.string, fontSize = 18.sp)
                                }
                            },
                            leadingIcon = {
                                IconButton(onClick = { rootNavController.popBackStack() }) {
                                    Icon(
                                        Icons.Outlined.ArrowBack,
                                        contentDescription = R.string.back.string
                                    )
                                }
                            }
                        )

                    }
                }
            )
        },

        content = { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding())
            ) {
                MemosList(
                    searchString = searchText.text,
                    isWholeWordSearch = isWholeWordSearch
                )
            }
        }
    )

    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
    }
}
