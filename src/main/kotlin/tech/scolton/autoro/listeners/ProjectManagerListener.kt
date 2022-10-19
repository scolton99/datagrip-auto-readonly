package tech.scolton.autoro.listeners

import com.intellij.openapi.project.Project
import tech.scolton.autoro.DBTimerSet

class ProjectManagerListener: com.intellij.openapi.project.ProjectManagerListener {
    override fun projectClosingBeforeSave(project: Project) {
        DBTimerSet.entries.forEach {
            it.key.isReadOnly = true
            it.value.interrupt()
        }

        project.save()
    }
}