package com.mikeschen.www.hangboardrepeaters.Presenters;

import com.mikeschen.www.hangboardrepeaters.Views.TimerActivityView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimerActivityPresenterTest {

    TimerActivityPresenter presenter;

    @Mock
    TimerActivityView view;

    @Before
    public void setUp() throws Exception {
        presenter = new TimerActivityPresenter(view);
    }

    @Test
    public void startRunnableButtonClicked() throws Exception {
        presenter.startRunnableButtonClicked();
        Mockito.verify(view).startRunnable();
    }
}