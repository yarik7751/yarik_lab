package com.joy.yariklab.features.common.logo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joy.yariklab.R
import com.joy.yariklab.ui.theme.Pink80
import com.joy.yariklab.uikit.simplePadding

@Composable
fun StartTitle(
    centerModifier: Modifier.() -> Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .centerModifier(),
    ) {
        Text(
            modifier = Modifier
                .simplePadding(
                    horizontal = 8.dp,
                    bottom = 8.dp,
                )
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.start_title),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .simplePadding(
                    horizontal = 8.dp,
                    bottom = 8.dp,
                )
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.start_title_sense),
            fontSize = 25.sp,
            color = Pink80
        )
    }
}