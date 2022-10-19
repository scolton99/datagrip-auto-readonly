package tech.scolton.autoro.actions

import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import tech.scolton.autoro.DBTimerSet
import tech.scolton.autoro.getLocalDataSourceFromAction
import tech.scolton.autoro.notify
import kotlin.concurrent.thread

open class UnlockAction(protected var delay: Long): AnAction() {
    private val noun = if (delay == 1L) "minute" else "minutes"

    override fun update(e: AnActionEvent) {
        e.presentation.text = "Unlock for $delay $noun"
//        e.presentation.icon = AllIcons.General.ExclMark
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val dataSource = getLocalDataSourceFromAction(e) ?: return

        if (DBTimerSet.containsKey(dataSource)) {
            DBTimerSet[dataSource]?.interrupt()
            DBTimerSet.remove(dataSource)
        }

        val message: String = if (dataSource.isReadOnly)
            "Unlocking \"${dataSource.name}\" for $delay $noun."
        else
            "\"${dataSource.name}\" was already unlocked. Re-locking in $delay $noun."

        dataSource.isReadOnly = false
        project.save()

        DBTimerSet[dataSource] = thread(name = "AutoRO Unlock ${dataSource.name} ($delay)") {
            try {
                Thread.sleep(delay * 60 * 1000)
                dataSource.isReadOnly = true
                project.save()
                DBTimerSet.remove(dataSource)
                notify("\"${dataSource.name}\" has been automatically locked.", NotificationType.INFORMATION, project)
            } catch (ignored: InterruptedException) {}
        }

        notify(message, NotificationType.INFORMATION, project)


    }
}