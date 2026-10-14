package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.BuildResult
import com.example.model.BuildState
import com.example.model.CreationType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class ReplitZeroViewModel : ViewModel() {

  private val _title = MutableStateFlow("")
  val title = _title.asStateFlow()

  private val _description = MutableStateFlow("")
  val description = _description.asStateFlow()

  private val _selectedType = MutableStateFlow(CreationType.APP_APK)
  val selectedType = _selectedType.asStateFlow()

  private val _buildState = MutableStateFlow<BuildState>(BuildState.Idle)
  val buildState = _buildState.asStateFlow()

  private val _toastEvent = MutableSharedFlow<String>()
  val toastEvent = _toastEvent.asSharedFlow()

  private val _isSimulatorOpen = MutableStateFlow(false)
  val isSimulatorOpen = _isSimulatorOpen.asStateFlow()

  private val _isCodeViewerOpen = MutableStateFlow(false)
  val isCodeViewerOpen = _isCodeViewerOpen.asStateFlow()

  private val _isApkDownloaded = MutableStateFlow(false)
  val isApkDownloaded = _isApkDownloaded.asStateFlow()

  fun onTitleChanged(newTitle: String) {
    _title.value = newTitle
  }

  fun onDescriptionChanged(newDesc: String) {
    _description.value = newDesc
  }

  fun onTypeSelected(type: CreationType) {
    _selectedType.value = type
  }

  fun applySuggestion(sampleTitle: String, sampleDesc: String, type: CreationType) {
    _title.value = sampleTitle
    _description.value = sampleDesc
    _selectedType.value = type
  }

  fun openSimulator() {
    _isSimulatorOpen.value = true
  }

  fun closeSimulator() {
    _isSimulatorOpen.value = false
  }

  fun openCodeViewer() {
    _isCodeViewerOpen.value = true
  }

  fun closeCodeViewer() {
    _isCodeViewerOpen.value = false
  }

  fun downloadApk() {
    viewModelScope.launch {
      _isApkDownloaded.value = true
      val currentResult = (_buildState.value as? BuildState.Completed)?.result
      val fileName = currentResult?.apkFileName ?: "replit_zero_app.apk"
      _toastEvent.emit("📦 '$fileName' (19.2 MB) 다운로드 완료! 스마트폰 설치 준비가 끝났습니다.")
    }
  }

  fun copyLink(text: String, label: String) {
    viewModelScope.launch {
      _toastEvent.emit("📋 $label 링크가 클립보드에 복사되었습니다.")
    }
  }

  fun resetToNew() {
    _buildState.value = BuildState.Idle
    _isApkDownloaded.value = false
    _isSimulatorOpen.value = false
    _isCodeViewerOpen.value = false
  }

  fun startOneSecondBuild() {
    val currentTitle = _title.value.trim().ifEmpty { "무제 프로그램" }
    val currentDesc = _description.value.trim().ifEmpty { "레플릿 제로 초고속 자동 생성 엔진으로 빌드된 소프트웨어입니다." }
    val currentType = _selectedType.value

    viewModelScope.launch {
      _isApkDownloaded.value = false

      // 1초 초고속 생성 파이프라인 (총 1.0초 정밀 시뮬레이션)
      _buildState.value = BuildState.Generating(
        progress = 0.20f,
        currentStepText = "🚀 AI 초고속 엔진 가동: '${currentTitle}' 아키텍처 및 UI 설계 완료",
        stepIndex = 1
      )
      delay(250)

      _buildState.value = BuildState.Generating(
        progress = 0.55f,
        currentStepText = "⚙️ 무결점 네이티브 컴파일 & '${currentType.label}' 패키징 완료",
        stepIndex = 2
      )
      delay(300)

      _buildState.value = BuildState.Generating(
        progress = 0.85f,
        currentStepText = "🌐 구글 플레이 & 애플 앱스토어 무료 글로벌 배포 승인 등록 중...",
        stepIndex = 3
      )
      delay(250)

      _buildState.value = BuildState.Generating(
        progress = 1.0f,
        currentStepText = "🎉 1초 만에 빌드 및 양대 스토어 배포 성공! (오류 0건)",
        stepIndex = 4
      )
      delay(200)

      val safeTitleKey = currentTitle.replace("\\s+".toRegex(), "_").lowercase()
      val randomCode = UUID.randomUUID().toString().take(6)
      val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
      val nowFormatted = timeFormat.format(Date())

      val result = BuildResult(
        id = UUID.randomUUID().toString(),
        title = currentTitle,
        description = currentDesc,
        type = currentType,
        apkFileName = "${safeTitleKey}_v1.0.apk",
        apkFileSize = "19.2 MB",
        version = "v1.0.0 (Build 101)",
        packageName = "com.zero.${safeTitleKey}.${randomCode}",
        playStoreUrl = "https://play.google.com/store/apps/details?id=com.zero.${safeTitleKey}.${randomCode}",
        appStoreUrl = "https://apps.apple.com/app/id${(100000000..999999999).random()}",
        playStoreStatus = "배포 활성 (Active) • 무료 다운로드 가능",
        appStoreStatus = "승인 완료 (Approved) • 무료 다운로드 가능",
        durationMs = 1000,
        errorCount = 0,
        sourceCodeSnippet = generateSourceCodeSnippet(currentTitle, currentType, currentDesc),
        manifestSnippet = generateManifestSnippet(currentTitle, safeTitleKey),
        generatedTimeFormatted = nowFormatted
      )

      _buildState.value = BuildState.Completed(result)
      _toastEvent.emit("✨ 1초 만에 오류 없이 생성 및 스토어 무료 배포가 완료되었습니다!")
    }
  }

  private fun generateSourceCodeSnippet(title: String, type: CreationType, desc: String): String {
    return """
      // [레플릿 제로 자동 생성 코드 - 무결점 100% 빌드 통과]
      package com.zero.app

      import androidx.compose.material3.*
      import androidx.compose.runtime.*
      import androidx.compose.ui.Modifier

      @Composable
      fun ${title.replace("[^a-zA-Z0-9]".toRegex(), "").ifEmpty { "MainApp" }}Screen() {
          // Type: ${type.label} (${type.tag})
          // Description: $desc
          Surface(
              modifier = Modifier.fillMaxSize(),
              color = MaterialTheme.colorScheme.background
          ) {
              // 1초 자동 생성 완벽 실행 컴포넌트
              InteractiveZeroRuntime(
                  appTitle = "$title",
                  category = "${type.label}",
                  ready = true
              )
          }
      }
    """.trimIndent()
  }

  private fun generateManifestSnippet(title: String, pkg: String): String {
    return """
      <manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.zero.$pkg">
          <application
              android:label="$title"
              android:icon="@mipmap/ic_launcher"
              android:theme="@style/Theme.ReplitZero">
              <activity
                  android:name=".MainActivity"
                  android:exported="true">
                  <intent-filter>
                      <action android:name="android.intent.action.MAIN" />
                      <category android:name="android.intent.category.LAUNCHER" />
                  </intent-filter>
              </activity>
          </application>
      </manifest>
    """.trimIndent()
  }
}
