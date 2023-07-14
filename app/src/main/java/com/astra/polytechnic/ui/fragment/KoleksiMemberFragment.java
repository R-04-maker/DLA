package com.astra.polytechnic.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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

import java.util.Collections;
import java.util.List;

public class KoleksiMemberFragment extends Fragment {
    private static final String TAG = "Fragment";
    private List<Koleksi> mKoleksiList;
    private RecyclerView mRvKoleksi;
    private KoleksiAdapter mKoleksiAdapter = new KoleksiAdapter(Collections.emptyList());
    private KoleksiViewModel mNewestViewModel;
    private TextView mTextView;
    private ImageView mImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewestViewModel = new ViewModelProvider(this).get(KoleksiViewModel.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_member, container, false);
        mImageView=view.findViewById(R.id.imageView7);
        mImageView.setEnabled(false);
        mImageView.setClickable(false);
        mImageView.setFocusable(false);
        mRvKoleksi = view.findViewById(R.id.recycler_view1);
        mRvKoleksi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRvKoleksi.setAdapter(mKoleksiAdapter);
        mTextView = view.findViewById(R.id.textViewye);
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mScrollView.setVisibility(View.INVISIBLE);
        mNewestViewModel.getNewest().observe(getViewLifecycleOwner(), this::updateNewestBook);
    }
    private void updateNewestBook(List<Koleksi> koleksiNewest){
        Log.d(TAG, "updateNewestBook: "+ koleksiNewest);
        mKoleksiList = DLAHelper.getNewestBook(koleksiNewest);
        mKoleksiAdapter = new KoleksiAdapter(mKoleksiList);
        mRvKoleksi.setAdapter(mKoleksiAdapter);
    }
    private class KoleksiAdapter extends RecyclerView.Adapter<KoleksiAdapter.KoleksiHolder> {
        private List<Koleksi> mKoleksis;
        ImageView mImageView;

        public KoleksiAdapter(List<Koleksi> koleksis){
            mKoleksis = koleksis;
        }
        @NonNull
        @Override
        public KoleksiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new KoleksiHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull KoleksiHolder holder, int position) {
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
            private TextView mBookTitle;
            private Koleksi mKoleksi;
            private TextView deskripsi;
            private String mEditableTitle;
            ImageView imageView;

            public KoleksiHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.fragment_koleksi_member, parent, false));

                mBookImage = itemView.findViewById(R.id.cover_book_newest);
                mBookTitle = itemView.findViewById(R.id.title_book_newest);
                deskripsi=itemView.findViewById(R.id.title_book_pengarang);

                itemView.setOnClickListener(this);
            }


            private void onBindViewHolder(Koleksi koleksi) {

                mEditableTitle = koleksi.getNama();
                if(mEditableTitle.length() > 25 ){
                    mBookTitle.setText(mEditableTitle.substring(0, 25));
                    deskripsi.setText(koleksi.getDeskripsi());

                }else {
                    mBookTitle.setText(koleksi.getNama());
                    deskripsi.setText(koleksi.getDeskripsi());
                }
                mKoleksi = koleksi;

            }
            @Override
            public void onClick(View v) {
//                getChildFragmentManager().beginTransaction()
//                        .setReorderingAllowed(true)
//                        .replace(R.id.fragmentContainerView, BookDetailFragment.class, null)
//                        .commit();
                Intent intent=new Intent(getContext(), BookDetailFragment.class);
                intent.putExtra("id",mKoleksi.getIdKoleksi());
                intent.putExtra("nama",mKoleksi.getNama());
                intent.putExtra("deskripsi",mKoleksi.getDeskripsi());
            }
        }
    }


}
