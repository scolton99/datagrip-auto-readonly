package tech.scolton.autoro

import com.intellij.database.DatabaseDataKeys
import com.intellij.database.dataSource.LocalDataSource
import com.intellij.database.dataSource.LocalDataSourceManager
import com.intellij.database.datagrid.DataGridUtil
import com.intellij.database.model.DasDataSource
import com.intellij.database.psi.DbDataSource
import com.intellij.database.psi.DbDataSourceImpl
import com.intellij.database.util.DbImplUtil
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ObjectUtils

fun getDbDataSourceFromAction(e: AnActionEvent): DbDataSource? {
    val system = ObjectUtils.coalesce(
        DataGridUtil.getDatabaseSystem(e.getData(DatabaseDataKeys.DATA_GRID_KEY)), DbImplUtil.getForcedDataSource(
            e.getData(CommonDataKeys.PSI_FILE)
        ), PsiTreeUtil.getParentOfType(
            e.getData(CommonDataKeys.PSI_ELEMENT),
            DbDataSource::class.java, false
        )
    ) as DasDataSource?

    return ObjectUtils.tryCast(system, DbDataSource::class.java)
}

fun getLocalDataSourceFromAction(e: AnActionEvent): LocalDataSource? {
    val dbDataSource = getDbDataSourceFromAction(e) ?: return null
    if (dbDataSource !is DbDataSourceImpl)
        return null

    val manager = dbDataSource.dbManager

    val dasDataSource = manager.dataSources.find { it.uniqueId == dbDataSource.uniqueId }

    return ObjectUtils.tryCast(dasDataSource, LocalDataSource::class.java)
}

fun notify(message: String, type: NotificationType, project: Project) {
    val group = NotificationGroupManager.getInstance().getNotificationGroup("AutoRO Notifications")
    group.createNotification(message, type).notify(project)
}