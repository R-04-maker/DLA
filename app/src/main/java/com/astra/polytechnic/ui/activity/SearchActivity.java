package com.astra.polytechnic.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.astra.polytechnic.model.Koleksi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    // ini buat ngamb data dari databasenya
    private KoleksiViewModel mKoleksiViewModel;

    // buat bikin recycler view kita perlu rcyclerview , adapter dan holder sama model dari datanya
    private RecyclerView mRecyclerViewKoleksi;
//    private KoleksiAdapter mKoleksiAdapter = new KoleksiAdapter(Collections.emptyList());
    private KoleksiAdapter mKoleksiAdapter;
    private List<Koleksi> mKoleksiList;
    SharedPreferences pref;

    // buat bikin search kita butuh tempat buat search sama nampilin kalau datanya gak ada
    private TextInputEditText mSearchLayout;
    private ImageView mKeranjang;
    private View mLayoutEmpty;
    private GridLayoutManager mGridLayoutManager;
    private ImageButton mToggleBtn;

    // kalau di activity di deklrasiinnya di oncreatenya yaa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // biasanya berisi deklarasi dari utility yang kita butuhin , contohnya viewmodel di bawah ini
        mKoleksiViewModel = new ViewModelProvider(this).get(KoleksiViewModel.class);
        mRecyclerViewKoleksi = findViewById(R.id.searchrecycler);
        mGridLayoutManager = new GridLayoutManager(this, KoleksiAdapter.SPAN_COUNT_ONE);
        mRecyclerViewKoleksi.setAdapter(mKoleksiAdapter);
        mLayoutEmpty = findViewById(R.id.layout_empty_data);
        mSearchLayout = findViewById(R.id.tie_search);

        mKeranjang = findViewById(R.id.icon_cart);
        pref = SearchActivity.this.getSharedPreferences("nomor", SearchActivity.MODE_PRIVATE);
        String namaSp = pref.getString("id_role", null);
        if (namaSp != null) {
            Log.v("TEST", "ROLE = " + namaSp);
            if(namaSp.equals("ROL06")){
                mKeranjang.setVisibility(View.VISIBLE);
            }else {
                mKeranjang.setVisibility(View.GONE);

            }
        }

        mToggleBtn = findViewById(R.id.toggle);
        mToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLayout();
                switchIcon();
            }
        });

        // tipe datanya kalau di activity harus pake muttable live data -> check view model , repository , dan servicenya yaa
        mKoleksiViewModel.getBukuByNamaMt().observe(this, koleksis -> {
            Log.v("TEST",koleksis.size()+"");
            updateKoleksi(koleksis);
        });
//        mRecyclerViewKoleksi.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewKoleksi.setLayoutManager(mGridLayoutManager);
        mSearchLayout.addTextChangedListener(new TextWatcher() {
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
        mKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, KeranjangActivity.class);
                startActivity(intent);
            }
        });
    }
    private void switchLayout(){
        if(mGridLayoutManager.getSpanCount() == KoleksiAdapter.SPAN_COUNT_ONE){
            mGridLayoutManager.setSpanCount(KoleksiAdapter.SPAN_COUNT_THREE);
        }else {
            mGridLayoutManager.setSpanCount(KoleksiAdapter.SPAN_COUNT_ONE);
        }
        // Perbarui jumlah kolom di GridLayoutManager yang sudah ada
        mGridLayoutManager.setSpanCount(mGridLayoutManager.getSpanCount());

        // Tetapkan GridLayoutManager yang baru ke RecyclerView
        mRecyclerViewKoleksi.setLayoutManager(mGridLayoutManager);

        // Perbarui viewType adapter dengan layout manager yang baru
        mKoleksiAdapter.setViewType(mGridLayoutManager.getSpanCount());
        mKoleksiAdapter.notifyItemRangeChanged(0,mKoleksiAdapter.getItemCount());
    }

    private void switchIcon(){
        if(mGridLayoutManager.getSpanCount() == KoleksiAdapter.SPAN_COUNT_ONE){
            mToggleBtn.setImageResource(R.drawable.view_list);
        }else {
            mToggleBtn.setImageResource(R.drawable.grid_view);
        }
    }
    private void updateKoleksi(List<Koleksi> koleksis){
        mKoleksiList = koleksis;
        Log.v("TEST",koleksis.size()+"");
        mKoleksiAdapter = new KoleksiAdapter(mKoleksiList, mGridLayoutManager);
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
        public static final int SPAN_COUNT_ONE = 1;
        public static final int SPAN_COUNT_THREE = 3;

        private static final int VIEW_ONE = 1;
        private static final int VIEW_THREE = 3;
        private int mViewType;

        private GridLayoutManager mGridLayoutManager;
        public KoleksiAdapter(List<Koleksi> koleksis, GridLayoutManager gridLayoutManager){
            mKoleksis = koleksis;
            mGridLayoutManager = gridLayoutManager;
        }
        // Method untuk mengubah tipe tampilan (dipanggil dari SearchActivity)
        public void setViewType(int viewType) {
            mViewType = viewType;
        }
        @Override
        public int getItemViewType(int position) {
            int spanCount = mGridLayoutManager.getSpanCount();
            if(spanCount == SPAN_COUNT_ONE){
                return VIEW_ONE;
            }else {
                return VIEW_THREE;
            }
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
            View view;
            if(viewType == VIEW_ONE){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_books, parent, false);
                mViewType = viewType;
            }else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_grid_view, parent, false);
                mViewType = viewType;
            }
