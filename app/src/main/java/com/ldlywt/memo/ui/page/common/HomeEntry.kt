package com.ldlywt.memo.ui.page.common

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.Consumer
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ldlywt.memo.data.model.ShareContent
import com.ldlywt.memo.data.preference.LocalDarkTheme
import com.ldlywt.memo.ext.isFirstLaunch
import com.ldlywt.memo.ext.string
import com.ldlywt.memo.theme.AppTheme
import com.ldlywt.memo.ui.component.animatedComposable
import com.ldlywt.memo.ui.component.bottomNavScreenList
import com.ldlywt.memo.ui.page.data.DataCloudManagerPage
import com.ldlywt.memo.ui.page.data.DataLocalManagerPage
import com.ldlywt.memo.ui.page.memoinput.MemoInputPage
import com.ldlywt.memo.ui.page.memos.ArchivedMemoList
import com.ldlywt.memo.ui.page.memos.ExplorePage
import com.ldlywt.memo.ui.page.memos.MemosHomePage
import com.ldlywt.memo.ui.page.memos.SearchPage
import com.ldlywt.memo.ui.page.resource.ResourceListPage
import com.ldlywt.memo.ui.page.settings.AboutPage
import com.ldlywt.memo.ui.page.settings.ColorAndStylePage
import com.ldlywt.memo.ui.page.settings.DarkThemePage
import com.ldlywt.memo.ui.page.settings.SettingsPage
import com.ldlywt.memo.ui.page.startup.StartupPage
import com.ldlywt.memo.ui.page.tag.TagListPage
import com.ldlywt.memo.ui.page.tag.TagMemoPage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeEntry() {
    val navController = rememberAnimatedNavController()
    val context = LocalContext.current
    var shareContent by remember { mutableStateOf<ShareContent?>(null) }

    //将底部栏菜单数据的路由名整成一个list
    val routes = bottomNavScreenList.map { it.route }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val useDarkTheme = LocalDarkTheme.current.isDarkTheme()

    CompositionLocalProvider(LocalRootNavController provides navController) {
        AppTheme(useDarkTheme = useDarkTheme) {
            rememberSystemUiController().run {
                setStatusBarColor(Color.Transparent, !useDarkTheme)
                setSystemBarsColor(Color.Transparent, !useDarkTheme)
                setNavigationBarColor(Color.Transparent, !useDarkTheme)
            }
            Scaffold(bottomBar = {
                //只要当页面为首页,才展示底部菜单栏
                if (currentDestination?.hierarchy?.any { routes.contains(it.route) } == true) {
                    BottomNavigationBar(navController = navController)
                }
            }, content = { padding ->
                NavHostContainer(navController = navController, padding = padding, shareContent = shareContent)
            })
        }
    }

    fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_SEND, Intent.ACTION_SEND_MULTIPLE -> {
                shareContent = ShareContent.parseIntent(intent)
                navController.navigate(RouteName.SHARE)
            }
        }
    }

    LaunchedEffect(context) {
        if (context is ComponentActivity && context.intent != null) {
            handleIntent(context.intent)
        }
    }

    DisposableEffect(context) {
        val activity = context as? ComponentActivity

        val listener = Consumer<Intent> {
            handleIntent(it)
        }

        activity?.addOnNewIntentListener(listener)

        onDispose {
            activity?.removeOnNewIntentListener(listener)
        }
    }
}

val LocalRootNavController = compositionLocalOf<NavHostController> { error(com.ldlywt.memo.R.string.nav_host_controller_not_found.string) }


@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomNavScreenList.forEach { navItem ->
            NavigationBarItem(selected = currentRoute == navItem.route, onClick = {
                navController.navigate(navItem.route) {
                    //使用此方法,可以避免生成一个重复的路由堆栈
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    //避免重复选择会创建一个新的页面副本
                    launchSingleTop = true
                    //当重新选择之前已选择项目恢复页面状态
                    restoreState = true
                }
            }, icon = {
                Icon(imageVector = navItem.imageId, contentDescription = navItem.stringId.string)
            }, label = {
                Text(text = navItem.stringId.string)
            })
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHostContainer(
    navController: NavHostController, padding: PaddingValues, shareContent: ShareContent?
) {
    val context = LocalContext.current
    AnimatedNavHost(
        navController,
        startDestination = if (context.isFirstLaunch) RouteName.STARTUP else RouteName.MEMOS,
        Modifier
            .fillMaxWidth()
            .padding(bottom = padding.calculateBottomPadding())
    ) {

        // Startup
        animatedComposable(route = RouteName.STARTUP) {
            StartupPage(navController)
        }

        composable(RouteName.MEMOS) {
            MemosHomePage()
        }

        composable(RouteName.EXPLORE) {
            ExplorePage()
        }

        composable(RouteName.ARCHIVED) {
            ArchivedMemoList()
        }

        composable(RouteName.TAGLIST) {
            TagListPage()
        }

        animatedComposable("${RouteName.TAG}/{tag}") { entry ->
            TagMemoPage(tag = entry.arguments?.getString("tag"))
        }

        animatedComposable(RouteName.SETTINGS) {
            SettingsPage(navController = navController)
        }

        animatedComposable(RouteName.INPUT) {
            MemoInputPage()
        }

        animatedComposable(RouteName.SHARE) {
            MemoInputPage(shareContent = shareContent)
        }

        animatedComposable(
            "${RouteName.EDIT}?memoId={id}"
        ) { entry ->
            MemoInputPage(memoId = entry.arguments?.getString("id")?.toLong())
        }

        animatedComposable(RouteName.SEARCH) {
            SearchPage()
        }

        animatedComposable(RouteName.RESOURCE) {
            ResourceListPage(navController = navController)
        }

        animatedComposable(RouteName.DATA_LOCAL_MANAGER) {
            DataLocalManagerPage(navController = navController)
        }

        animatedComposable(RouteName.DATA_CLOUD_MANAGER) {
            DataCloudManagerPage(navController = navController)
        }

        // Color & Style
        animatedComposable(route = RouteName.COLOR_AND_STYLE) {
            ColorAndStylePage(navController)
        }
        animatedComposable(route = RouteName.DARK_THEME) {
            DarkThemePage(navController)
        }

        // Tips & Support
        animatedComposable(route = RouteName.TIPS_AND_SUPPORT) {
            AboutPage(navController)
        }
    }
}
