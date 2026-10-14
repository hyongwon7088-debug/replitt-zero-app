package com.example.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BorderLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenBg
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZeroCyan
import com.example.ui.theme.ZeroDark
import com.example.ui.theme.ZeroPrimary

@Composable
fun ReplitZeroHeader(
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Top Brand Badge & Rocket Z Logo
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      // Rocket Z Logo Container
      Box(
        modifier = Modifier
          .size(56.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(ZeroDark)
          .border(
            width = 2.dp,
            brush = Brush.linearGradient(listOf(ZeroCyan, ZeroPrimary)),
            shape = RoundedCornerShape(16.dp)
          ),
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painterResource(id = R.drawable.ic_rocket_zero_1791944121172),
          contentDescription = "레플릿 제로 로켓 Z 로고",
          modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(12.dp)),
          contentScale = ContentScale.Crop
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "레플릿 제로",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary,
            letterSpacing = (-0.5).sp,
            modifier = Modifier.testTag("app_brand_title")
          )
          Spacer(modifier = Modifier.width(8.dp))
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(
                Brush.horizontalGradient(
                  listOf(ZeroPrimary, ZeroCyan)
                )
              )
              .padding(horizontal = 7.dp, vertical = 3.dp)
          ) {
            Text(
              text = "ZERO 1s",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = "초고속 원클릭 소프트웨어 생성 & 스토어 무료 배포",
          fontSize = 12.sp,
          color = TextSecondary,
          fontWeight = FontWeight.Medium
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Engine Status Pill
    Row(
      modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .background(SuccessGreenBg)
        .border(1.dp, BorderLight, RoundedCornerShape(20.dp))
        .padding(horizontal = 14.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(8.dp)
          .clip(CircleShape)
          .background(SuccessGreen)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "1초 무결점 생성 모드 가동 중 • Play/App Store 무료 배포 지원",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = SuccessGreen
      )
    }
  }
}
