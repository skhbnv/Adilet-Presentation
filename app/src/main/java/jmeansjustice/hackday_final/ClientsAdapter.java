package jmeansjustice.hackday_final;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientViewHolder> {

    private List<Users> mUsersList;

    public ClientsAdapter() {
        mUsersList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ClientViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_client, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder clientViewHolder, int i) {
        clientViewHolder.bindClient(mUsersList.get(i));
    }

    public void addUser(Users users) {
        mUsersList.add(users);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameView;
        private TextView mSurnameView;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameView = itemView.findViewById(R.id.usersNames);
            mSurnameView = itemView.findViewById(R.id.usersSurnames);
        }

        public void bindClient(Users users) {
            mNameView.setText(itemView.getContext().getString(R.string.name_fmt, users.getmName()));
            mSurnameView.setText(itemView.getContext().getString(R.string.surname_fmt, users.getmSurname()));
        }
    }
}
