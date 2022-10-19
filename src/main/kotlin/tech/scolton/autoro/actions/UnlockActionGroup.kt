package tech.scolton.autoro.actions

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import tech.scolton.autoro.getDbDataSourceFromAction

class UnlockActionGroup : ActionGroup() {
    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        return arrayOf(UnlockAction(1), UnlockAction(5), UnlockAction(30), CustomUnlockAction())
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = getDbDataSourceFromAction(e) != null
    }
}