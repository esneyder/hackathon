package com.gleamsoft.avamigables.home.adapter;

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
import com.gleamsoft.avamigables.home.model.metroPlus;

import java.util.List;

/**
 * Created by Developer on 24/09/2017.
 */

public class metroPlusAdapter  extends RecyclerView.Adapter<metroPlusAdapter.BooksViewHolder> {
private ItemClickListener clickListener;
private List<metroPlus> mDataset;
private Context context;
public metroPlusAdapter(List<metroPlus> myDataset, Context ctx) {
    mDataset = myDataset;
    this.context = ctx;
}

public void setClickListener(ItemClickListener itemClickListener) {
    this.clickListener = itemClickListener;
}

public class BooksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public CardView mProductCardView;
    public TextView direccion;
    public TextView fecha;
   
    public TextView sentido;
    public ImageView imagen;
    
    public BooksViewHolder(View itemView)  {
        super(itemView);
        mProductCardView = (CardView)itemView.findViewById(R.id.metro);
        direccion = (TextView)itemView.findViewById(R.id.gramos);
        fecha = (TextView)itemView.findViewById(R.id.fecha);
        
        sentido = (TextView)itemView.findViewById(R.id.sentido);
        imagen = (ImageView)itemView.findViewById(R.id.imagen);
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
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
    BooksViewHolder viewHolder = new BooksViewHolder(view);
    return viewHolder;
}

@Override
public void onBindViewHolder(BooksViewHolder booksViewHolder, int position) {
    booksViewHolder.direccion.setText(mDataset.get(position).getDireccionSolicitud());
    booksViewHolder.fecha.setText(String.valueOf(mDataset.get(position).getFecha()));
     
//    booksViewHolder.minutos.setText(mDataset.get(position).getMinutosRetraso());
    booksViewHolder.sentido.setText(String.valueOf(mDataset.get(position).getSentido()));

    if(position == 0) {
        Glide.with(context).load(R.drawable.moto)
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(booksViewHolder.imagen);
    }
    if(position == 1) {
        Glide.with(context).load(R.drawable.metroplus)
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(booksViewHolder.imagen);
    }
    if(position == 2) {
        Glide.with(context).load(R.drawable.bicicleta)
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(booksViewHolder.imagen);
    }
    if(position == 3) {
        Glide.with(context).load(R.drawable.carro)
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(booksViewHolder.imagen);
    }

    
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