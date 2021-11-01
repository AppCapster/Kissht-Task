package com.monika.kisshttask.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.monika.kisshttask.ui.theme.shimmerHighLight
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.palette.BitmapPalette

@Preview
@Composable
fun NetworkImage(
    @PreviewParameter(NetworkUrlPreviewProvider::class) url: String?,
    modifier: Modifier = Modifier,
    circularRevealedEnabled: Boolean = false,
    contentScale: ContentScale = ContentScale.Crop,
    bitmapPalette: BitmapPalette? = null
) {
    CoilImage(
        imageModel = url,
        modifier = modifier,
        contentScale = contentScale,
        circularReveal = CircularReveal(duration = 250),
        bitmapPalette = bitmapPalette,
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.background,
            highlightColor = shimmerHighLight,
            dropOff = 0.65f
        ),
        failure = {
            Text(
                text = "image request failed.",
                style = MaterialTheme.typography.body2
            )
        }
    )
}
