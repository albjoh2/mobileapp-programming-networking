package com.example.networking;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=brom";
    private final ArrayList<Mountain> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new JsonTask(this).execute(JSON_URL);

    }




    public static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private final List<Mountain> items;
        private final LayoutInflater layoutInflater;
        private final OnClickListener onClickListener;

        RecyclerViewAdapter(Context context, List<Mountain> items, OnClickListener onClickListener) {
            this.layoutInflater = LayoutInflater.from(context);
            this.items = items;
            this.onClickListener = onClickListener;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(layoutInflater.inflate(R.layout.recyclerviewitem, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.title.setText(items.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title;

            ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                title = itemView.findViewById(R.id.title);
            }

            @Override
            public void onClick(View view) {
                onClickListener.onClick(items.get(getAdapterPosition()));
            }
        }

        public interface OnClickListener {
            void onClick(Mountain item);
        }
    }

    @Override
    public void onPostExecute(String json) {
        Log.d("MainActivity", json);
        // Create GSON object to perform marshall/unmarshall operations


        Gson gson = new Gson();
        Type type = new TypeToken<List<Mountain>>() {
        }.getType();
        ArrayList<Mountain> items = gson.fromJson(json, type);
        RecyclerViewAdapter adapter;
        adapter = new RecyclerViewAdapter(this, items, item -> Toast.makeText(MainActivity.this, item.toString(), Toast.LENGTH_SHORT).show());
        adapter.notifyDataSetChanged();

        RecyclerView view = findViewById(R.id.recyclerview);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
    }

}
