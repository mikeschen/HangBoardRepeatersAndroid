package com.mikeschen.www.hangboardrepeaters.Presenters;

import com.mikeschen.www.hangboardrepeaters.Views.TimerActivityView;

public class TimerActivityPresenter {
    TimerActivityView view;

    public TimerActivityPresenter (TimerActivityView view) {
        this.view = view;
    }

    public void startRunnableButtonClicked() {
        view.startRunnable();
    }
}
