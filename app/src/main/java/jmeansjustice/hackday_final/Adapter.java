package jmeansjustice.hackday_final;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements PersonalPage.itemCreatedInterface  {
    int adapterPosition;
    private List<Users> users;

    Adapter(List<Users> users){ this.users = users;}

    @Override
    public void onItemCreated(String id) {
        //дописать открытие layouta
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener{
        TextView name;
        TextView stazh;
        TextView self_repr;
        TextView category;
        private RecyclerViewItemClickListener recyclerViewItemClickListener;

        void setItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener){
            this.recyclerViewItemClickListener = recyclerViewItemClickListener;
        }
        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.onItemClicked(v, getAdapterPosition() , false);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener((View.OnClickListener) this);
            itemView.setOnLongClickListener((View.OnLongClickListener) this);

            name = itemView.findViewById(R.id.user_card_name);
            stazh = itemView.findViewById(R.id.stazh);
            self_repr = itemView.findViewById(R.id.self_representation);
            category = itemView.findViewById(R.id.category);
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_card, viewGroup
                , false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Users currentUser = users.get(i);
        viewHolder.name.setText(currentUser.getmName()+" "+currentUser.getmSurname());
        viewHolder.stazh.setText(currentUser.getmAge());
        viewHolder.category.setText(currentUser.getmCategory());
        viewHolder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(View v, int position, boolean isLongClick) {
                if (isLongClick) {
                    Log.d("possition", "onItemClicked: " + position);
                }else {
                    users.get(position).getmID();
                    String id = users.get(position).getmID();
                    onItemCreated(id);
                }
            }
        });

    }

    @Override
    public int getItemCount() {return users.size();}
}
