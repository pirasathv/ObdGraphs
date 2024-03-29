package org.obd.graphs.ui.preferences.pid

import android.content.Context
import android.util.AttributeSet
import androidx.preference.MultiSelectListPreference
import org.obd.graphs.bl.datalogger.DataLogger
import org.obd.graphs.ui.preferences.Prefs
import org.obd.graphs.ui.preferences.getStringSet
import java.util.*

private const val HIGH_PRIO_PID_PREF = "pref.pids.generic.high"
private const val LOW_PRIO_PID_PREF = "pref.pids.generic.low"

class DisplayedPIDsListPreferences(
    context: Context?,
    attrs: AttributeSet?
) :
    MultiSelectListPreference(context, attrs) {

    init {

        val entries: MutableList<CharSequence> =
            LinkedList()
        val entriesValues: MutableList<CharSequence> =
            LinkedList()

        highPriority(entries, entriesValues)
        lowPriority(entries, entriesValues)

        setEntries(entries.toTypedArray())
        entryValues = entriesValues.toTypedArray()
    }

    private fun highPriority(
        entries: MutableList<CharSequence>,
        entriesValues: MutableList<CharSequence>
    ) {
        val query = Prefs.getStringSet(HIGH_PRIO_PID_PREF)
        DataLogger.instance.pidDefinitionRegistry().findAll()
            .filter { pidDefinition -> pidDefinition.priority < 4 }
            .filter { pidDefinition -> query.contains(pidDefinition.id.toString()) }
            .sortedBy { p -> p.displayString().toString() }
            .forEach { p ->
                entries.add(p.displayString())
                entriesValues.add(p.id.toString())
            }
    }

    private fun lowPriority(
        entries: MutableList<CharSequence>,
        entriesValues: MutableList<CharSequence>
    ) {
        val query = Prefs.getStringSet(LOW_PRIO_PID_PREF)

        DataLogger.instance.pidDefinitionRegistry().findAll()
            .filter { p -> p.priority > 4 }
            .filter { p -> query.contains(p.id.toString()) }
            .sortedBy { p -> p.displayString().toString() }
            .forEach { p ->
                entries.add(p.displayString())
                entriesValues.add(p.id.toString())
            }
    }
}