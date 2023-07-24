package com.astra.polytechnic.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.astra.polytechnic.model.Koleksi;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    // ini buat ngambil data dari databasenya
    private KoleksiViewModel mKoleksiViewModel;

    // buat bikin recycler view kita perlu rcyclerview , adapter dan holder sama model dari datanya
    private RecyclerView mRecyclerViewKoleksi;
    private KoleksiAdapter mKoleksiAdapter= new KoleksiAdapter(Collections.emptyList());
    private List<Koleksi> mKoleksiList;

    // buat bikin search kita butuh tempat buat search sama nampilin kalau datanya gak ada
    private TextInputLayout mSearchLayout;
    private View mLayoutEmpty;

    // kalau di activity di deklrasiinnya di oncreatenya yaa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // biasanya berisi deklarasi dari utility yang kita butuhin , contohnya viewmodel di bawah ini
        mKoleksiViewModel = new ViewModelProvider(this).get(KoleksiViewModel.class);
        mRecyclerViewKoleksi = findViewById(R.id.searchrecycler);
        mRecyclerViewKoleksi.setAdapter(mKoleksiAdapter);
        mLayoutEmpty = findViewById(R.id.layout_empty_data);
        mSearchLayout = findViewById(R.id.til_search);

        // tipe datanya kalau di activity harus pake muttable live data -> check view model , repository , dan servicenya yaa
        mKoleksiViewModel.getBukuByNamaMt().observe(this, koleksis -> {
            Log.v("TEST",koleksis.size()+"");
            updateKoleksi(koleksis);
        });
        mRecyclerViewKoleksi.setLayoutManager(new LinearLayoutManager(this));
        mSearchLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // ini method yang bakal kita gunain buat search, jadi selama kita ketik huruf dia bakal nyari di list datanya
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }


    private void updateKoleksi(List<Koleksi> koleksis){
        mKoleksiList = koleksis;
        Log.v("TEST",koleksis.size()+"");
        mKoleksiAdapter = new KoleksiAdapter(mKoleksiList);
        mRecyclerViewKoleksi.setAdapter(mKoleksiAdapter);
    }

    // ini method buat searchnya
    public void filter(String text) {
        List<Koleksi> filtered = new ArrayList<>();

        for (Koleksi s : mKoleksiList) {
            if (s.getNama().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(s);
            }
        }

        mKoleksiAdapter.filterList(filtered);
    }

    // ini adapter recyler viewnya
    private class KoleksiAdapter extends RecyclerView.Adapter<KoleksiAdapter.KoleksiHolder>{
        private List<Koleksi> mKoleksis;

        public KoleksiAdapter(List<Koleksi> koleksis){
            mKoleksis = koleksis;
        }

        // nah biar bisa kesambung sama adapternya, kita butuh method filter juga di dalem sini
        public void filterList(List<Koleksi> filtered) {
            if (filtered.size() == 0) {
                mLayoutEmpty.setVisibility(View.VISIBLE);
                mRecyclerViewKoleksi.setVisibility(View.GONE);
            } else {
                mLayoutEmpty.setVisibility(View.GONE);
                mRecyclerViewKoleksi.setVisibility(View.VISIBLE);
            }
            this.mKoleksis = filtered;
            notifyDataSetChanged();
        }

        // ini holder yang biasanya digunain buat deklrasiin tampilan yang ada di item recyler viewnya
        @NonNull
        @Override
        public KoleksiAdapter.KoleksiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new KoleksiAdapter.KoleksiHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(@NonNull KoleksiAdapter.KoleksiHolder holder, int position) {
            Koleksi koleksi = mKoleksis.get(position);
            Log.d("TEST",koleksi.getNama());
            holder.bind(koleksi);
        }

        @Override
        public int getItemCount() {
            return mKoleksis.size();
        }

        private class KoleksiHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private ImageView mBookImage;
            private TextView mBookTitle;
            private Koleksi mKoleksi;
            public KoleksiHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_card_book_newest, parent, false));
                mBookImage = itemView.findViewById(R.id.cover_book_newest);
                mBookTitle = itemView.findViewById(R.id.title_book_newest);
                itemView.setOnClickListener(this);
            }
            public void bind(Koleksi koleksi){
                mKoleksi = koleksi;
                mBookTitle.setText(koleksi.getNama());
            }
            @Override
            public void onClick(View v) {

            }
        }
    }
}
