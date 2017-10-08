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
import com.gleamsoft.avamigables.home.model.Info;

import java.util.List;


 

/**
 * Created by MacBook on 9/15/17.
 */
public class PautaAdapter extends RecyclerView.Adapter<PautaAdapter.BooksViewHolder> {
private ItemClickListener clickListener;
private List<Info> mDataset;
private Context context;
public PautaAdapter(List<Info> myDataset, Context ctx) {
    mDataset = myDataset;
    this.context = ctx;
}

public void setClickListener(ItemClickListener itemClickListener) {
    this.clickListener = itemClickListener;
}

public class BooksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public CardView mProductCardView;
    public TextView gramos;

    public TextView titulo;
    
    public BooksViewHolder(View itemView)  {
        super(itemView);
       // mProductCardView = (CardView)itemView.findViewById(R.id.product_card_view);
        gramos = (TextView)itemView.findViewById(R.id.avgramos);
        titulo = (TextView)itemView.findViewById(R.id.titulo);

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
    booksViewHolder.gramos.setText(mDataset.get(position).getGramos() +"");
    booksViewHolder.titulo.setText(String.valueOf(mDataset.get(position).getTitulo()));
    /*if(mDataset.get(position).getMensaje().length() > 40)
        booksViewHolder.mBookDescription.setText(mDataset.get(position).getMensaje().substring(0,40));
    booksViewHolder.mBookPrice.setText(String.valueOf(mDataset.get(position).getPunto()));
    booksViewHolder.mBookService.setText(String.valueOf(mDataset.get(position).getDintance()));*/
    

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