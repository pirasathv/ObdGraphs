package org.obd.graphs.ui.preferences.profile

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference
import androidx.preference.Preference.OnPreferenceChangeListener
import org.obd.graphs.activity.navigateToPreferencesScreen
import org.obd.graphs.ui.preferences.updateToolbar

class ProfileListPreference(
    context: Context?,
    attrs: AttributeSet?
) : ListPreference(context, attrs) {

    init {

        getProfileList()
            .let {
                entries = it.values.toTypedArray()
                entryValues = it.keys.toTypedArray()
                setDefaultValue(it.keys.first())
            }

        onPreferenceChangeListener =
            OnPreferenceChangeListener { _, newValue ->
                loadProfile(newValue.toString())
                updateToolbar()
                navigateToPreferencesScreen(PROFILES_PREF)
                true
            }
    }
}