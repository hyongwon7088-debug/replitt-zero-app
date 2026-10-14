package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderLight
import com.example.ui.theme.ColorAppBg
import com.example.ui.theme.ColorAppPrimary
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.ZeroCyan
import com.example.ui.theme.ZeroPrimary

@Composable
fun GenerationProgressCard(
  progress: Float,
  currentStepText: String,
  stepIndex: Int,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = progress,
    label = "buildProgress"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(SurfaceLight)
      .border(1.5.dp, ZeroPrimary, RoundedCornerShape(20.dp))
      .padding(18.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.Speed,
          contentDescription = "초고속 엔진",
          tint = ZeroPrimary,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "1초 초고속 빌드 & 배포 엔진 가동 중",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(ColorAppBg)
          .padding(horizontal = 8.dp, vertical = 3.dp)
      ) {
        Text(
          text = "${(animatedProgress * 100).toInt()}%",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = ColorAppPrimary
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    LinearProgressIndicator(
      progress = { animatedProgress },
      modifier = Modifier
        .fillMaxWidth()
        .height(8.dp)
        .clip(RoundedCornerShape(4.dp)),
      color = ZeroPrimary,
      trackColor = ColorAppBg
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
      text = currentStepText,
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      color = TextPrimary,
      lineHeight = 18.sp
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Step dots
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      for (i in 1..4) {
        val isDone = i <= stepIndex
        Box(
          modifier = Modifier
            .weight(1f)
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(if (isDone) ZeroCyan else BorderLight)
        )
      }
    }
  }
}
