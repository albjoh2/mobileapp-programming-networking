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

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "HTTPS_URL_TO_JSON_DATA_CHANGE_THIS_URL";
    private final String JSON_FILE = "mountains.json";
    ArrayList<RecyclerViewItem> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new JsonFile(this, this).execute(JSON_FILE);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, items, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(RecyclerViewItem item) {
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView view = findViewById(R.id.recyclerview);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);


    }



    @SuppressWarnings("WeakerAccess")
    public static class RecyclerViewItem {

        private final String title;

        public RecyclerViewItem(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }



    public static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private final List<RecyclerViewItem> items;
        private final LayoutInflater layoutInflater;
        private final OnClickListener onClickListener;

        RecyclerViewAdapter(Context context, List<RecyclerViewItem> items, OnClickListener onClickListener) {
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
            holder.title.setText(items.get(position).getTitle());
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
            void onClick(RecyclerViewItem item);
        }
    }

    @Override
    public void onPostExecute(String json) {
        Log.d("MainActivity", json);
    }

}
