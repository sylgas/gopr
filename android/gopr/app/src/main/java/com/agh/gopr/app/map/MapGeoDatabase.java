package com.agh.gopr.app.map;

import android.app.ProgressDialog;
import android.util.Log;

import com.agh.gopr.app.GOPRMobile;
import com.esri.android.map.FeatureLayer;
import com.esri.android.map.MapView;
import com.esri.core.ags.FeatureServiceInfo;
import com.esri.core.geodatabase.Geodatabase;
import com.esri.core.geodatabase.GeodatabaseFeatureTable;
import com.esri.core.map.CallbackListener;
import com.esri.core.tasks.geodatabase.GenerateGeodatabaseParameters;
import com.esri.core.tasks.geodatabase.GeodatabaseStatusCallback;
import com.esri.core.tasks.geodatabase.GeodatabaseStatusInfo;
import com.esri.core.tasks.geodatabase.GeodatabaseSyncTask;

import java.io.FileNotFoundException;

/**
 * Created by Sylgas on 09.06.14.
 */
public class MapGeoDatabase extends Geodatabase {
    private static final String TAG = "MapGeoDatabase";
    private static final String DB_NAME = "geo.db";
    private static final String PATH = GOPRMobile.getAppDirectory().getPath();

    private ProgressDialog dialog;
    private GeodatabaseSyncTask gdbSyncTask;
    private MapView mapView;

    public MapGeoDatabase(MapView mapView) throws FileNotFoundException {
        super(PATH);
        Log.d(TAG, "Created GeoDatabase in " + PATH);
        this.mapView = mapView;
    }

    /**
     * Create the GeodatabaseTask from the feature service URL w/o credentials.
     */
    public void downloadData(String url) {
        Log.i(TAG, "Create GeoDatabase");
        // create a dialog to update user on progress
        dialog = ProgressDialog.show(mapView.getContext(), "Download Data", "Create local runtime geodatabase");
        dialog.show();

        // create the GeodatabaseTask
        gdbSyncTask = new GeodatabaseSyncTask(url, null);
        gdbSyncTask.fetchFeatureServiceInfo(new CallbackListener<FeatureServiceInfo>() {

            @Override
            public void onError(Throwable arg0) {
                Log.e(TAG, "Error fetching FeatureServiceInfo");
            }

            @Override
            public void onCallback(FeatureServiceInfo fsInfo) {
                if (fsInfo.isSyncEnabled()) {
                    create(fsInfo);
                }
            }
        });

    }

    /**
     * Set up parameters to pass the the submitTask() method. A
     * CallbackListener is used for the response.
     */
    private void create(FeatureServiceInfo featureServerInfo) {
        // set up the parameters to generate a geodatabase
        GenerateGeodatabaseParameters params = new GenerateGeodatabaseParameters(featureServerInfo, mapView.getExtent(),
                mapView.getSpatialReference());

        // a callback which fires when the task has completed or failed.
        CallbackListener<String> gdbResponseCallback = new CallbackListener<String>() {
            @Override
            public void onError(final Throwable e) {
                Log.e(TAG, "Error creating geodatabase");
                dialog.dismiss();
            }

            @Override
            public void onCallback(String path) {
                Log.i(TAG, "Geodatabase is: " + path);
                dialog.dismiss();
                // update map with local feature layer from geodatabase
                updateFeatureLayer(path);

            }
        };

        // a callback which updates when the status of the task changes
        GeodatabaseStatusCallback statusCallback = new GeodatabaseStatusCallback() {
            @Override
            public void statusUpdated(GeodatabaseStatusInfo status) {
                Log.i(TAG, status.getStatus().toString());

            }
        };

        // create the fully qualified PATH for geodatabase file
        String localGdbFilePath = GOPRMobile.getAppDirectory().getAbsolutePath();

        // get geodatabase based on params
        submitTask(params, localGdbFilePath, statusCallback, gdbResponseCallback);
    }


    /**
     * Request database, poll server to get status, and download the file
     */
    private void submitTask(GenerateGeodatabaseParameters params, String file,
                                   GeodatabaseStatusCallback statusCallback, CallbackListener<String> gdbResponseCallback) {
        // submit task
        gdbSyncTask.generateGeodatabase(params, file, false, statusCallback, gdbResponseCallback);
    }

    /**
     * Add feature layer from local geodatabase to map
     */
    public void updateFeatureLayer(String featureLayerPath) {
        // create a new geodatabase
        Geodatabase localGdb = null;
        try {
            localGdb = new Geodatabase(featureLayerPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Geodatabase contains GdbFeatureTables representing attribute data
        // and/or spatial data. If GdbFeatureTable has geometry add it to
        // the MapView as a Feature Layer
        if (localGdb != null) {
            for (GeodatabaseFeatureTable gdbFeatureTable : localGdb.getGeodatabaseTables()) {
                if (gdbFeatureTable.hasGeometry())
                    mapView.addLayer(new FeatureLayer(gdbFeatureTable));
            }
        }
    }
}
