package com.agh.gopr.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.agh.gopr.app.R;
import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.response.BasicActionInfo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

@EActivity(R.layout.action_list_activity)
public class ActionListActivity extends Activity {

    @Pref
    protected Preferences_ preferences;

    @Extra
    public List<BasicActionInfo> responseList;

    @ViewById(R.id.actionListView)
    protected ListView actionListView;

    @AfterViews
    protected void init() {
        ActionListAdapter listAdapter = new ActionListAdapter(this, responseList);
        actionListView.setAdapter(listAdapter);

        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BasicActionInfo chosenAction = responseList.get(position);
                preferences.actionId().put(chosenAction.getActionId());
                preferences.userId().put(chosenAction.getUserInActionId());
                MainActivity_.intent(ActionListActivity.this).start();
                ActionListActivity.this.finish();
            }
        });
    }

    private class ActionListAdapter extends ArrayAdapter<BasicActionInfo> {

        private Context context;
        private List<BasicActionInfo> responseList;

        public ActionListAdapter(Context context, List<BasicActionInfo> responseList) {
            super(context, R.layout.action_list_item, responseList);
            this.context = context;
            this.responseList = responseList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            if (rowView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                rowView = layoutInflater.inflate(R.layout.action_list_item, null, true);

                BasicActionInfo responseItem = responseList.get(position);

                TextView actionNameView = (TextView) rowView.findViewById(R.id.actionNameView);
                TextView actionIdView = (TextView) rowView.findViewById(R.id.actionIdView);
                TextView userInActionIdView = (TextView) rowView.findViewById(R.id.userInActionIdView);

                actionNameView.setText(responseItem.getActionName());
                actionIdView.setText(responseItem.getActionId());
                userInActionIdView.setText(responseItem.getUserInActionId());
            }
            return rowView;
        }
    }
}