//            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new KoleksiAdapter.KoleksiHolder(view, viewType);
        }
        @Override
        public void onBindViewHolder(@NonNull KoleksiAdapter.KoleksiHolder holder, int position) {
            Koleksi koleksi = mKoleksis.get(position);
            Log.d("TEST",koleksi.getNama());
            holder.bind(koleksi, mViewType);
        }

        @Override
        public int getItemCount() {
            return mKoleksis.size();
        }

        private class KoleksiHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private ImageView mBookImage;
            private TextView mBookTitle, mAuthor, mYearBook, mCategoryBook,mRak;
            private Koleksi mKoleksi;
/*            public KoleksiHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item _detail_books, parent, false));
                mBookImage = itemView.findViewById(R.id.cover_book_newest);
                mBookTitle = itemView.findViewById(R.id.title_book);
                mAuthor = itemView.findViewById(R.id.author_book);
                mYearBook = itemView.findViewById(R.id.year_book);
                mCategoryBook = itemView.findViewById(R.id.category_book);
                mRak = itemView.findViewById(R.id.rak);
                itemView.setOnClickListener(this);
            }*/
            public KoleksiHolder(View itemView, int viewType) {
//                super(inflater.inflate(R.layout.item_detail_books, parent, false));
                super(itemView);
                if(viewType == VIEW_ONE){
                    mBookImage = itemView.findViewById(R.id.cover_book_list_view);
                    mBookTitle = itemView.findViewById(R.id.title_book);
                    mAuthor = itemView.findViewById(R.id.author_book);
                    mYearBook = itemView.findViewById(R.id.year_book);
                    mCategoryBook = itemView.findViewById(R.id.category_book);
                    mRak = itemView.findViewById(R.id.rak);
                }else {
                    mBookImage = itemView.findViewById(R.id.cover_book_grid_view);
                    mBookTitle = itemView.findViewById(R.id.title_book_grid_view);
                }

                itemView.setOnClickListener(this);
            }
            public void bind(Koleksi koleksi, int viewType){
                mKoleksi = koleksi;
                if(viewType == VIEW_ONE){
                    mBookTitle.setText(koleksi.getNama());
                    mAuthor.setText(koleksi.getPengarang());
                    mYearBook.setText(koleksi.getTahunTerbit());
                    mCategoryBook.setText(koleksi.getIdKategori().getNama());
                    mRak.setText(koleksi.getIdRak().getNama());
                    if(!koleksi.getGambar().equals("KOSONG") && !koleksi.getGambar().equals("IMG_NoImage.jpg")){
                        Picasso.get()
                                .load(koleksi.getGambar())
                                .placeholder(R.drawable.no_cover_book)
                                .error(R.drawable.no_cover_book)
                                .into(mBookImage);
                    }else {
                        mBookImage.setImageResource(R.drawable.no_cover_book);
                    }
                }else {
                    mBookTitle.setText(koleksi.getNama());
                    if(!koleksi.getGambar().equals("KOSONG") && !koleksi.getGambar().equals("IMG_NoImage.jpg")){
                        Picasso.get()
                                .load(koleksi.getGambar())
                                .placeholder(R.drawable.no_cover_book)
                                .error(R.drawable.no_cover_book)
                                .into(mBookImage);
                    }else {
                        mBookImage.setImageResource(R.drawable.no_cover_book);
                    }
                }
            }
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, BookDetailActivity.class);
                intent.putExtra("id_koleksi", mKoleksi.getIdKoleksi());
                startActivity(intent);
            }
        }
    }
    public void BacktoHome(View view){
        finish();
    }
}
