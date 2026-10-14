package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.BuildState
import com.example.ui.components.ActionAndDeploySection
import com.example.ui.components.BuildResultSection
import com.example.ui.components.CategorySelectionSection
import com.example.ui.components.CodeViewerDialog
import com.example.ui.components.GenerationProgressCard
import com.example.ui.components.InputSection
import com.example.ui.components.InteractiveAppSimulator
import com.example.ui.components.ReplitZeroHeader
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.ReplitZeroTheme
import com.example.viewmodel.ReplitZeroViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ReplitZeroTheme {
        ReplitZeroScreen()
      }
    }
  }
}

@Composable
fun ReplitZeroScreen(
  viewModel: ReplitZeroViewModel = viewModel()
) {
  val title by viewModel.title.collectAsStateWithLifecycle()
  val description by viewModel.description.collectAsStateWithLifecycle()
  val selectedType by viewModel.selectedType.collectAsStateWithLifecycle()
  val buildState by viewModel.buildState.collectAsStateWithLifecycle()
  val isSimulatorOpen by viewModel.isSimulatorOpen.collectAsStateWithLifecycle()
  val isCodeViewerOpen by viewModel.isCodeViewerOpen.collectAsStateWithLifecycle()
  val isApkDownloaded by viewModel.isApkDownloaded.collectAsStateWithLifecycle()

  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current
  val snackbarHostState = remember { SnackbarHostState() }
  val scrollState = rememberScrollState()

  // Collect toast events
  LaunchedEffect(Unit) {
    viewModel.toastEvent.collect { message ->
      Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
      snackbarHostState.showSnackbar(message)
    }
  }

  // Auto scroll to result when generation completes
  LaunchedEffect(buildState) {
    if (buildState is BuildState.Completed) {
      scrollState.animateScrollTo(scrollState.maxValue)
    }
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = BackgroundLight,
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    contentWindowInsets = WindowInsets(0, 0, 0, 0)
  ) { innerPadding ->
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(BackgroundLight)
        .padding(
          top = statusBarPadding,
          bottom = navBarPadding
        ),
      contentAlignment = Alignment.TopCenter
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .widthIn(max = 640.dp)
          .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // App Header with Rocket Z Logo
        ReplitZeroHeader()

        Spacer(modifier = Modifier.height(8.dp))

        // 1. 상단 메인 입력 구역 ('프로그램 제목' 1개, '상세 내용 및 기능' 1개)
        InputSection(
          title = title,
          description = description,
          onTitleChanged = viewModel::onTitleChanged,
          onDescriptionChanged = viewModel::onDescriptionChanged,
          onApplySuggestion = viewModel::applySuggestion
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. 중간 선택 구역 (6가지 선택 버튼)
        CategorySelectionSection(
          selectedType = selectedType,
          onTypeSelected = viewModel::onTypeSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. 하단 실행 및 배포 구역 ('1초 생성 및 스토어 무료 배포' 버튼)
        val isGenerating = buildState is BuildState.Generating
        ActionAndDeploySection(
          isGenerating = isGenerating,
          onGenerateClick = viewModel::startOneSecondBuild
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 1초 실시간 진행 상태 카드
        if (buildState is BuildState.Generating) {
          val genState = buildState as BuildState.Generating
          GenerationProgressCard(
            progress = genState.progress,
            currentStepText = genState.currentStepText,
            stepIndex = genState.stepIndex
          )
          Spacer(modifier = Modifier.height(16.dp))
        }

        // 결과물 표시 구역 (스마트폰 바로 설치 APK 파일 + 스토어 배포 + 즉시 실행)
        if (buildState is BuildState.Completed) {
          val completedResult = (buildState as BuildState.Completed).result
          BuildResultSection(
            result = completedResult,
            isApkDownloaded = isApkDownloaded,
            onDownloadApk = viewModel::downloadApk,
            onOpenSimulator = viewModel::openSimulator,
            onOpenCodeViewer = viewModel::openCodeViewer,
            onCopyLink = { url, label ->
              clipboardManager.setText(AnnotatedString(url))
              viewModel.copyLink(url, label)
            },
            onReset = viewModel::resetToNew
          )
        }

        Spacer(modifier = Modifier.height(32.dp))
      }
    }
  }

  // Interactive Live App/Game/Web Simulator Modal
  if (isSimulatorOpen && buildState is BuildState.Completed) {
    val completedResult = (buildState as BuildState.Completed).result
    InteractiveAppSimulator(
      result = completedResult,
      onDismiss = viewModel::closeSimulator,
      onInstallApk = {
        viewModel.downloadApk()
        viewModel.closeSimulator()
      }
    )
  }

  // Source Code Inspector Dialog
  if (isCodeViewerOpen && buildState is BuildState.Completed) {
    val completedResult = (buildState as BuildState.Completed).result
    CodeViewerDialog(
      result = completedResult,
      onDismiss = viewModel::closeCodeViewer,
      onCopyCode = { code, label ->
        clipboardManager.setText(AnnotatedString(code))
        viewModel.copyLink(code, label)
      }
    )
  }
}
