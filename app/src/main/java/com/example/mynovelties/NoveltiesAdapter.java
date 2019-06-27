package com.example.mynovelties;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynovelties.Activity.Open;

import java.util.ArrayList;

import static com.example.mynovelties.DBHelper.KEY_TITLE;

public class NoveltiesAdapter extends RecyclerView.Adapter<NoveltiesAdapter.NoveltiesViewHolder> {

    private ArrayList<NoveltiesList> novelties;
    private Context parent;
    private Drawable i1, i2;
    private DBHelper dbhelper;
    private SQLiteDatabase database;
    private ContentValues contentValues;
    private int index;

    public NoveltiesAdapter(ArrayList<NoveltiesList> novelties, Context parent, Drawable i1, Drawable i2) {
        this.novelties = novelties;
        this.parent = parent;
        this.i1 = i1;
        this.i2 = i2;
        dbhelper = new DBHelper(parent);
        database = dbhelper.getWritableDatabase();
        contentValues = new ContentValues();
        index = 0;
    }

    public class NoveltiesViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        ImageButton imageButton;

        public NoveltiesViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.image);
            imageButton = view.findViewById(R.id.imageButton);

            if (getContains(novelties.get(index++).url)) {
                imageButton.setImageDrawable(i2);
            } else {
                imageButton.setImageDrawable(i1);
            }

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String b1 = imageButton.getDrawable().toString();
                    String b2 = i1.toString();
                    if (b1.equals(b2) == true) {
                        add_DateBase(getAdapterPosition());
                        imageButton.setImageDrawable(i2);
                    } else {
                        del_DateBase(getAdapterPosition());
                        imageButton.setImageDrawable(i1);
                    }
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent, Open.class);
                    new TransportNovelties(novelties.get(getAdapterPosition()));
                    parent.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public NoveltiesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new NoveltiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoveltiesViewHolder noveltiesViewHolder, int i) {

        noveltiesViewHolder.title.setText(novelties.get(i).getTitle());

        new ImageLoadAsync(noveltiesViewHolder.image)
                .execute(novelties.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return novelties.size();
    }

    private void add_DateBase(int ind) {
        /*String part = read_image(ind);*/
        if (!getContains(novelties.get(ind).url)) {

            contentValues.put(KEY_TITLE, novelties.get(ind).title);
            contentValues.put(DBHelper.KEY_URLTOIMAGE, novelties.get(ind).image);
            contentValues.put(DBHelper.KEY_URL, novelties.get(ind).url);
            contentValues.put(DBHelper.KEY_DESCRIPTION, novelties.get(ind).text);
            contentValues.put(DBHelper.KEY_DATE, novelties.get(ind).date);

            database.insert(DBHelper.TABLE_CONSTANT, null, contentValues);
        }
    }

    private void del_DateBase(int ind) {

        String str = novelties.get(ind).url;
        Cursor cursor = database.query(DBHelper.TABLE_CONSTANT, null, null, null, null, null, null);

        String id = "";

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DBHelper.KEY_URL)).equals(str)) {
                    id = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ID));
                    break;
                }
            } while (cursor.moveToNext());
        }
        if (id != "")
            database.delete(DBHelper.TABLE_CONSTANT, dbhelper.KEY_ID + "= " + id, null);
    }

    private boolean getContains(String str) {
        Cursor cursor = database.query(DBHelper.TABLE_CONSTANT, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DBHelper.KEY_URL)).equals(str))
                    return true;
            } while (cursor.moveToNext());
        }
        return false;
    }

}
