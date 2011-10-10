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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "PrefExample"; //TAG to identify app to logcat

    private String PREF_EDIT_HOLDER; //Holds new value of EditTextPreference
    private final int MENU_EXAMPLE = 1;
    private final int MENU_CODE = 2;

    private static final String PREF_EDIT_TEXT = "pref_edit_text"; //handle to find this EditTextPreference (android:key)
    private static final String PREF_LIST = "pref_list"; //handle to find the ListPreference (android:key)
    private static final String PREF_CHECKBOX = "pref_checkbox"; //handle to find the CheckBoxPreference (android:key)
    private static final String PREF_ICON = "pref_icon"; //handle to find the PreferenceScreen (android:key)

    private static final String GITHUB = "https://github.com/n00bware/android_apps_PrefExamples";
    private static final String DONATE = "http://bit.ly/oCWMo0";

    private EditTextPreference mEditTextPref; //Object used to reference EditTextPreference
    private ListPreference mListPref; //Object used to reference ListPreference
    private CheckBoxPreference mCheckBoxPref; //Object used to reterence CheckBoxPreference

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

        //this is simplier
        mListPref = (ListPreference) prefSet.findPreference(PREF_LIST);
        mListPref.setOnPreferenceChangeListener(this);

        mCheckBoxPref = (CheckBoxPreference) prefSet.findPreference(PREF_CHECKBOX);
        boolean check = mCheckBoxPref.isChecked();
        mCheckBoxPref.setChecked(check);

        findPreference(PREF_ICON).setOnPreferenceClickListener(
            new OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference prefSet) {
                    website(GITHUB);
                    return true;
                }
            });
    }

    public boolean onPreferenceChange(Preference pref, Object newValue) {

        //we want to handle "unknown" differently than anything else
        //so we take care of it first
        if (newValue.toString().equals("unknown")) {
            //do work
            Log.d(TAG, "method recieved jinx **DO SOMETHING SPECIAL**");
            Toast.makeText(MainActivity.this, "method recieved \"jinx\" **DO SOMETHING SPECIAL**", Toast.LENGTH_LONG).show();
            return true; //we used the valuse so return true
        //now we handle everything that doesn't have a newValue of "jinx"
        } else {
            if (pref == mEditTextPref) {
                //do work
                Toast.makeText(MainActivity.this, "You chose " + pref + " with a new value of " + newValue.toString(), Toast.LENGTH_LONG).show();
                return true; //we used the values so return true

            //handle all the ListPreference values except "jinx"
            } else if (pref == mListPref) {
                //do work
                Toast.makeText(MainActivity.this, "You chose " + pref + " with a new value of " + newValue.toString(), Toast.LENGTH_LONG).show();
                return true; //we used the values so return true
            }
        return false; //pref selected but not newValue so return false
        }
    }

    //This is an @Override because onPreferneceTreeClick is from a parent class
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen prefScreen, Preference prefS) {
        boolean value;
        if (prefS == mCheckBoxPref) {
            //do work that happens independant of the on/off state

            //I wanted to provide an example of work that can be done
            //so for this example we will send to values GoVols & CheckedOff
            //as strings to the method ezBOX() since ezBOX is a boolean
            //we can just use its return value
            value = mCheckBoxPref.isChecked();
            return ezBOX(String.valueOf(value ? "GoVols" : "CheckedOff"));
        }
    return false;
    }

    private boolean ezBOX(String onORoff) {
        //do work that is dependant of the on/off state of mCheckBoxPref

        //this is a super simple example but it shows how to
        //recieve values from a checkbox and act on them
        Toast.makeText(MainActivity.this, "Yo your choice is " + onORoff, Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean website(String web) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
        startActivity(browserIntent);
        return true;
    }

    /*
     * these booleans will handle when the user selects
     * a menu hardkey
     */
    public boolean onCreateOptionsMenu(Menu menu){
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_DONATE, 0, "Donate").setIcon(R.drawable.paypal_logo);
        menu.add(0, MENU_CODE, 0, "Show me the code").setIcon(R.drawable.pref_icon);
        return result;
    }
 
    /* Handle the menu selection */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_DONATE:
            //do work
            Toast.makeText(MainActivity.this, "Please donate if you want", Toast.LENGTH_SHORT).show();
            return website(DONATE);
        case MENU_CODE:
            //do work
            Toast.makeText(MainActivity.this, "Launching github in a webview", Toast.LENGTH_SHORT).show();
            return website(GITHUB);
        }
        return false;
    }

}
