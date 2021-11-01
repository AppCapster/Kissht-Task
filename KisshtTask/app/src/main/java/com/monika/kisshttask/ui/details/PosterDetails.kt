package com.monika.kisshttask.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.monika.kisshttask.model.Poster
import com.monika.kisshttask.utils.NetworkImage

@Composable
fun PosterDetails(
    posterId: String,
    viewModel: DetailViewModel,
    pressOnBack: () -> Unit
) {
    LaunchedEffect(key1 = posterId) {
        viewModel.loadPosterById(posterId)
    }

    val details: Poster? by viewModel.posterDetailsFlow.collectAsState(initial = null)
    details?.let { poster ->
        PosterDetailsBody(poster, pressOnBack)
    }
}

@Composable
private fun PosterDetailsBody(
    poster: Poster,
    pressOnBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
            .fillMaxHeight()
    ) {

        ConstraintLayout {
            val (arrow, image, paletteRow, title, content) = createRefs()

            NetworkImage(
                url = poster.urls.raw,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                    }
                    .fillMaxWidth()
                    .aspectRatio(0.85f),
                circularRevealedEnabled = true
            )


            Text(
                text = poster.user.name.toString(),
                style = MaterialTheme.typography.h1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(paletteRow.bottom)
                    }
                    .padding(start = 16.dp, top = 12.dp)
            )

            Text(
                text = poster.alt_description.toString(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(title.bottom)
                    }
                    .padding(16.dp)
            )

            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(arrow) {
                        top.linkTo(parent.top)
                    }
                    .padding(12.dp)
                    .clickable(onClick = { pressOnBack() })
            )
        }
    }
}
