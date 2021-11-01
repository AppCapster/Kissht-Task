package com.monika.kisshttask.ui.posters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.statusBarsPadding
import com.monika.kisshttask.model.Poster
import com.monika.kisshttask.ui.custom.StaggeredVerticalGrid
import com.monika.kisshttask.ui.theme.KisshtTaskTheme
import com.monika.kisshttask.utils.NetworkImage

@Composable
fun HomePosters(
    posters: List<Poster>,
    selectPoster: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
    ) {
        StaggeredVerticalGrid(
            maxColumnWidth = 220.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            posters.forEach { poster ->
                HomePoster(poster = poster, selectPoster = selectPoster)
            }
        }
    }
}

@Composable
private fun HomePoster(
    poster: Poster,
    selectPoster: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .clickable(
                onClick = { selectPoster(poster.id) }
            ),
        color = MaterialTheme.colors.onBackground,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        ConstraintLayout {
            val (image, title, content) = createRefs()
            NetworkImage(
                url = poster.urls.raw,
                modifier = Modifier
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }
                    .aspectRatio(0.8f)
            )
            Text(
                text = poster.user.name.toString(),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(title) {
                        centerHorizontallyTo(parent)
                        top.linkTo(image.bottom)
                    }
                    .padding(8.dp)
            )
            Text(
                text = poster.user.username.toString(),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(content) {
                        centerHorizontallyTo(parent)
                        top.linkTo(title.bottom)
                    }
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HomePosterPreviewLight() {
    KisshtTaskTheme(darkTheme = false) {
        HomePoster(
            poster = Poster.default(),
            selectPoster = {}
        )
    }
}

@Preview
@Composable
private fun HomePosterPreviewDark() {
    KisshtTaskTheme(darkTheme = true) {
        HomePoster(
            poster = Poster.default(),
            selectPoster = {}
        )
    }
}
