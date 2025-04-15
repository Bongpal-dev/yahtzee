package com.bongpal.yatzee.feature.play.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
internal fun ConfirmGiveUpDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("게임을 포기하시겠어요?") },
        text = { Text("기록하지 않은 모든 조합은 0점으로 처리됩니다.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("포기하기")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("계속하기")
            }
        }
    )
}