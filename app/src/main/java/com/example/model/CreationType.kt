package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ui.theme.ColorApkBg
import com.example.ui.theme.ColorApkBorder
import com.example.ui.theme.ColorApkPrimary
import com.example.ui.theme.ColorAppBg
import com.example.ui.theme.ColorAppBorder
import com.example.ui.theme.ColorAppPrimary
import com.example.ui.theme.ColorGameAppBg
import com.example.ui.theme.ColorGameAppBorder
import com.example.ui.theme.ColorGameAppPrimary
import com.example.ui.theme.ColorPcGameBg
import com.example.ui.theme.ColorPcGameBorder
import com.example.ui.theme.ColorPcGamePrimary
import com.example.ui.theme.ColorPcProgramBg
import com.example.ui.theme.ColorPcProgramBorder
import com.example.ui.theme.ColorPcProgramPrimary
import com.example.ui.theme.ColorWebsiteBg
import com.example.ui.theme.ColorWebsiteBorder
import com.example.ui.theme.ColorWebsitePrimary

enum class CreationType(
  val label: String,
  val subtitle: String,
  val icon: ImageVector,
  val primaryColor: Color,
  val backgroundColor: Color,
  val borderColor: Color,
  val tag: String
) {
  WEBSITE(
    label = "웹사이트",
    subtitle = "반응형 웹 & 호스팅",
    icon = Icons.Filled.Language,
    primaryColor = ColorWebsitePrimary,
    backgroundColor = ColorWebsiteBg,
    borderColor = ColorWebsiteBorder,
    tag = "HTML / Web"
  ),
  APP(
    label = "앱",
    subtitle = "iOS & Android 네이티브",
    icon = Icons.Filled.Smartphone,
    primaryColor = ColorAppPrimary,
    backgroundColor = ColorAppBg,
    borderColor = ColorAppBorder,
    tag = "Mobile Cross"
  ),
  APP_APK(
    label = "앱 APK",
    subtitle = "스마트폰 바로 설치 파일",
    icon = Icons.Filled.Android,
    primaryColor = ColorApkPrimary,
    backgroundColor = ColorApkBg,
    borderColor = ColorApkBorder,
    tag = "Direct APK"
  ),
  GAME_APP(
    label = "게임 앱",
    subtitle = "모바일 터치 아케이드 게임",
    icon = Icons.Filled.SportsEsports,
    primaryColor = ColorGameAppPrimary,
    backgroundColor = ColorGameAppBg,
    borderColor = ColorGameAppBorder,
    tag = "Mobile Game"
  ),
  PC_GAME(
    label = "컴퓨터 게임",
    subtitle = "PC/데스크톱 액션 게임",
    icon = Icons.Filled.VideogameAsset,
    primaryColor = ColorPcGamePrimary,
    backgroundColor = ColorPcGameBg,
    borderColor = ColorPcGameBorder,
    tag = "Desktop Game"
  ),
  PC_PROGRAM(
    label = "컴퓨터 프로그램",
    subtitle = "PC 유틸리티 & GUI 도구",
    icon = Icons.Filled.Computer,
    primaryColor = ColorPcProgramPrimary,
    backgroundColor = ColorPcProgramBg,
    borderColor = ColorPcProgramBorder,
    tag = "Desktop Software"
  )
}
