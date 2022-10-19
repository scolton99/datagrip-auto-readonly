package tech.scolton.autoro.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import tech.scolton.autoro.ui.CustomUnlockDialogWrapper

class CustomUnlockAction: UnlockAction(-1L) {
    override fun update(e: AnActionEvent) {
        super.update(e)

        e.presentation.text = "Unlock..."
    }

    override fun actionPerformed(e: AnActionEvent) {
        val dialog = CustomUnlockDialogWrapper()

        if (!dialog.showAndGet())
            return

        delay = dialog.time.toLong()

        super.actionPerformed(e)
    }
}