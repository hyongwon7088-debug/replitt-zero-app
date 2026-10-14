package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CreationType
import com.example.ui.theme.BorderLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ColorAppBg
import com.example.ui.theme.ColorAppPrimary
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZeroPrimary

private data class QuickIdea(
  val title: String,
  val desc: String,
  val type: CreationType
)

private val SAMPLE_IDEAS = listOf(
  QuickIdea("우주 로켓 액션 게임", "스페이스 로켓을 조종하여 행성과 운석을 피하고 점수를 획득하는 모바일 터치 아케이드 게임", CreationType.GAME_APP),
  QuickIdea("스마트 할일 관리 앱", "오늘의 일정, 중요도 분류, 완료 체크 및 알림을 지원하는 깔끔한 데일리 투두 앱", CreationType.APP_APK),
  QuickIdea("나만의 포트폴리오 웹", "반응형 소개 페이지, 프로젝트 쇼케이스, 문의하기 폼이 포함된 모던 웹사이트", CreationType.WEBSITE),
  QuickIdea("PC 시스템 리소스 모니터", "CPU, 메모리, 네트워크 속도를 실시간으로 감시하는 데스크톱 유틸리티 프로그램", CreationType.PC_PROGRAM)
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputSection(
  title: String,
  description: String,
  onTitleChanged: (String) -> Unit,
  onDescriptionChanged: (String) -> Unit,
  onApplySuggestion: (String, String, CreationType) -> Unit,
  modifier: Modifier = Modifier
) {
  val focusManager = LocalFocusManager.current

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
          .background(ColorAppBg),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "1",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = ColorAppPrimary
        )
      }
      Spacer(modifier = Modifier.width(10.dp))
      Text(
        text = "상단 메인 입력 구역",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // 1. 프로그램 제목 Input Field
    Text(
      text = "프로그램 제목",
      fontSize = 14.sp,
      fontWeight = FontWeight.SemiBold,
      color = TextPrimary
    )
    Spacer(modifier = Modifier.height(6.dp))

    OutlinedTextField(
      value = title,
      onValueChange = onTitleChanged,
      placeholder = {
        Text(
          text = "예: 우주 로켓 액션 게임, 스마트 메모장, 모던 쇼핑몰",
          color = TextMuted,
          fontSize = 14.sp
        )
      },
      leadingIcon = {
        Icon(
          imageVector = Icons.Default.Title,
          contentDescription = "프로그램 제목 아이콘",
          tint = ZeroPrimary
        )
      },
      trailingIcon = {
        if (title.isNotEmpty()) {
          IconButton(onClick = { onTitleChanged("") }) {
            Icon(
              imageVector = Icons.Default.Clear,
              contentDescription = "제목 지우기",
              tint = TextMuted
            )
          }
        }
      },
      singleLine = true,
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = ZeroPrimary,
        unfocusedBorderColor = BorderSubtle,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
      ),
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("input_program_title")
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Quick Inspiration Chips
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(bottom = 6.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Lightbulb,
        contentDescription = "추천 예시",
        tint = ZeroPrimary,
        modifier = Modifier.size(15.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = "빠른 예시 선택 (터치 시 자동 입력):",
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = TextSecondary
      )
    }

    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      SAMPLE_IDEAS.forEach { idea ->
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(ColorAppBg)
            .border(1.dp, BorderLight, RoundedCornerShape(8.dp))
            .clickable {
              onApplySuggestion(idea.title, idea.desc, idea.type)
            }
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Text(
            text = idea.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = ColorAppPrimary
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // 2. 상세 내용 및 기능 Input Field
    Text(
      text = "상세 내용 및 기능",
      fontSize = 14.sp,
      fontWeight = FontWeight.SemiBold,
      color = TextPrimary
    )
    Spacer(modifier = Modifier.height(6.dp))

    OutlinedTextField(
      value = description,
      onValueChange = onDescriptionChanged,
      placeholder = {
        Text(
          text = "구현하고 싶은 핵심 기능, 동작 방식, 디자인 스타일 등을 입력하세요.\n(예: 터치 조작, 실시간 점수 랭킹, 사운드 이펙트, 다크/라이트 테마 지원 등)",
          color = TextMuted,
          fontSize = 13.sp,
          lineHeight = 18.sp
        )
      },
      leadingIcon = {
        Icon(
          imageVector = Icons.Default.Description,
          contentDescription = "상세 내용 및 기능 아이콘",
          tint = ZeroPrimary
        )
      },
      minLines = 3,
      maxLines = 6,
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
      keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = ZeroPrimary,
        unfocusedBorderColor = BorderSubtle,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
      ),
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("input_program_description")
    )
  }
}
