package com.flopetracker.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.flopetracker.R;
import com.flopetracker.util.LocaleHelper;

public class SettingFragment extends PreferenceFragmentCompat {
    public SettingFragment() {}
    private static final String PREF_LANGUAGE = "app_language";

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        ListPreference languagePreference = findPreference(PREF_LANGUAGE);
        if (languagePreference != null) {
            languagePreference.setOnPreferenceChangeListener(((preference, newValue) -> {
                LocaleHelper.setLocale(requireActivity(), newValue.toString());
                requireActivity().recreate();
                return true;
            }));
        }
    }
}