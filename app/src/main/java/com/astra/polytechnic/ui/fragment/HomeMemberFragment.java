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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.astra.polytechnic.helper.DLAHelper;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.ui.activity.BookDetailActivity;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeMemberFragment extends Fragment {

    private static final String TAG = "HomeMemberFragment";
    private static final String KEY_EXTRA = "id_koleksi";

    // Components
    private RecyclerView mRvNewestBooks, mRvPopularBooks;
    private TextView mNewestSeeAll, mPopularSeeAll, mBookCount, mVisitorCount, mHistoryCount,mLoginName, mWelcomeText, mRole;
    private LinearLayout mLayoutData, mLayoutGuest;
    private ImageView mCardData;
    private ImageSlider mImageSlider;
    private List<SlideModel> mSlideModelList = new ArrayList<>();

    // Data
    private KoleksiViewModel mNewestViewModel, mNewestColectionVM,mDashboardVM;
    private List<Koleksi> mNewestList, mCollectionList;
    // Get Login Info
    SharedPreferences pref;
    // Adapter
    private HomeMemberFragment.NewestAdapter mNewestAdapter = new HomeMemberFragment.NewestAdapter(Collections.emptyList());
    private HomeMemberFragment.CollectionAdapter mPopularAdapater = new HomeMemberFragment.CollectionAdapter(Collections.emptyList());

    public static HomeMemberFragment newInstance() {
        return new HomeMemberFragment();
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
        View view =  inflater.inflate(R.layout.fragment_home_member, container, false);

        pref = getActivity().getSharedPreferences("nomor", getActivity().MODE_PRIVATE);

        mLoginName = view.findViewById(R.id.login_name);
        mWelcomeText = view.findViewById(R.id.welcome_text1);
        mRole = view.findViewById(R.id.login_role);

        mLayoutData = view.findViewById(R.id.layout_data);
        mLayoutGuest = view.findViewById(R.id.layout_data_guest);
        mCardData = view.findViewById(R.id.card_data);

        mBookCount = view.findViewById(R.id.book_count);
        mVisitorCount = view.findViewById(R.id.visitors_count);
        mHistoryCount = view.findViewById(R.id.history_count);

        mNewestSeeAll = view.findViewById(R.id.see_all_newest);
        mPopularSeeAll = view.findViewById(R.id.see_all_newestcollection);

        mRvNewestBooks = view.findViewById(R.id.rv_newest_book);
        mRvNewestBooks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvNewestBooks.setAdapter(mNewestAdapter);

        mRvPopularBooks = view.findViewById(R.id.rv_popular_books);
        mRvPopularBooks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvPopularBooks.setAdapter(mPopularAdapater);

        String namaSp = pref.getString("nama", null);
        if(namaSp != null){
            int firstName = namaSp.indexOf(' ');
            String nama = (firstName != -1) ? namaSp.substring(0, firstName) : namaSp;
            mLoginName.setText(nama);
        }else {
            mLayoutData.setVisibility(View.GONE);
            mWelcomeText.setText("Please Sign In to get all features.");
            mRole.setVisibility(View.GONE);
            mLayoutGuest.setVisibility(View.VISIBLE);

        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNewestViewModel.getNewest().observe(getViewLifecycleOwner(), this::updateNewestBook);
        mNewestColectionVM.getNewestReleased().observe(getViewLifecycleOwner(), this::updateNewestCollection);
        mDashboardVM.getDataDashboard().observe(getViewLifecycleOwner(), this::updateDashboard);
    }

    private void updateDashboard(List<Object[]> object) {
        Object[] obj = object.get(0);
        Log.d(TAG, "updateDashboard: " + obj[0].toString());

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
        Log.d(TAG, "updateNewestBook: "+ koleksiNewest);
        mNewestList = DLAHelper.getNewestBook(koleksiNewest);
        mNewestAdapter = new HomeMemberFragment.NewestAdapter(mNewestList);
        mRvNewestBooks.setAdapter(mNewestAdapter);
    }

    private void updateNewestCollection(List<Koleksi> koleksiNewest){
        Log.d(TAG, "updateNewestCollectionBook: "+ koleksiNewest);
        mCollectionList = DLAHelper.getPopularBooks(koleksiNewest);
        mPopularAdapater = new HomeMemberFragment.CollectionAdapter(mCollectionList);
        mRvPopularBooks.setAdapter(mPopularAdapater);
    }

    private class NewestAdapter extends RecyclerView.Adapter<HomeMemberFragment.NewestAdapter.NewestHolder>{
        private List<Koleksi> mKoleksis;

        public NewestAdapter(List<Koleksi> koleksis){
            mKoleksis = koleksis;
        }

        @NonNull
        @Override
        public HomeMemberFragment.NewestAdapter.NewestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new HomeMemberFragment.NewestAdapter.NewestHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(@NonNull HomeMemberFragment.NewestAdapter.NewestHolder holder, int position) {
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
//                Toast.makeText(getContext(), mKoleksi.getNama(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra(KEY_EXTRA, mKoleksi.getIdKoleksi());
                startActivity(intent);
/*                BookDetailFragment bookDetailFragment = new BookDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id_koleksi", mKoleksi.getIdKoleksi());
                bookDetailFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainter, bookDetailFragment)
                        .addToBackStack(null)
                        .commit();*/
            }
        }
    }
    private class CollectionAdapter extends RecyclerView.Adapter<HomeMemberFragment.CollectionAdapter.CollectionHolder>{
        private List<Koleksi> mKoleksis;

        public CollectionAdapter(List<Koleksi> koleksis){
            mKoleksis = koleksis;
        }

        @NonNull
        @Override
        public HomeMemberFragment.CollectionAdapter.CollectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new HomeMemberFragment.CollectionAdapter.CollectionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeMemberFragment.CollectionAdapter.CollectionHolder holder, int position) {
            Koleksi koleksi = mKoleksis.get(position);
            holder.onBindViewHolder(koleksi);
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
        }

        @Override
        public int getItemCount() {
            return mKoleksis.size();
        }

        private class CollectionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
            public void onBindViewHolder(Koleksi koleksi){
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
//                Toast.makeText(getContext(), mKoleksi.getGambar(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), mKoleksi.getNama(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra(KEY_EXTRA, mKoleksi.getIdKoleksi());
                startActivity(intent);
            }
        }
    }
}