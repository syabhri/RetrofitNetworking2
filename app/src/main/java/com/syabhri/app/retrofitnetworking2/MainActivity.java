package com.syabhri.app.retrofitnetworking2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.syabhri.app.retrofitnetworking2.Adapter.MyAdapter;
import com.syabhri.app.retrofitnetworking2.Model.GetPembelian;
import com.syabhri.app.retrofitnetworking2.Model.Pembelian;
import com.syabhri.app.retrofitnetworking2.Rest.ApiClient;
import com.syabhri.app.retrofitnetworking2.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btGet;
    ApiInterface mApiInterface;

    //perubahan di 11.2
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btGet = (Button) findViewById(R.id.btGet);

        //perubahan di 11.2
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mApiInterface  = ApiClient.getClient().create(ApiInterface.class);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<GetPembelian> pembelianCall = mApiInterface.getPembelian();
                pembelianCall.enqueue(new Callback<GetPembelian>() {
                    @Override
                    public void onResponse(Call<GetPembelian> call, Response<GetPembelian> response) {
                        List<Pembelian> pembelianList = response.body().getListDataPembelian();
                        Log.d("Retrofit Get", "Jumlah data pembelian: " + String.valueOf(pembelianList.size()));

                        //perubahan di 11.2
                        mAdapter = new MyAdapter(pembelianList);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<GetPembelian> call, Throwable t) {
                        // Log error
                        Log.e("Retrofit Get", t.toString());
                    }
                });
            }
        });
    }
}

