package com.mikeschen.www.hangboardrepeaters.Presenters;

import com.mikeschen.www.hangboardrepeaters.TimerActivity;

/**
 * Created by reviveit on 8/15/16.
 */
public class TimerActivityPresenter {
    TimerActivityView view;

    public void TimerActivityPresenter (TimerActivityView view) {
        this.view = view;
    }

    public void startRunnableButtonClicked () {
        view.startRunnable;

    }
}
