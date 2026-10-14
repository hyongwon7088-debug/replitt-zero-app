package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderLight
import com.example.ui.theme.ColorWebsiteBg
import com.example.ui.theme.ColorWebsitePrimary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZeroAccent
import com.example.ui.theme.ZeroCyan
import com.example.ui.theme.ZeroPrimary

@Composable
fun ActionAndDeploySection(
  isGenerating: Boolean,
  onGenerateClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.02f,
    animationSpec = infiniteRepeatable(
      animation = tween(900, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseScale"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(SurfaceLight)
      .border(1.dp, BorderLight, RoundedCornerShape(20.dp))
      .padding(18.dp)
  ) {
    // Section Header
    Row(verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier = Modifier
          .size(26.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(ColorWebsiteBg),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "3",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = ColorWebsitePrimary
        )
      }
      Spacer(modifier = Modifier.width(10.dp))
      Text(
        text = "하단 실행 및 배포 구역",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // The Main Execution & Deploy Button: '1초 생성 및 스토어 무료 배포'
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .scale(if (isGenerating) 1f else pulseScale)
        .shadow(
          elevation = 8.dp,
          shape = RoundedCornerShape(18.dp),
          ambientColor = ZeroPrimary.copy(alpha = 0.3f),
          spotColor = ZeroPrimary.copy(alpha = 0.5f)
        )
        .clip(RoundedCornerShape(18.dp))
        .background(
          brush = Brush.horizontalGradient(
            colors = listOf(
              ZeroPrimary,
              Color(0xFF4338CA),
              ZeroAccent
            )
          )
        )
        .clickable(enabled = !isGenerating, onClick = onGenerateClick)
        .padding(vertical = 18.dp, horizontal = 16.dp)
        .testTag("btn_1sec_generate_and_deploy"),
      contentAlignment = Alignment.Center
    ) {
      if (isGenerating) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          CircularProgressIndicator(
            color = Color.White,
            modifier = Modifier.size(24.dp),
            strokeWidth = 2.5.dp
          )
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = "1초 초고속 생성 및 배포 중...",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
      } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.Bolt,
              contentDescription = "초고속 생성",
              tint = ZeroCyan,
              modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "1초 생성 및 스토어 무료 배포",
              fontSize = 18.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color.White,
              letterSpacing = (-0.3).sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
              imageVector = Icons.Default.RocketLaunch,
              contentDescription = "로켓 배포",
              tint = Color(0xFFFDE047),
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Google Play Store & Apple App Store 즉시 무료 배포 + APK 생성",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.9f)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Guarantee & Highlights
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      BenefitBadge(text = "1초 무오류 보장")
      BenefitBadge(text = "양대 스토어 무료 배포")
      BenefitBadge(text = "스마트폰 바로 설치 APK")
    }
  }
}

@Composable
private fun BenefitBadge(text: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(horizontal = 2.dp)
  ) {
    Icon(
      imageVector = Icons.Default.Check,
      contentDescription = null,
      tint = SuccessGreen,
      modifier = Modifier.size(13.dp)
    )
    Spacer(modifier = Modifier.width(3.dp))
    Text(
      text = text,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      color = TextSecondary
    )
  }
}
