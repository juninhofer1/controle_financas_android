package com.buffalo.controlefinancas.util;

public interface BaseView {

    void showMessage(String aMessa);

    void showErrorMessage(String aMessage);

    void showWarningMessage(String aMessage);

    void showInfoMessage(String aMessage);

    void showWarningMessageLong(String aMessage);
}
