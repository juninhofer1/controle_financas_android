package com.buffalo.controlefinancas.util.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

public class ActivityUtil {

    private Context mContextActivity;
    private Class mClazz;
    private Bundle mBundle;
    private boolean mOnActivityResult;
    private int mRequestCode;

    public static class Builder {

        private Activity mContextActivity;
        private Class mClazz;
        private Bundle mBundle;
        private boolean mOnActivityResult;
        private int mRequestCode;

        private void initBuilder(Class aClazz) {
            this.mBundle = new Bundle();
            this.mClazz = aClazz;
        }

        public Builder(Activity aActivity, Class aClazz) {
            this.mContextActivity = aActivity;
            initBuilder(aClazz);
        }

        public Builder putBundleInt(String aKey, int aValue) {
            mBundle.putInt(aKey, aValue);
            return this;
        }

        public Builder putBundleLong(String aKey, long aValue) {
            mBundle.putLong(aKey, aValue);
            return this;
        }

        public Builder putBundleString(String aKey, String aValue) {
            mBundle.putString(aKey, aValue);
            return this;
        }

        public Builder putBundleSerializable(String aKey, Serializable aValue) {
            mBundle.putSerializable(aKey, aValue);
            return this;
        }

        public Builder putBundleDouble(String aKey, double aValue) {
            mBundle.putDouble(aKey, aValue);
            return this;
        }

        public Builder putBundleString(String aKey, float aValue) {
            mBundle.putFloat(aKey, aValue);
            return this;
        }

        public Builder onActivityResult(int aRequestCode) {
            mOnActivityResult = true;
            mRequestCode = aRequestCode;
            return this;
        }

        public ActivityUtil build() {
            return new ActivityUtil(this);
        }
    }

    private ActivityUtil(Builder aBuilder) {
        this.mContextActivity = aBuilder.mContextActivity;
        this.mBundle = aBuilder.mBundle;
        this.mClazz = aBuilder.mClazz;
        this.mRequestCode = aBuilder.mRequestCode;
        this.mOnActivityResult = aBuilder.mOnActivityResult;
        show();
    }

    private void show() {
        Intent lIntent = new Intent(mContextActivity, mClazz);
        lIntent.putExtras(mBundle);
        if(mOnActivityResult)
            if(mContextActivity instanceof Activity)
                ((Activity) mContextActivity).startActivityForResult(lIntent, mRequestCode);
            else
                mContextActivity.startActivity(lIntent);
        else
            mContextActivity.startActivity(lIntent);
    }
}
