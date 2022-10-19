package tech.scolton.autoro.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.panel
import org.jetbrains.annotations.Nullable
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class CustomUnlockDialogWrapper: DialogWrapper(true) {
    private val model = Model()

    val time get() = model.time

    init {
        title = "Unlock Duration"
        init()
    }

    @Nullable
    override fun createCenterPanel(): JComponent {
        return panel {
            row {
                intTextField()
                    .label("How long would you like to unlock this connection?", LabelPosition.TOP)
                    .bindIntText(model::time)
            }
        }
    }

    internal data class Model(
        var time: Int = 0
    )
}