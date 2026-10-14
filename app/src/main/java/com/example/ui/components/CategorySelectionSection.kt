package com.example.ui.components

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CreationType
import com.example.ui.theme.BorderLight
import com.example.ui.theme.ColorApkBg
import com.example.ui.theme.ColorApkPrimary
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CategorySelectionSection(
  selectedType: CreationType,
  onTypeSelected: (CreationType) -> Unit,
  modifier: Modifier = Modifier
) {
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
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(26.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ColorApkBg),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "2",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = ColorApkPrimary
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "중간 선택 구역 (6가지 생성 모드)",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
      }

      Text(
        text = "택 1",
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextMuted
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 6 Distinctive, colorful buttons in 3 rows x 2 columns
    val types = CreationType.entries
    val chunked = types.chunked(2)

    chunked.forEachIndexed { index, rowTypes ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        rowTypes.forEach { creationType ->
          CategoryButton(
            type = creationType,
            isSelected = creationType == selectedType,
            onClick = { onTypeSelected(creationType) },
            modifier = Modifier.weight(1f)
          )
        }
      }
      if (index < chunked.lastIndex) {
        Spacer(modifier = Modifier.height(10.dp))
      }
    }
  }
}

@Composable
private fun CategoryButton(
  type: CreationType,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val animatedBgColor by animateColorAsState(
    targetValue = if (isSelected) type.backgroundColor else Color.White,
    label = "btnBgColor"
  )
  val animatedBorderColor by animateColorAsState(
    targetValue = if (isSelected) type.primaryColor else type.borderColor,
    label = "btnBorderColor"
  )

  val tagSlug = when (type) {
    CreationType.WEBSITE -> "website"
    CreationType.APP -> "app"
    CreationType.APP_APK -> "app_apk"
    CreationType.GAME_APP -> "game_app"
    CreationType.PC_GAME -> "pc_game"
    CreationType.PC_PROGRAM -> "pc_program"
  }

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .background(animatedBgColor)
      .border(
        width = if (isSelected) 2.dp else 1.2.dp,
        color = animatedBorderColor,
        shape = RoundedCornerShape(16.dp)
      )
      .clickable(onClick = onClick)
      .padding(horizontal = 12.dp, vertical = 12.dp)
      .testTag("btn_category_$tagSlug")
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Icon Box with individual color
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) type.primaryColor else type.backgroundColor),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = type.icon,
            contentDescription = type.label,
            tint = if (isSelected) Color.White else type.primaryColor,
            modifier = Modifier.size(22.dp)
          )
        }

        // Selection Indicator Check
        if (isSelected) {
          Box(
            modifier = Modifier
              .size(22.dp)
              .clip(CircleShape)
              .background(type.primaryColor),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = "선택됨",
              tint = Color.White,
              modifier = Modifier.size(16.dp)
            )
          }
        } else {
          Box(
            modifier = Modifier
              .size(18.dp)
              .clip(CircleShape)
              .border(1.5.dp, type.borderColor, CircleShape)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = type.label,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = if (isSelected) type.primaryColor else TextPrimary
      )

      Spacer(modifier = Modifier.height(2.dp))

      Text(
        text = type.subtitle,
        fontSize = 11.sp,
        color = if (isSelected) TextSecondary else TextMuted,
        maxLines = 1
      )
    }
  }
}
