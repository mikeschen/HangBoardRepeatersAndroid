package com.mikeschen.www.hangboardrepeaters.Presenters;

import com.mikeschen.www.hangboardrepeaters.TimerActivity;
import com.mikeschen.www.hangboardrepeaters.Views.MainActivityView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    MainActivityPresenter presenter;

    @Mock
    MainActivityView view;

    @Before
    public void setUp() throws Exception {
        presenter = new MainActivityPresenter(view);
    }

    @Test
    public void launchOtherActivityButtonClicked() throws Exception {
        Class clazz = TimerActivity.class;
        presenter.launchOtherActivityButtonClicked(clazz);
        Mockito.verify(view).launchOtherActivity(clazz);
    }
}