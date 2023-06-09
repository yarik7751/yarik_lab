package com.joy.yariklab.uikit

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
fun Modifier.simplePadding(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
    vertical: Dp = 0.dp,
    horizontal: Dp = 0.dp,
): Modifier {
    if ((start != 0.dp || end != 0.dp) && horizontal != 0.dp) {
        throw IllegalArgumentException("Start and End OR Horizontal!")
    }

    if ((top != 0.dp || bottom != 0.dp) && vertical != 0.dp) {
        throw IllegalArgumentException("Top and Bottom OR Vertical!")
    }

    var startValue = start
    var endValue = end

    if (horizontal != 0.dp) {
        startValue = horizontal
        endValue = horizontal
    }

    var topValue = top
    var bottomValue = bottom

    if (vertical != 0.dp) {
        topValue = vertical
        bottomValue = vertical
    }
    return this.then(
        Modifier.padding(
            start = startValue,
            top = topValue,
            end = endValue,
            bottom = bottomValue,
        )
    )
}