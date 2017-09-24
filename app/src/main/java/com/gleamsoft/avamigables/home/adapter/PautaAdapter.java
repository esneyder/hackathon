package com.gleamsoft.avamigables.home.adapter;

/**
 * Created by Developer on 23/09/2017.
 */

import android.content.Context;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gleamsoft.avamigables.R;
import com.gleamsoft.avamigables.home.model.Pautas;

import java.util.List;


 

/**
 * Created by MacBook on 9/15/17.
 */
public class PautaAdapter extends RecyclerView.Adapter<PautaAdapter.BooksViewHolder> {
private ItemClickListener clickListener;
private List<Pautas> mDataset;
private Context context;
public PautaAdapter(List<Pautas> myDataset, Context ctx) {
    mDataset = myDataset;
    this.context = ctx;
}

public void setClickListener(ItemClickListener itemClickListener) {
    this.clickListener = itemClickListener;
}

public class BooksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public CardView mProductCardView;
    public TextView mBookTitle;
    public TextView mBookDescription;
    public TextView distance;
    public TextView mBookService;
    public ImageView mBookCover;
    
    public BooksViewHolder(View itemView)  {
        super(itemView);
       // mProductCardView = (CardView)itemView.findViewById(R.id.product_card_view);
        mBookTitle = (TextView)itemView.findViewById(R.id.direccion);
        distance = (TextView)itemView.findViewById(R.id.distance);
       /* mBookDescription = (TextView)itemView.findViewById(R.id.book_description);
        mBookPrice = (TextView)itemView.findViewById(R.id.book_price);
        mBookService = (TextView)itemView.findViewById(R.id.txtservicio);*/
        mBookCover = (ImageView)itemView.findViewById(R.id.book_cover);
        itemView.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        
    }
}

@Override
public BooksViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    // create a new view
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item, viewGroup, false);
    BooksViewHolder viewHolder = new BooksViewHolder(view);
    return viewHolder;
}

@Override
public void onBindViewHolder(BooksViewHolder booksViewHolder, int position) {
    booksViewHolder.mBookTitle.setText(mDataset.get(position).getPunto());
    booksViewHolder.distance.setText(String.valueOf(mDataset.get(position).getDintance()));
    /*if(mDataset.get(position).getMensaje().length() > 40)
        booksViewHolder.mBookDescription.setText(mDataset.get(position).getMensaje().substring(0,40));
    booksViewHolder.mBookPrice.setText(String.valueOf(mDataset.get(position).getPunto()));
    booksViewHolder.mBookService.setText(String.valueOf(mDataset.get(position).getDintance()));*/
    
    // Loading profile image
    Glide.with(context).load(mDataset.get(position).getFoto().getUrl())
            .crossFade()
            .thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(booksViewHolder.mBookCover);
    
}

@Override
public int getItemCount() {
    return mDataset.size();
}

@Override
public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
}
    
    
}