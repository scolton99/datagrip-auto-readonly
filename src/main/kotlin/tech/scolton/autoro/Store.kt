package tech.scolton.autoro

import com.intellij.database.dataSource.LocalDataSource
import java.util.concurrent.ConcurrentHashMap

val DBTimerSet = mutableMapOf<LocalDataSource, Thread>()