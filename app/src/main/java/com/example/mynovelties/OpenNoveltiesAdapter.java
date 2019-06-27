package com.example.mynovelties;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OpenNoveltiesAdapter extends RecyclerView.Adapter<OpenNoveltiesAdapter.NoveltiesViewHolder> {

    private ArrayList<NoveltiesList> novelties;
    private Context parent;

    //При загрузке
    public OpenNoveltiesAdapter(ArrayList<NoveltiesList> novelties, Context parent) {
        this.novelties = novelties;
        this.parent = parent;
    }

    public class NoveltiesViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView text;
        ImageView image;
        Button button;

        //отображение записей
        public NoveltiesViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            text = view.findViewById(R.id.text);
            image = view.findViewById(R.id.image);
            button = view.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri address = Uri.parse(TransportNovelties.NL.url);
                    parent.startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, address), "Browser"));
                }
            });
        }
    }

    @NonNull
    @Override
    public NoveltiesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.open_novelties, viewGroup, false);
        return new NoveltiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoveltiesViewHolder noveltiesViewHolder, int i) {

        String date = novelties.get(i).getDate();

        String resultStr = date.substring(8, 10) + "-" +
                date.substring(5, 7) + "-" +
                date.substring(0, 4) + " " +
                date.substring(11, 13) + ":" +
                date.substring(14, 16);

        noveltiesViewHolder.title.setText(novelties.get(i).getTitle());
        noveltiesViewHolder.text.setText(novelties.get(i).getText()
                + "\n\n" + resultStr);

        new ImageLoadAsync(noveltiesViewHolder.image)
                .execute(novelties.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return novelties.size();
    }

}