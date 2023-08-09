package com.astra.polytechnic.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.ui.activity.BookDetailActivity;
import com.astra.polytechnic.ui.activity.HistoryMemberActivity;
import com.astra.polytechnic.ui.activity.KeranjangActivity;
import com.astra.polytechnic.ui.activity.SearchActivity;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class BooksCollectionFragment extends Fragment {
    private static final String TAG = "BooksCollectionFragment";
    private List<Koleksi> mKoleksiList;
    private RecyclerView mRvKoleksi;
    private BooksCollectionFragment.KoleksiAdapter mKoleksiAdapter = new BooksCollectionFragment.KoleksiAdapter(Collections.emptyList());
    private KoleksiViewModel mNewestViewModel;
    private EditText mSearchBtn;
    SharedPreferences pref;
    private ImageView mImageView,mbtnHistory;
    private LottieAnimationView mLoadingIndicator;

    ImageView mKeranjang;
    public BooksCollectionFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewestViewModel = new ViewModelProvider(this).get(KoleksiViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_collection, container, false);

        // Inflate the layout for this fragment
        mImageView = view.findViewById(R.id.imageView7);
        mKeranjang = view.findViewById(R.id.icon_cart);
        mbtnHistory = view.findViewById(R.id.btn_all_transaction);
        mKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KeranjangActivity.class);
                startActivity(intent);
            }
        });

        mbtnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryMemberActivity.class);
                startActivity(intent);
            }
        });
        mImageView.setEnabled(false);
        mImageView.setClickable(false);
        mImageView.setFocusable(false);
        mRvKoleksi = view.findViewById(R.id.list_collection);
        mRvKoleksi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRvKoleksi.setAdapter(mKoleksiAdapter);

        mSearchBtn = view.findViewById(R.id.searchBtn);
        mSearchBtn.setFocusable(false);
        mSearchBtn.setClickable(false);

        mLoadingIndicator = view.findViewById(R.id.loading_indicator_2);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        pref = getActivity().getSharedPreferences("nomor", SearchActivity.MODE_PRIVATE);
        String namaSp = pref.getString("id_role", null);
        if (namaSp != null) {
            if(namaSp.equals("ROL06")){
                mKeranjang.setVisibility(View.VISIBLE);
            }else {
                mKeranjang.setVisibility(View.GONE);

            }
        }
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mNewestViewModel.getBukuByNamaMt().observe(getViewLifecycleOwner(), this::updateNewestBook);
        mLoadingIndicator.setVisibility(View.GONE);

    }
    private void updateNewestBook(List<Koleksi> koleksiNewest){
        mKoleksiList = koleksiNewest;
        mKoleksiAdapter = new BooksCollectionFragment.KoleksiAdapter(mKoleksiList);
        mRvKoleksi.setAdapter(mKoleksiAdapter);

        // Terapkan animasi fade-in pada RecyclerView
        Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up );
        mRvKoleksi.startAnimation(fadeIn);
    }
    private class KoleksiAdapter extends RecyclerView.Adapter<BooksCollectionFragment.KoleksiAdapter.KoleksiHolder> {
        private List<Koleksi> mKoleksis;
        ImageView mImageView;

        public KoleksiAdapter(List<Koleksi> koleksis){
            mKoleksis = koleksis;
        }
        @NonNull
        @Override
        public BooksCollectionFragment.KoleksiAdapter.KoleksiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new BooksCollectionFragment.KoleksiAdapter.KoleksiHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BooksCollectionFragment.KoleksiAdapter.KoleksiHolder holder, int position) {
            Koleksi koleksi = mKoleksis.get(position);
            holder.onBindViewHolder(koleksi);
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
        }

        @Override
        public int getItemCount() {
            return mKoleksis.size();
        }

        private class KoleksiHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private ImageView mBookImage;
            private TextView mBookTitle, mAuthor, mYearBook, mCategoryBook, mRak, mStatus;
            private Koleksi mKoleksi;
            private CardView mStatusCard;

            public KoleksiHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_detail_books, parent, false));

                mBookImage = itemView.findViewById(R.id.cover_book_list_view);
                mBookTitle = itemView.findViewById(R.id.title_book);
                mAuthor = itemView.findViewById(R.id.author_book);
                mYearBook = itemView.findViewById(R.id.year_book);
                mCategoryBook = itemView.findViewById(R.id.category_book);
                mRak = itemView.findViewById(R.id.rak_buku_list);
                mStatus = itemView.findViewById(R.id.status_text);
                mStatusCard = itemView.findViewById(R.id.status_buku_cardView);

                itemView.setOnClickListener(this);
            }


            private void onBindViewHolder(Koleksi koleksi) {
                mKoleksi = koleksi;
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
                if(koleksi.getStatuspinjam() == 0){
                    mStatusCard.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.card_ditolak));
                    mStatus.setText("Tidak Tersedia");
                }else {
                    mStatusCard.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.card_dipinjam));
                    mStatus.setText("Tersedia");
                }
            }
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BookDetailActivity.class);
                intent.putExtra("id_koleksi",mKoleksi.getIdKoleksi());
                startActivity(intent);
            }
        }
    }
}
