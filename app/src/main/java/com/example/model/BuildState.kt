package com.example.model

data class BuildResult(
  val id: String,
  val title: String,
  val description: String,
  val type: CreationType,
  val apkFileName: String,
  val apkFileSize: String,
  val version: String,
  val packageName: String,
  val playStoreUrl: String,
  val appStoreUrl: String,
  val playStoreStatus: String,
  val appStoreStatus: String,
  val durationMs: Long,
  val errorCount: Int,
  val sourceCodeSnippet: String,
  val manifestSnippet: String,
  val generatedTimeFormatted: String
)

sealed interface BuildState {
  data object Idle : BuildState

  data class Generating(
    val progress: Float,
    val currentStepText: String,
    val stepIndex: Int
  ) : BuildState

  data class Completed(
    val result: BuildResult
  ) : BuildState
}
