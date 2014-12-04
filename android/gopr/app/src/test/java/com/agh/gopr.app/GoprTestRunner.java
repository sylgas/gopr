package com.agh.gopr.app;

import com.agh.gopr.app.ioc.GeoSUPModule;
import com.google.inject.Injector;

import org.junit.runners.model.InitializationError;
import org.mockito.MockitoAnnotations;
import org.robolectric.DefaultTestLifecycle;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.TestLifecycle;

import roboguice.RoboGuice;
import roboguice.inject.ContextScope;

public class GoprTestRunner extends RobolectricTestRunner {
    public GoprTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected Class<? extends TestLifecycle> getTestLifecycleClass() {
        return TestLifeCycleWithInjection.class;
    }

    public static class TestLifeCycleWithInjection extends DefaultTestLifecycle {
        @Override
        public void prepareTest(Object test) {
            injectDependencies(test);
            injectMocks(test);
        }

        private void injectDependencies(Object test) {
            GeoSUP application = (GeoSUP) Robolectric.application;
            RoboGuice.setBaseApplicationInjector(application, RoboGuice.DEFAULT_STAGE,
                    RoboGuice.newDefaultRoboModule(application), new GeoSUPModule());


            Injector injector = RoboGuice.getInjector(application);
            ContextScope scope = injector.getInstance(ContextScope.class);
            scope.enter(application);
            injector.injectMembers(test);
        }

        private void injectMocks(Object test) {
            MockitoAnnotations.initMocks(test);
        }
    }
}
