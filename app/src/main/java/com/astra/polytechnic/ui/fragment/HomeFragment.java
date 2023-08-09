package com.astra.polytechnic.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.astra.polytechnic.helper.DLAHelper;
import com.astra.polytechnic.model.Dashboard;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.repository.KoleksiRepository;
import com.astra.polytechnic.ui.activity.BookDetailActivity;
import com.astra.polytechnic.ui.activity.SearchActivity;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "HomeFragment";
    private static final String KEY_EXTRA = "id_koleksi";

    // Components
    private RecyclerView mRvNewestBooks, mRvPopularBooks;
    private EditText searchTxt;
    private TextView mBookCount, mVisitorCount, mHistoryCount,mLoginName;

    // Data
    private KoleksiViewModel mNewestViewModel, mNewestColectionVM,mDashboardVM;
    private List<Koleksi> mNewestList, mCollectionList;
    // Get Login Info
    SharedPreferences pref;
    // Adapter
    private NewestAdapter mNewestAdapter = new NewestAdapter(Collections.emptyList());
    private CollectionAdapter mPopularAdapater = new CollectionAdapter(Collections.emptyList());
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewestViewModel = new ViewModelProvider(this).get(KoleksiViewModel.class);
        mNewestColectionVM = new ViewModelProvider(this).get(KoleksiViewModel.class);
        mDashboardVM = new ViewModelProvider(this).get(KoleksiViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        pref = getActivity().getSharedPreferences("nomor", getActivity().MODE_PRIVATE);
        mLoginName = view.findViewById(R.id.login_name);
        String namaSp = pref.getString("nama", null);
        int firstName = namaSp.indexOf(' ');
        String nama = (firstName != -1) ? namaSp.substring(0, firstName) : namaSp;
        mLoginName.setText(nama);

        mBookCount = view.findViewById(R.id.book_count);
        mVisitorCount = view.findViewById(R.id.visitors_count);
        mHistoryCount = view.findViewById(R.id.history_count);

        mRvNewestBooks = view.findViewById(R.id.rv_newest_book);
        mRvNewestBooks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvNewestBooks.setAdapter(mNewestAdapter);

        mRvPopularBooks = view.findViewById(R.id.rv_popular_books);
        mRvPopularBooks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvPopularBooks.setAdapter(mPopularAdapater);

        searchTxt = view.findViewById(R.id.searchBtn);
        searchTxt.setFocusable(false);
        searchTxt.setClickable(false);
        searchTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNewestViewModel.getNewestCollection().observe(getViewLifecycleOwner(), this::updateNewestBook);
        mNewestColectionVM.getNewestReleased().observe(getViewLifecycleOwner(), this::updateNewestCollection);
        mDashboardVM.getDataDashboard().observe(getViewLifecycleOwner(), this::updateDashboard);
    }

    private void updateDashboard(List<Object[]> object) {
        Object[] obj = object.get(0);

        // Parse values as double instead of int to handle decimal numbers
        double bookCount = Double.parseDouble(obj[0].toString());
        double visitorCount = Double.parseDouble(obj[1].toString());
        double historyCount = Double.parseDouble(obj[2].toString());

        // Convert the double values to int if necessary
        int parsedBookCount = (int) bookCount;
        int parsedVisitorCount = (int) visitorCount;
        int parsedHistoryCount = (int) historyCount;

        mBookCount.setText(String.valueOf(parsedBookCount));
        mVisitorCount.setText(String.valueOf(parsedVisitorCount));
        mHistoryCount.setText(String.valueOf(parsedHistoryCount));
    }

    private void updateNewestBook(List<Koleksi> koleksiNewest){
        mNewestList = DLAHelper.getNewestBook(koleksiNewest);
        mNewestAdapter = new NewestAdapter(mNewestList);
        mRvNewestBooks.setAdapter(mNewestAdapter);
    }

    private void updateNewestCollection(List<Koleksi> koleksiNewest){
        mCollectionList = DLAHelper.getPopularBooks(koleksiNewest);
        mPopularAdapater = new CollectionAdapter(mCollectionList);
        mRvPopularBooks.setAdapter(mPopularAdapater);
    }

    @Override
    public void onClick(View v) {
    }

    private class NewestAdapter extends RecyclerView.Adapter<NewestAdapter.NewestHolder>{
        private List<Koleksi> mKoleksis;

        public NewestAdapter(List<Koleksi> koleksis){
            mKoleksis = koleksis;
        }

        @NonNull
        @Override
        public NewestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new NewestHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(@NonNull NewestHolder holder, int position) {
            Koleksi koleksi = mKoleksis.get(position);
            holder.bind(koleksi);
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
        }

        @Override
        public int getItemCount() {
            return mKoleksis.size();
        }

        private class NewestHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private ImageView mBookImage;
            private TextView mBookTitle;
            private Koleksi mKoleksi;
            private String mEditableTitle;

            public NewestHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_card_book_newest, parent, false));

                mBookImage = itemView.findViewById(R.id.cover_book_newest);
                mBookTitle = itemView.findViewById(R.id.title_book_newest);
                itemView.setOnClickListener(this);
            }
            public void bind(Koleksi koleksi){
                mKoleksi = koleksi;
                if(!koleksi.getGambar().equals("KOSONG") && !koleksi.getGambar().equals("IMG_NoImage.jpg")){
                    Picasso.get()
                            .load(koleksi.getGambar())
                            .placeholder(R.drawable.no_cover_book)
                            .error(R.drawable.no_cover_book)
                            .into(mBookImage);
                }else {
                    mBookImage.setImageResource(R.drawable.no_cover_book);
                }
                mEditableTitle = koleksi.getNama();
                if(mEditableTitle.length() > 20 ){
                    mBookTitle.setText(mEditableTitle.substring(0,20));
                }else {
                    mBookTitle.setText(koleksi.getNama());
                }
            }
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra(KEY_EXTRA, mKoleksi.getIdKoleksi());
                startActivity(intent);
            }
        }
    }
    private class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionHolder> {
        private List<Koleksi> mKoleksis;

        public CollectionAdapter(List<Koleksi> koleksis) {
            mKoleksis = koleksis;
        }

        @NonNull
        @Override
        public CollectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CollectionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CollectionHolder holder, int position) {
            Koleksi koleksi = mKoleksis.get(position);
            holder.onBindViewHolder(koleksi);
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
        }

        @Override
        public int getItemCount() {
            return mKoleksis.size();
        }

        private class CollectionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private ImageView mBookImage;
            private TextView mBookTitle;
            private Koleksi mKoleksi;
            private String mEditableTitle;

            public CollectionHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_card_book_newest, parent, false));

                mBookImage = itemView.findViewById(R.id.cover_book_newest);
                mBookTitle = itemView.findViewById(R.id.title_book_newest);
                itemView.setOnClickListener(this);
            }

            public void onBindViewHolder(Koleksi koleksi) {
                mKoleksi = koleksi;
                if (!koleksi.getGambar().equals("KOSONG") && !koleksi.getGambar().equals("IMG_NoImage.jpg")) {
                    Picasso.get()
                            .load(koleksi.getGambar())
                            .placeholder(R.drawable.no_cover_book)
                            .error(R.drawable.no_cover_book)
                            .into(mBookImage);
                } else {
                    mBookImage.setImageResource(R.drawable.no_cover_book);
                }
                mEditableTitle = koleksi.getNama();
                if (mEditableTitle.length() > 20) {
                    mBookTitle.setText(mEditableTitle.substring(0, 20));
                } else {
                    mBookTitle.setText(koleksi.getNama());
                }
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("id_koleksi", mKoleksi.getIdKoleksi());
                startActivity(intent);
            }
        }
    }
}