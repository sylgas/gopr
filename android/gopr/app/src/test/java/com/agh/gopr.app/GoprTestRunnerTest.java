package com.agh.gopr.app;

import android.content.Context;

import com.agh.gopr.app.ui.fragment.MapFragment;
import com.google.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.exceptions.verification.WantedButNotInvoked;

import roboguice.inject.InjectResource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GoprTestRunner.class)
public class GoprTestRunnerTest {

    @Inject
    private Context context;

    @InjectResource(R.string.map_title)
    private String resource;

    @Mock
    private MapFragment injectedMock;

    @Test
    public void testContextHasBeenInjected() throws Exception {
        assertNotNull(context);
        assertTrue(context instanceof Context);
    }

    @Test
    public void testResourceHasBeenInjected() throws Exception {
        assertNotNull(resource);
        assertEquals("Map", resource);
    }

    @Test
    public void testMockHasBeenInjected() throws Exception {
        assertNotNull(injectedMock);
    }

    @Test
    public void testMockBehaviour() throws Exception {
        when(injectedMock.getLogin()).thenReturn("GOPR");
        assertNotNull(injectedMock.getLogin());
        assertEquals("GOPR", injectedMock.getLogin());
    }

    @Test(expected = WantedButNotInvoked.class)
    public void testMockVerificationError() {
        verify(injectedMock).getCurrentPosition();
    }

    @Test
    public void testMockVerificationSuccess() {
        injectedMock.getCurrentPosition();
        verify(injectedMock).getCurrentPosition();
    }

    public static class InnerTest {
        @Test
        public void testRunningInnerTest() {
            assertTrue(true);
        }
    }
}
