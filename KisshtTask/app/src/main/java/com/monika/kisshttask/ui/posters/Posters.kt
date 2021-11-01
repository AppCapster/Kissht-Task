package com.monika.kisshttask.ui.posters

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.monika.kisshttask.R
import com.monika.kisshttask.extensions.visible
import com.monika.kisshttask.model.Poster
import com.monika.kisshttask.ui.jetpack.JetpackViewModel
import com.monika.kisshttask.ui.theme.Purple500

@Composable
fun Posters(
    viewModel: JetpackViewModel,
    selectPoster: (String) -> Unit
) {
    val posters: List<Poster> by viewModel.posterList.collectAsState(initial = listOf())
    val isLoading: Boolean by viewModel.isLoading
    val selectedTab = JetpackHomeTab.getTabFromResource(viewModel.selectedTab.value)

    ConstraintLayout {
        val (body, progress) = createRefs()
        Scaffold(
            backgroundColor = MaterialTheme.colors.primarySurface,
            topBar = { PosterAppBar() },
            modifier = Modifier.constrainAs(body) {
                top.linkTo(parent.top)
            }
        ) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            Crossfade(selectedTab) { destination ->
                when (destination) {
                    JetpackHomeTab.HOME -> HomePosters(posters, selectPoster, modifier)
                }
            }
        }
        CircularProgressIndicator(
            modifier = Modifier
                .constrainAs(progress) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .visible(isLoading)
        )
    }
}

@Preview
@Composable
private fun PosterAppBar() {
    TopAppBar(
        elevation = 6.dp,
        backgroundColor = Purple500,
        modifier = Modifier.height(58.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(R.string.app_name),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

enum class JetpackHomeTab(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    HOME(R.string.menu_home, Icons.Filled.Home);

    companion object {
        fun getTabFromResource(@StringRes resource: Int): JetpackHomeTab {
            return HOME
        }
    }
}
