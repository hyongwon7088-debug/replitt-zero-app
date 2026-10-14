package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.BuildResult
import com.example.model.CreationType
import com.example.ui.theme.BorderLight
import com.example.ui.theme.ColorAppBg
import com.example.ui.theme.ColorAppPrimary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZeroCyan
import com.example.ui.theme.ZeroDark
import com.example.ui.theme.ZeroPrimary

@Composable
fun InteractiveAppSimulator(
  result: BuildResult,
  onDismiss: () -> Unit,
  onInstallApk: () -> Unit
) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.75f))
        .padding(16.dp),
      contentAlignment = Alignment.Center
    ) {
      // Mobile Device Frame Mockup
      Card(
        modifier = Modifier
          .fillMaxWidth(0.96f)
          .height(680.dp)
          .clip(RoundedCornerShape(36.dp))
          .border(4.dp, Color(0xFF1E293B), RoundedCornerShape(36.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
      ) {
        Column(modifier = Modifier.fillMaxSize()) {
          // Device Speaker Notch & Status Bar
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(Color(0xFF0F172A))
              .padding(horizontal = 20.dp, vertical = 8.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(text = "09:41", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)

              // Camera Notch
              Box(
                modifier = Modifier
                  .size(width = 70.dp, height = 12.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(Color(0xFF1E293B))
              )

              Text(text = "5G  100%", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
          }

          // Top App Bar of the Simulated App
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .background(Color.White)
              .border(1.dp, BorderLight)
              .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(result.type.backgroundColor),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = result.type.icon,
                  contentDescription = null,
                  tint = result.type.primaryColor,
                  modifier = Modifier.size(18.dp)
                )
              }
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = result.title,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextPrimary
                )
                Text(
                  text = "실시간 라이브 구동 중 • ${result.type.label}",
                  fontSize = 11.sp,
                  color = SuccessGreen,
                  fontWeight = FontWeight.Medium
                )
              }
            }

            IconButton(
              onClick = onDismiss,
              modifier = Modifier.size(36.dp)
            ) {
              Icon(imageVector = Icons.Default.Close, contentDescription = "닫기", tint = TextSecondary)
            }
          }

          // Dynamic Interactive Body according to CreationType
          Box(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth()
          ) {
            when (result.type) {
              CreationType.GAME_APP, CreationType.PC_GAME -> {
                PlayableMiniGame(result = result)
              }
              CreationType.APP, CreationType.APP_APK -> {
                PlayableInteractiveApp(result = result)
              }
              CreationType.WEBSITE, CreationType.PC_PROGRAM -> {
                PlayableWebOrProgram(result = result)
              }
            }
          }

          // Bottom Bar of the Simulator
          Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(text = result.apkFileName, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(text = "19.2 MB • 스마트폰 바로 설치", fontSize = 11.sp, color = TextMuted)
              }

              Button(
                onClick = onInstallApk,
                colors = ButtonDefaults.buttonColors(containerColor = ColorAppPrimary),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("btn_simulator_install")
              ) {
                Text(text = "스마트폰에 설치", fontSize = 13.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}

// 1. Playable Interactive Mini Game for GAME_APP & PC_GAME
@Composable
private fun PlayableMiniGame(result: BuildResult) {
  var score by remember { mutableIntStateOf(0) }
  var rocketY by remember { mutableFloatStateOf(200f) }
  var starsCollected by remember { mutableIntStateOf(0) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(ZeroDark)
      .pointerInput(Unit) {
        detectTapGestures {
          score += 10
          starsCollected += 1
          rocketY = (rocketY - 40f).coerceAtLeast(60f)
        }
      }
  ) {
    // Starfield Canvas
    Canvas(modifier = Modifier.fillMaxSize()) {
      drawCircle(Color(0xFF38BDF8), radius = 3f, center = Offset(size.width * 0.2f, size.height * 0.15f))
      drawCircle(Color(0xFFFDE047), radius = 4f, center = Offset(size.width * 0.7f, size.height * 0.3f))
      drawCircle(Color(0xFF818CF8), radius = 2.5f, center = Offset(size.width * 0.85f, size.height * 0.6f))
      drawCircle(Color(0xFFF472B6), radius = 3.5f, center = Offset(size.width * 0.15f, size.height * 0.75f))
    }

    // Top Game HUD
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .background(Color.White.copy(alpha = 0.15f))
          .padding(horizontal = 12.dp, vertical = 6.dp)
      ) {
        Text(
          text = "SCORE: $score",
          fontSize = 15.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFFFDE047)
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .background(Color.White.copy(alpha = 0.15f))
          .padding(horizontal = 12.dp, vertical = 6.dp)
      ) {
        Text(
          text = "⭐ 별 수집: $starsCollected",
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
      }
    }

    // Rocket Player
    Box(
      modifier = Modifier
        .align(Alignment.Center)
        .padding(bottom = 60.dp),
      contentAlignment = Alignment.Center
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
          modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(ZeroPrimary.copy(alpha = 0.3f))
            .border(2.dp, ZeroCyan, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.RocketLaunch,
            contentDescription = "플레이어 로켓",
            tint = ZeroCyan,
            modifier = Modifier.size(42.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "👆 화면을 터치하여 비행 & 점수 획득!",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = result.description,
          fontSize = 12.sp,
          color = Color.White.copy(alpha = 0.7f),
          modifier = Modifier.padding(horizontal = 24.dp)
        )
      }
    }
  }
}

// 2. Playable Interactive App for APP & APP_APK
@Composable
private fun PlayableInteractiveApp(result: BuildResult) {
  var likes by remember { mutableIntStateOf(24) }
  var isLiked by remember { mutableStateOf(false) }
  val tasks = remember {
    mutableStateListOf(
      "레플릿 제로 무결점 엔진 구동 확인" to true,
      "스마트폰 APK 다운로드 링크 생성" to true,
      "구글 플레이 스토어 자동 등록" to true,
      "사용자 맞춤형 기능 테스트" to false
    )
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    // Welcome Banner
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "환영합니다! 🚀",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = result.description,
          fontSize = 13.sp,
          color = TextSecondary,
          lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(if (isLiked) Color(0xFFFFE4E6) else Color(0xFFF1F5F9))
              .clickable {
                isLiked = !isLiked
                likes += if (isLiked) 1 else -1
              }
              .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.ThumbUp,
              contentDescription = "좋아요",
              tint = if (isLiked) Color(0xFFE11D48) else Color(0xFF475569),
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "좋아요 $likes",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = if (isLiked) Color(0xFFE11D48) else TextPrimary
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(SuccessGreen.copy(alpha = 0.1f))
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Text(text = "상태: 정상 작동", fontSize = 12.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Interactive Task Checklist
    Text(
      text = "프로그램 주요 기능 체크리스트",
      fontSize = 15.sp,
      fontWeight = FontWeight.Bold,
      color = TextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))

    tasks.forEachIndexed { index, (taskTitle, isDone) ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              tasks[index] = taskTitle to !isDone
            }
            .padding(horizontal = 12.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Checkbox(
            checked = isDone,
            onCheckedChange = { tasks[index] = taskTitle to it }
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = taskTitle,
            fontSize = 13.sp,
            fontWeight = if (isDone) FontWeight.Medium else FontWeight.SemiBold,
            color = if (isDone) TextMuted else TextPrimary
          )
        }
      }
    }
  }
}

