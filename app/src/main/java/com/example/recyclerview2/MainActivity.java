package com.example.recyclerview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.AdapterListener {
    private Adapter MyAdapter;
    private RecyclerView rvteamleague;
    ArrayList <Data> dataTeam;
    ShimmerFrameLayout shimmer;
    TextView tvPB;

    public void getAPIData(){
        shimmer = findViewById(R.id.shimmer);
        shimmer.startShimmer();
        String url = "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=English%20Premier%20League";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray ArrayTeam = jsonObject.getJSONArray("teams");
                            for (int i = 0; i < ArrayTeam.length(); i++) {
                                Data myTeam = new Data ();
                                JSONObject jsonTeam = ArrayTeam.getJSONObject(i);
                                myTeam.setNama(jsonTeam.getString("strTeam"));
                                myTeam.setWebsite(jsonTeam.getString("strWebsite"));
                                myTeam.setImage(jsonTeam.getString("strTeamBadge"));
                                dataTeam.add(myTeam);
                            }
                            rvteamleague = findViewById(R.id.rvTeamLeague);
                            MyAdapter = new Adapter(getApplicationContext(), dataTeam, MainActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvteamleague.setHasFixedSize(true);
                            rvteamleague.setLayoutManager(mLayoutManager);
                            rvteamleague.setAdapter(MyAdapter);
                            shimmer.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("failed ", "onError: "+anError.toString());
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataTeam = new ArrayList<>();
        getAPIData();
    }

    @Override
    public void onDataSelected(Data data) {

    }
}