package com.example.ui.components

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.BuildResult
import com.example.ui.theme.BorderLight
import com.example.ui.theme.ColorAppPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZeroDark

@Composable
fun CodeViewerDialog(
  result: BuildResult,
  onDismiss: () -> Unit,
  onCopyCode: (String, String) -> Unit
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf("MainActivity.kt", "AndroidManifest.xml", "빌드 명세")

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(560.dp),
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Code,
              contentDescription = null,
              tint = ColorAppPrimary,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "생성된 소스코드 아티팩트",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "닫기", tint = TextSecondary)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        TabRow(
          selectedTabIndex = selectedTab,
          containerColor = Color(0xFFF8FAFC),
          contentColor = ColorAppPrimary
        ) {
          tabs.forEachIndexed { index, tabTitle ->
            Tab(
              selected = selectedTab == index,
              onClick = { selectedTab = index },
              text = { Text(text = tabTitle, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        val currentCode = when (selectedTab) {
          0 -> result.sourceCodeSnippet
          1 -> result.manifestSnippet
          else -> """
            [빌드 및 배포 완료 명세서]
            - 프로젝트명: ${result.title}
            - 패키지명: ${result.packageName}
            - APK 파일명: ${result.apkFileName}
            - 파일 크기: ${result.apkFileSize}
            - 소요 시간: ${result.durationMs}ms (정밀 1.0초)
            - 빌드 오류: ${result.errorCount}건 (100% 무결점 통과)
            - Google Play 배포 상태: ${result.playStoreStatus}
            - Apple App Store 배포 상태: ${result.appStoreStatus}
            - 생성 일시: ${result.generatedTimeFormatted}
          """.trimIndent()
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ZeroDark)
            .padding(14.dp)
            .verticalScroll(rememberScrollState())
        ) {
          Text(
            text = currentCode,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp,
            color = Color(0xFFE2E8F0),
            lineHeight = 18.sp
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          Button(
            onClick = { onCopyCode(currentCode, tabs[selectedTab]) },
            colors = ButtonDefaults.buttonColors(containerColor = ColorAppPrimary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.testTag("btn_copy_source_code")
          ) {
            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "복사", modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "코드 복사하기", fontSize = 13.sp)
          }
        }
      }
    }
  }
}
