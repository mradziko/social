package com.airnauts.social.utils;

import android.os.Bundle;
import android.util.Log;

import com.airnauts.social.model.parse.ParseModel;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.parse.ParseUser;

import org.json.JSONObject;

/**
 * Created by mradziko on 17.11.2015.
 */
public class FacebookUtils {

    public static void updateFacebookData(GraphRequestBatch.Callback callback) {

        GraphRequest birthDayRequest = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/me", new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                try {
                    ParseModel.User.getCurrent().setFbBirthday(response.getJSONObject().getString("birthday"));
                    ParseModel.User.getCurrent().get().saveInBackground();
                    Log.v("data", response.getJSONObject().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "birthday");
        birthDayRequest.setParameters(parameters);

        GraphRequest meRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                if (jsonObject != null && jsonObject.has("id")) {
                    try {
                        ParseModel.User.getCurrent().setFbId(jsonObject.getString("id"));
                        ParseModel.User.getCurrent().get().saveInBackground();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ParseUser.getCurrentUser().saveInBackground();
                Log.v("data", jsonObject.toString());
            }
        });

        GraphRequestBatch batch = new GraphRequestBatch(meRequest, birthDayRequest);
        batch.addCallback(callback);
        batch.executeAsync();
    }
}
