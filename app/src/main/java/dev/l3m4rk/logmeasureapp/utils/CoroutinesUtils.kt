package dev.l3m4rk.logmeasureapp.utils

import kotlinx.coroutines.flow.SharingStarted

private const val WAITING_TIMEOUT_MS = 5_000L

val WhileSubscribed = SharingStarted.WhileSubscribed(WAITING_TIMEOUT_MS)