package com.mikeschen.www.hangboardrepeaters.Presenters;

import com.mikeschen.www.hangboardrepeaters.Views.MainActivityView;

public class MainActivityPresenter {
    MainActivityView view;

    public MainActivityPresenter(MainActivityView view) {
        this.view = view;
    }

    public void launchOtherActivityButtonClicked(Class activity){
        view.launchOtherActivity(activity);
    }
}
