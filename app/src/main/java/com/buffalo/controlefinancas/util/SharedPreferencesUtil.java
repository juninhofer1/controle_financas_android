package com.buffalo.controlefinancas.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SharedPreferencesUtil {

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private static final String LAST_ACTIVITY = "LastActivity";
    private String mPrefix = null;
    private String mNome = null;
    private String mSuffix = null;
    private String mSenha = null;


    public SharedPreferencesUtil(Context context) {
        mContext = context;
    }

    public static SharedPreferencesUtil instantiate(Context context) {
        return new SharedPreferencesUtil(context);
    }

    @SuppressWarnings("unused")
    public SharedPreferencesUtil withPrefix(String prefix) {
        mPrefix = prefix;
        return this;
    }

    @SuppressWarnings("unused")
    public SharedPreferencesUtil withSuffix(String suffix) {
        mSuffix = suffix;
        return this;
    }

    @SuppressWarnings("unused")
    public SharedPreferencesUtil withNome(String nome) {
        mNome = nome;
        return this;
    }

    private String buildSharedPreferencesName() {
        StringBuilder sharedPreferencesName = new StringBuilder();
        if (mPrefix != null) {
            sharedPreferencesName.append(mPrefix).append("_");
        } else {
            sharedPreferencesName.append("Default_");
        }
        if (mNome != null) {
            sharedPreferencesName.append(mNome);
        } else {
            sharedPreferencesName.append("SharedPreferences");
        }
        if (mSuffix != null) {
            sharedPreferencesName.append("_").append(mSuffix);
        } else {
            sharedPreferencesName.append("");
        }
        return sharedPreferencesName.toString();
    }

    public SharedPreferences getSharedPreferences() {
        if (mSharedPreferences == null) {
            try {
                mSharedPreferences = mContext.getSharedPreferences(buildSharedPreferencesName(), Context.MODE_PRIVATE);
            } catch (Exception e) {
                return null;
            }
        }
        return mSharedPreferences;
    }

    @SuppressWarnings("unused")
    public void setSharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    private SharedPreferences.Editor getEditor() {
        if (getSharedPreferences() == null) {
            return null;
        }
        return getSharedPreferences().edit();
    }

    public void putString(String key, String value) {
        if (getEditor() == null) {
            return;
        }
        getEditor().putString(key, value).apply();
    }

    public String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    public void putBoolean(String key, Boolean value) {
        if (getEditor() != null) getEditor().putBoolean(key, value).apply();
    }

    public Boolean getBoolean(String key) {
        if (!getSharedPreferences().contains(key)) {
            return null;
        }
        return getSharedPreferences().getBoolean(key, false);
    }

    public void putFloat(String key, Float value) {
        if (getEditor() != null) getEditor().putFloat(key, value).apply();
    }

    public Float getFloat(String key) {
        if (!getSharedPreferences().contains(key)) {
            return null;
        }
        return getSharedPreferences().getFloat(key, 0F);
    }

    public void putInt(String key, Integer value) {
        if (getEditor() != null) getEditor().putInt(key, value).apply();
    }

    public Integer getInt(String key) {
        if (!getSharedPreferences().contains(key)) {
            return null;
        }
        return getSharedPreferences().getInt(key, 0);
    }

    public void putLong(String key, Long value) {
        if (getEditor() != null) getEditor().putLong(key, value).apply();
    }

    public Long getLong(String key) {
        if (!getSharedPreferences().contains(key)) {
            return null;
        }
        return getSharedPreferences().getLong(key, 0L);
    }

    public void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    public Date getDateTimeUltimaSincronizacao(Class<?> clazz) {
        return getDateTimeUltimaSincronizacao(clazz, "", true);
    }

    public Date getDateTimeUltimaSincronizacao(Class<?> clazz, String identificador) {
        return getDateTimeUltimaSincronizacao(clazz, identificador, true);
    }

    public Date getDateTimeUltimaSincronizacao(Class<?> clazz, String identificador, boolean aMenosUmaHora) {
        Long millisDateTimeUltimaSincronizacao = getLong("millisDateTimeUltimaSincronizacao_" + clazz.getSimpleName() + identificador);
        if (millisDateTimeUltimaSincronizacao == null) {
            millisDateTimeUltimaSincronizacao = 0L;
        }
        if (aMenosUmaHora){
            return new Date(millisDateTimeUltimaSincronizacao - 3600000L);
        } else {
            return new Date(millisDateTimeUltimaSincronizacao);
        }
    }

    public void setDateTimeUltimaSincronizacao(Class<?> clazz, Long millisDateTimeUltimaSincronizacao) {
        setDateTimeUltimaSincronizacao(clazz, "", millisDateTimeUltimaSincronizacao);
    }

    public void setDateTimeUltimaSincronizacao(Class<?> clazz, String identificador, Long millisDateTimeUltimaSincronizacao) {
        putLong("millisDateTimeUltimaSincronizacao_" + clazz.getSimpleName() + identificador, millisDateTimeUltimaSincronizacao);
    }

    @SuppressWarnings("unused")
    public Integer getLastActivityDetected() {
        return getInt(LAST_ACTIVITY);
    }

    @SuppressWarnings("unused")
    public void setLastActivity(Integer aLastActivity) {
        putInt(LAST_ACTIVITY, aLastActivity);
    }
}
