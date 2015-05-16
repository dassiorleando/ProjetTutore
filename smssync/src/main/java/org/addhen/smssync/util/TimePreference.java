/*
 * Copyright (c) 2010 - 2015 Ushahidi Inc
 * All rights reserved
 * Contact: team@ushahidi.com
 * Website: http://www.ushahidi.com
 * GNU Lesser General Public License Usage
 * This file may be used under the terms of the GNU Lesser
 * General Public License version 3 as published by the Free Software
 * Foundation and appearing in the file LICENSE.LGPL included in the
 * packaging of this file. Please review the following information to
 * ensure the GNU Lesser General Public License version 3 requirements
 * will be met: http://www.gnu.org/licenses/lgpl.html.
 *
 * If you have questions regarding the use of this file, please contact
 * Ushahidi developers at team@ushahidi.com.
 */

package org.addhen.smssync.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import org.addhen.smssync.Settings;
import org.addhen.smssync.prefs.Prefs;

/**
 * Created by Kamil Kalfas(kkalfas@soldevelo.com) on 19.05.14.
 * <p/>
 * Fields and methods are inherited from DialogPreference and TimePicker so DO NOT BE MISLED by
 * those names
 */
public class TimePreference extends DialogPreference {

    //fist picker field
    private int lastHour = 0;

    //second picker field
    private int lastMinute = 0;

    private TimePicker picker = null;

    private Prefs prefs;

    public TimePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        prefs = new Prefs(context);
        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    private static int getHour(String time) {
        String[] pieces = time.split(":");
        return (Integer.parseInt(pieces[0]));
    }

    private static int getMinute(String time) {
        String[] pieces = time.split(":");
        return (Integer.parseInt(pieces[1]));
    }

    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());

        return (picker);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        picker.setIs24HourView(true);
        picker.setCurrentHour(lastHour);
        picker.setCurrentMinute(lastMinute);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            lastHour = picker.getCurrentHour();
            lastMinute = picker.getCurrentMinute();
            if (callChangeListener(getTimeValueAsString())) {
                persistString(getTimeValueAsString());
                saveTimeFrequency();
            }
        }
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        String time;

        if (restoreValue) {
            if (defaultValue == null) {
                time = getPersistedString(loadTimeFrequency());
            } else {
                time = getPersistedString(defaultValue.toString());
            }
        } else {
            time = defaultValue.toString();
        }

        //This is needed for backward compatible with versions pre 2.6
        //It adjusts format of input string from "mm" to "hh:mm"
        if (!time.contains(":")) {
            int minutes = Integer.parseInt(time);
            time = Integer.toString((minutes / 60)) + ":" + Integer.toString((minutes % 60));
        }

        lastHour = getHour(time);
        lastMinute = getMinute(time);

    }

    public String getTimeValueAsString() {

        String h = String.valueOf(lastHour);
        String m = String.valueOf(lastMinute);

        String time = appendZeroAtBegin(h) + ":" + appendZeroAtBegin(m);
        return time;
    }

    private String appendZeroAtBegin(String time) {
        StringBuilder sb = new StringBuilder();
        if (time.length() == 1) {
            sb.append(0);
        }
        return sb.append(time).toString();
    }

    private void saveTimeFrequency() {
        if (Settings.TASK_CHECK_TIMES.equals(this.getKey())) {
            prefs.taskCheckTime().set(getTimeValueAsString());
        } else if (Settings.AUTO_SYNC_TIMES.equals(this.getKey())) {
            prefs.autoTime().set(getTimeValueAsString());
        }
    }

    private String loadTimeFrequency() {
        String time;
        if (Settings.TASK_CHECK_TIMES.equals(this.getKey())) {
            time = prefs.taskCheckTime().get();
        } else if (Settings.AUTO_SYNC_TIMES.equals(this.getKey())) {
            time = prefs.autoTime().get();
        } else {
            time = TimeFrequencyUtil.DEFAULT_TIME_FREQUENCY;
        }

        Logger.log("TimePreferences", "Save time " + time);
        return time;
    }
}