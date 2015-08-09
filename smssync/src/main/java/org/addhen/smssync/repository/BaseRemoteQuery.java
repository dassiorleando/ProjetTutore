package org.addhen.smssync.repository;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dassi on 20/07/15.
 */
public class BaseRemoteQuery<T extends ParseObject> {

    private static String TAG = BaseRemoteQuery.class.getSimpleName();
    private String className = "null";
    private Class<T> clazz;
    public T t;

    public BaseRemoteQuery(Class<T> clazz) {
        this.clazz = clazz;

        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalAccessException i) {
            Log.e(TAG, i.getMessage());
        }

        className = t.getClass().getSimpleName();
        className.toString();
    }

    public T createOne(final T t1, SaveCallback saveCallback) {
        if (t1 == null)
            return null;

        t1.saveInBackground(saveCallback);

        t1.pinInBackground(saveCallback);

        return t1;
    }

    public List<ParseObject> findOne() {
        ParseQuery<ParseObject> queryObect = new ParseQuery<ParseObject>(className);                 // queryUser.fromLocalDatastore();
        queryObect.fromLocalDatastore();

        List<ParseObject> l = new ArrayList<>();

        try {
            l = queryObect.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return l;
    }

    public void findAll(FindCallback findCallback) {
        ParseQuery<ParseObject> queryObect = new ParseQuery<ParseObject>(className);
        queryObect.orderByDescending("createdAt");

        queryObect.findInBackground(findCallback);
    }

    public void findBy(String fieldKey, String fieldValue, Integer limit, FindCallback findCallback) {
        ParseQuery<ParseObject> queryObect = new ParseQuery<ParseObject>(className);
        queryObect.whereEqualTo(fieldKey, fieldValue);
        queryObect.orderByDescending("createdAt");
        queryObect.setLimit(limit);

        queryObect.findInBackground(findCallback);
    }

    public void findById(String id, GetCallback getCallback) {
        ParseQuery<ParseObject> queryObect = new ParseQuery<ParseObject>(className);
        queryObect.orderByDescending("createdAt");
        queryObect.getInBackground(id, getCallback);
    }
}
