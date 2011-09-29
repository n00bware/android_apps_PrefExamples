/*
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
 *                    Version 2, December 2004 
 *
 * Copyright (C) 2004 Sam Hocevar <sam@hocevar.net> 
 *
 * Everyone is permitted to copy and distribute verbatim or modified 
 * copies of this license document, and changing it is allowed as long 
 * as the name is changed. 
 *
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 
 *
 *  0. You just DO WHAT THE FUCK YOU WANT TO. 
 */

package com.n00bware.prefexample;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener {

    private String PREF_EDIT_HOLDER; //Holds new value of EditTextPreference
    private static final String PREF_EDIT_TEXT = "pref_edit_text"; //handle to find this EditTextPreference (android:key)
    private static final String TAG = "PrefExample"; //TAG to identify app to logcat

    private EditTextPreference mEditTextPref; //Object used to reference preference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // called when first created

        //set the title of the view
        setTitle(R.string.main_title_head);

        //load the preferences from res/xml/main.xml
        addPreferencesFromResource(R.xml.main); 

        //send logcat a message for debuging
        Log.d(TAG, "Loading prefs");

        //we neet get the screen we are using so we can reference our preferences (Objects)
        PreferenceScreen prefSet = getPreferenceScreen();

        //assign mEditTextPref to work with android:key="pref_edit_text"
        mEditTextPref = (EditTextPreference) prefSet.findPreference(PREF_EDIT_TEXT);

        //if mEditTextPref isn't empty (null) then get the text and send it to a string (PREF_EDIT_HOLDER)
        if (mEditTextPref != null) {
            EditText editText = mEditTextPref.getEditText();
            PREF_EDIT_HOLDER = mEditTextPref.getEditText().toString();

            //check if the editText widget is empty if not then
            if (editText != null){

                //we want to limit the length of the input to 30char
                InputFilter lengthFilter = new InputFilter.LengthFilter(30);
                editText.setFilters(new InputFilter[]{lengthFilter});
                //we only want one line of text from the user
                editText.setSingleLine(true);
            }
        }
        /*
         * This is the most important part setup a listener for change
         * this lets us use the onPreferenceChange method below
         */
        mEditTextPref.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference pref, Object newValue) {

        //this is an over simplization but works for our purpose here
        //the next example (ListPreference) will make the reciever more
        //dynamic as it is we just do what is sent no matter what
        if (pref != null) {
            if (newValue != null) {

                //do work
                Toast.makeText(MainActivity.this, "You chose " + pref + " with a new value of " + newValue.toString(), Toast.LENGTH_LONG).show();

            return true; //we used the values so return true
            }
        return false; //pref selected but not newValue so return false
        }
    return false; //nothing new so return false
    }
}
