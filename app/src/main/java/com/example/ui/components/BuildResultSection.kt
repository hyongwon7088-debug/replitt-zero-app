package com.example.ui.components

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
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BuildResult
import com.example.ui.theme.AppleStoreBlue
import com.example.ui.theme.BorderLight
import com.example.ui.theme.ColorApkBg
import com.example.ui.theme.ColorApkPrimary
import com.example.ui.theme.ColorAppBg
import com.example.ui.theme.ColorAppPrimary
import com.example.ui.theme.GooglePlayGreen
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenBg
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZeroPrimary

@Composable
fun BuildResultSection(
  result: BuildResult,
  isApkDownloaded: Boolean,
  onDownloadApk: () -> Unit,
  onOpenSimulator: () -> Unit,
  onOpenCodeViewer: () -> Unit,
  onCopyLink: (String, String) -> Unit,
  onReset: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
    // 1. Success Congratulatory Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(SuccessGreenBg)
        .border(1.5.dp, SuccessGreen.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
        .padding(18.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(SuccessGreen),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "성공",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
          )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
          Text(
            text = "1초 생성 및 스토어 무료 배포 성공!",
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            color = SuccessGreen
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "소요 시간: 1.0초 • 빌드 오류: 0건 • 스마트폰 설치 준비 완료",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 2. 스마트폰 바로 설치 APK 카드 (핵심 요구사항)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(SurfaceLight)
        .border(1.5.dp, ColorApkPrimary, RoundedCornerShape(20.dp))
        .padding(18.dp)
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(ColorApkBg),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Android,
                contentDescription = "APK 설치 파일",
                tint = ColorApkPrimary,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "스마트폰 바로 설치 APK",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = "${result.apkFileName} (${result.apkFileSize})",
                fontSize = 12.sp,
                color = TextSecondary
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(ColorApkBg)
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = result.version,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = ColorApkPrimary
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // APK Download Action Button
        Button(
          onClick = onDownloadApk,
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("btn_download_apk"),
          colors = ButtonDefaults.buttonColors(containerColor = ColorApkPrimary),
          shape = RoundedCornerShape(14.dp)
        ) {
          Icon(
            imageVector = if (isApkDownloaded) Icons.Default.InstallMobile else Icons.Default.Download,
            contentDescription = "APK 다운로드",
            tint = Color.White
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = if (isApkDownloaded) "APK 다운로드 완료 (스마트폰 즉시 설치)" else "스마트폰 바로 설치 APK 다운로드",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
        }

        if (isApkDownloaded) {
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(SuccessGreenBg)
              .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = SuccessGreen,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "패키지 다운로드가 완료되었습니다. 탭하여 설치를 시작하세요.",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = SuccessGreen
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Interactive Live Simulator Button
        OutlinedButton(
          onClick = onOpenSimulator,
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("btn_open_simulator"),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = ZeroPrimary),
          shape = RoundedCornerShape(14.dp)
        ) {
          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "라이브 실행",
            tint = ZeroPrimary
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "생성된 프로그램 바로 실행 (실시간 테스트)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 3. 구글 플레이 스토어 & 애플 앱스토어 무료 배포 현황
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(SurfaceLight)
        .border(1.dp, BorderLight, RoundedCornerShape(20.dp))
        .padding(18.dp)
    ) {
      Column {
        Text(
          text = "스토어 무료 배포 현황 (자동 등록 완료)",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Google Play Store
        StoreRow(
          storeName = "Google Play Store",
          status = result.playStoreStatus,
          accentColor = GooglePlayGreen,
          icon = Icons.Default.Shop,
          onCopyLink = { onCopyLink(result.playStoreUrl, "Google Play Store") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Apple App Store
        StoreRow(
          storeName = "Apple App Store",
          status = result.appStoreStatus,
          accentColor = AppleStoreBlue,
          icon = Icons.Default.PhoneIphone,
          onCopyLink = { onCopyLink(result.appStoreUrl, "Apple App Store") }
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 4. 소스코드 확인 & 초기화 버튼
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      OutlinedButton(
        onClick = onOpenCodeViewer,
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("btn_view_code"),
        shape = RoundedCornerShape(12.dp)
      ) {
        Icon(imageVector = Icons.Default.Code, contentDescription = "코드 보기", tint = TextSecondary)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = "소스코드 보기", color = TextSecondary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
      }

      Button(
        onClick = onReset,
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("btn_reset_new"),
        colors = ButtonDefaults.buttonColors(containerColor = ColorAppBg),
        shape = RoundedCornerShape(12.dp)
      ) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "새로 만들기", tint = ColorAppPrimary)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = "새로 만들기", color = ColorAppPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}

@Composable
private fun StoreRow(
  storeName: String,
  status: String,
  accentColor: Color,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  onCopyLink: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(accentColor.copy(alpha = 0.08f))
      .border(1.dp, accentColor.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
      .padding(horizontal = 12.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(accentColor),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = storeName,
          tint = Color.White,
          modifier = Modifier.size(18.dp)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column {
        Text(
          text = storeName,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
        Text(
          text = status,
          fontSize = 11.sp,
          color = accentColor,
          fontWeight = FontWeight.Medium
        )
      }
    }

    IconButton(
      onClick = onCopyLink,
      modifier = Modifier.size(36.dp)
    ) {
      Icon(
        imageVector = Icons.Default.ContentCopy,
        contentDescription = "링크 복사",
        tint = accentColor,
        modifier = Modifier.size(18.dp)
      )
    }
  }
}
