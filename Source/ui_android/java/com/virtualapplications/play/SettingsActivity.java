package com.virtualapplications.play;

import android.os.*;
import android.preference.*;
import android.widget.*;
import java.util.*;

public class SettingsActivity extends PreferenceActivity
{
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		SettingsManager.save();
	}
	
	@Override
	public void onBuildHeaders(List<Header> target) 
	{
		loadHeadersFromResource(R.xml.settings_headers, target);
	}
	
	@Override
	protected boolean isValidFragment(String fragmentName)
	{
		return true;
	}
	
	public static class GeneralSettingsFragment extends PreferenceFragment 
	{
		@Override
		public void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);

			addPreferencesFromResource(R.xml.settings_general_fragment);
			
			PreferenceGroup prefGroup = getPreferenceScreen();
			for(int i = 0; i < prefGroup.getPreferenceCount(); i++)
			{
				Preference pref = prefGroup.getPreference(i);
				if(pref instanceof CheckBoxPreference)
				{
					CheckBoxPreference checkBoxPref = (CheckBoxPreference)pref;
					checkBoxPref.setChecked(SettingsManager.getPreferenceBoolean(checkBoxPref.getKey()));
				}
			}
			
			final Preference button_f = (Preference)getPreferenceManager().findPreference("ui.clearfolder");
			if (button_f != null) {
				button_f.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference arg0) {
						MainActivity.resetDirectory();
						getPreferenceScreen().removePreference(button_f);
						return true;
					}
				});
			}
			final Preference button_c = (Preference)getPreferenceManager().findPreference("ui.clearcache");
			if (button_c != null) {
				button_c.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference arg0) {
						MainActivity.clearCache();
						getPreferenceScreen().removePreference(button_c);
						return true;
					}
				});
			}
		}
		
		@Override
		public void onDestroy()
		{
			PreferenceGroup prefGroup = getPreferenceScreen();
			for(int i = 0; i < prefGroup.getPreferenceCount(); i++)
			{
				Preference pref = prefGroup.getPreference(i);
				if(pref instanceof CheckBoxPreference)
				{
					CheckBoxPreference checkBoxPref = (CheckBoxPreference)pref;
					SettingsManager.setPreferenceBoolean(checkBoxPref.getKey(), checkBoxPref.isChecked());
				}
			}

			super.onDestroy();
		}
	}
}
