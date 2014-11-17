package com.agh.gopr.app.common;

import com.agh.gopr.app.GoprTestRunner;
import com.google.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GoprTestRunner.class)
public class PreferenceHelperTest {

    @Inject
    private Preferences_ preferences;

    @Test
    public void testGettingDefaultAddress() throws Exception {
        assertEquals(PreferenceHelper.DEFAULT_ADDRESS, PreferenceHelper.getServerAddress(preferences));
    }

    @Test
    public void testGettingAddressFromPreferences() throws Exception {
        preferences.serverAddress().put("address");
        assertEquals("address", PreferenceHelper.getServerAddress(preferences));
    }

    @Test
    public void testGettingMapSiteFromPreferences() throws Exception {
        preferences.mapSite().put("mapSite");
        assertEquals("mapSite", preferences.mapSite().get());
    }

    @Test
    public void testGettingDefautMapSite() throws Exception {
        assertEquals(PreferenceHelper.DEFAULT_SITE, PreferenceHelper.getMapSite(preferences));
    }
}
