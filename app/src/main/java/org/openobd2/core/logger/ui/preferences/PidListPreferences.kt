package org.openobd2.core.logger.ui.preferences

import android.content.Context
import android.util.AttributeSet
import androidx.preference.MultiSelectListPreference
import org.openobd2.core.logger.bl.DataLogger
import java.util.*

class PidListPreferences(
    context: Context?,
    attrs: AttributeSet?
) :
    MultiSelectListPreference(context, attrs) {
    init {

        val entries: MutableList<CharSequence> =
            LinkedList()
        val entriesValues: MutableList<CharSequence> =
            LinkedList()

        DataLogger.INSTANCE.pids().definitions.sortedBy { pidDefinition -> pidDefinition.description }
            .forEach { p ->
                entries.add(p.description)
                entriesValues.add(p.pid)
            }

        val default = hashSetOf<String>().apply {
            add("05")//Engine coolant temperature
            add("0B") //Intake manifold absolute pressure
            add("0C") //Engine RPM
            add("0F") //Intake air temperature
            add("11") //Throttle position
            add("OD") //Vehicle speed
            add("OE") //Timing Advance
        }
        setDefaultValue(default)
        setEntries(entries.toTypedArray())
        entryValues = entriesValues.toTypedArray()
    }
}