//package com.astra.polytechnic;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
//    private static final String TAG = "RecyclerViewAdapterNewest";
//
//    // vars
//    private ArrayList<String> mNames = new ArrayList<>();
//    private ArrayList<String> mImageUrls = new ArrayList<>();
//    private Context mContext;
//
//    public RecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls){
//        mNames = names;
//        mImageUrls = imageUrls;
//        mContext = context;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_book_newest, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        Log.d(TAG, "onCreateViewHolder: Called");
//
//        Glide.with(mContext)
//                .asBitmap()
//                .load(mImageUrls.get(holder.getAdapterPosition()))
//                .into(holder.mCoverBook);
//
//        holder.mTitleBook.setText(mNames.get(holder.getAdapterPosition()));
//
//        holder.mCoverBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: Clicked on an Image : " + mNames.get(holder.getAdapterPosition()));
//                Toast.makeText(mContext, mNames.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mImageUrls.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView mCoverBook;
//        TextView mTitleBook;
//
//        public ViewHolder(View itemView){
//            super(itemView);
//            mCoverBook = itemView.findViewById(R.id.cover_book_newest);
//            mTitleBook = itemView.findViewById(R.id.title_book_newest);
//        }
//    }
//}
