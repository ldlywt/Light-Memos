package com.ldlywt.memo.ui.page.settings

import android.view.HapticFeedbackConstants
import android.view.SoundEffectConstants
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Assignment
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material.icons.rounded.TipsAndUpdates
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ldlywt.memo.Constant
import com.ldlywt.memo.R
import com.ldlywt.memo.theme.palette.alwaysLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutPage(navController: NavHostController) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.about)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back))
                }
            },
            scrollBehavior = scrollBehavior
        )
    }) { innerPadding ->
        AboutComposeScreen(contentPadding = innerPadding)
    }

}

@Composable
fun AboutComposeScreen(contentPadding: PaddingValues) {
    val view = LocalView.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.mipmap.ic_launcher),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .padding(top = 40.dp)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = R.string.contact),
            modifier = Modifier.padding(top = 24.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = R.string.author),
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            text = stringResource(id = R.string.qq),
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            text = stringResource(id = R.string.email),
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            text = stringResource(id = R.string.about_icon),
            modifier = Modifier.padding(top = 24.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "https://www.iconfont.cn/",
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            text = "https://www.flaticon.com/",
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            text = "https://iconpark.oceanengine.com/",
            modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.height(100.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundIconButton(RoundIconButtonType.Sponsor(
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer alwaysLight true,
            ) {
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                view.playSoundEffect(SoundEffectConstants.CLICK)

            })
            Spacer(modifier = Modifier.width(16.dp))

            RoundIconButton(RoundIconButtonType.UserAgree(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer alwaysLight true,
            ) {
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                view.playSoundEffect(SoundEffectConstants.CLICK)
                Constant.startUserAgreeUrl(context)
            })
            Spacer(modifier = Modifier.width(16.dp))

            RoundIconButton(RoundIconButtonType.Privacy(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer alwaysLight true,
            ) {
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                view.playSoundEffect(SoundEffectConstants.CLICK)
                Constant.startPrivacyUrl(context)
            })
            Spacer(modifier = Modifier.width(16.dp))

            // Help
            RoundIconButton(RoundIconButtonType.Help(
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer alwaysLight true,
            ) {
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                view.playSoundEffect(SoundEffectConstants.CLICK)
//                context.showToast(context.getString(R.string.coming_soon))
            })
        }
    }
}

@Immutable
sealed class RoundIconButtonType(
    val iconVector: ImageVector,
    val descResource: Int,
    open val size: Dp = 24.dp,
    open val offset: Modifier = Modifier.offset(),
    open val backgroundColor: Color = Color.Unspecified,
    open val onClick: () -> Unit = {},
) {

    @Immutable
    data class Sponsor(
        val desc: Int = R.string.sponsor,
        override val backgroundColor: Color,
        override val onClick: () -> Unit = {},
    ) : RoundIconButtonType(
        iconVector = Icons.Rounded.VolunteerActivism,
        descResource = desc,
        backgroundColor = backgroundColor,
        onClick = onClick,
    )

    @Immutable
    data class UserAgree(
        val desc: Int = R.string.user_agree,
        override val backgroundColor: Color,
        override val onClick: () -> Unit = {},
    ) : RoundIconButtonType(
        iconVector = Icons.Rounded.Assignment,
        descResource = desc,
        backgroundColor = backgroundColor,
        onClick = onClick,
    )

    @Immutable
    data class Privacy(
        val desc: Int = R.string.privacy_policy,
        override val offset: Modifier = Modifier.offset(x = (-1).dp),
        override val backgroundColor: Color,
        override val onClick: () -> Unit = {},
    ) : RoundIconButtonType(
        iconVector = Icons.Rounded.PrivacyTip,
        descResource = desc,
        backgroundColor = backgroundColor,
        onClick = onClick,
    )

    @Immutable
    data class Help(
        val desc: Int = R.string.help,
        override val offset: Modifier = Modifier.offset(x = (3).dp),
        override val backgroundColor: Color,
        override val onClick: () -> Unit = {},
    ) : RoundIconButtonType(
        iconVector = Icons.Rounded.TipsAndUpdates,
        descResource = desc,
        backgroundColor = backgroundColor,
        onClick = onClick,
    )
}

@Composable
private fun RoundIconButton(type: RoundIconButtonType) {
    IconButton(
        modifier = Modifier
            .size(70.dp)
            .background(
                color = type.backgroundColor,
                shape = CircleShape,
            ),
        onClick = { type.onClick() }
    ) {
        Icon(
            modifier = type.offset.size(type.size),
            imageVector = type.iconVector!!,
            contentDescription = stringResource(type.descResource!!),
            tint = MaterialTheme.colorScheme.onSurface alwaysLight true,
        )
    }
}


