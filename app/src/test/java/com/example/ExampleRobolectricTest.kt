package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.BuildState
import com.example.model.CreationType
import com.example.viewmodel.ReplitZeroViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  private val testDispatcher = StandardTestDispatcher()

  @Before
  fun setUp() {
    Dispatchers.setMain(testDispatcher)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `read string from context matches app name`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("레플릿 제로", appName)
  }

  @Test
  fun `verify 6 creation types are defined with correct labels`() {
    val types = CreationType.entries
    assertEquals(6, types.size)
    assertEquals("웹사이트", types[0].label)
    assertEquals("앱", types[1].label)
    assertEquals("앱 APK", types[2].label)
    assertEquals("게임 앱", types[3].label)
    assertEquals("컴퓨터 게임", types[4].label)
    assertEquals("컴퓨터 프로그램", types[5].label)
  }

  @Test
  fun `verify viewModel build generation produces APK result`() = runTest(testDispatcher) {
    val viewModel = ReplitZeroViewModel()
    viewModel.onTitleChanged("우주 액션 러너")
    viewModel.onDescriptionChanged("모바일 터치 아케이드 게임")
    viewModel.onTypeSelected(CreationType.GAME_APP)

    viewModel.startOneSecondBuild()
    advanceUntilIdle()

    val state = viewModel.buildState.value
    assertTrue("State should be Completed, was: $state", state is BuildState.Completed)
    val result = (state as BuildState.Completed).result

    assertEquals("우주 액션 러너", result.title)
    assertEquals(CreationType.GAME_APP, result.type)
    assertTrue("APK file name should end with .apk", result.apkFileName.endsWith(".apk"))
    assertEquals(0, result.errorCount)
    assertNotNull(result.playStoreUrl)
    assertNotNull(result.appStoreUrl)
  }
}
