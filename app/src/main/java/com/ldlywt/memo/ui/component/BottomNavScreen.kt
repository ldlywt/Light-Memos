package com.ldlywt.memo.ui.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.ui.graphics.vector.ImageVector
import com.ldlywt.memo.R
import com.ldlywt.memo.ui.page.common.RouteName

sealed class Screen(val route: String, @StringRes val stringId: Int, val imageId: ImageVector) {
    object Memos : Screen(RouteName.MEMOS, R.string.memos, Icons.Outlined.GridView)
    object Explore : Screen(RouteName.EXPLORE, R.string.explore, Icons.Outlined.Home)
    object Archived : Screen(RouteName.TAGLIST, R.string.tags, Icons.Outlined.Tag)
//    object Settings : Screen(RouteName.SETTINGS, R.string.settings, Icons.Outlined.Settings)
}

val bottomNavScreenList = listOf(
    Screen.Memos,
    Screen.Explore,
    Screen.Archived,
//    Screen.Settings,
)