package com.mikeschen.www.hangboardrepeaters;

public class MainActivityPresenter {
    MainActivityView view;

    public MainActivityPresenter(MainActivityView view) {
        this.view = view;
    }

    public void launchOtherActivityButtonClicked(Class activity){
        view.launchOtherActivity(activity);
    }
}