// 3. Playable Web / PC Program view
@Composable
private fun PlayableWebOrProgram(result: BuildResult) {
  var activeTab by remember { mutableIntStateOf(0) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF1F5F9))
      .padding(14.dp)
  ) {
    // Browser / Window Bar
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(Color.White)
        .border(1.dp, BorderLight, RoundedCornerShape(10.dp))
        .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
      Text(
        text = "https://zero.app/${result.title.replace("\\s+".toRegex(), "").lowercase()}",
        fontSize = 12.sp,
        color = TextSecondary,
        fontWeight = FontWeight.Medium
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Tabs
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      listOf("개요", "핵심 대시보드", "라이브 설정").forEachIndexed { index, tabTitle ->
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (activeTab == index) ZeroPrimary else Color.White)
            .clickable { activeTab = index }
            .padding(vertical = 8.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = tabTitle,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (activeTab == index) Color.White else TextPrimary
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    Card(
      modifier = Modifier.fillMaxSize(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
      ) {
        Text(
          text = "${result.title} 실행 콘솔",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = result.description,
          fontSize = 13.sp,
          color = TextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF0F172A))
            .padding(14.dp)
        ) {
          Column {
            Text(text = "$ status: ACTIVE 200 OK", fontSize = 12.sp, color = Color(0xFF38BDF8))
            Text(text = "$ engine: Replit Zero v1.0", fontSize = 12.sp, color = Color(0xFF4ADE80))
            Text(text = "$ packages: android_arm64, web_v8", fontSize = 12.sp, color = Color.White)
            Text(text = "$ ready: true", fontSize = 12.sp, color = Color(0xFFFDE047))
          }
        }
      }
    }
  }
}
