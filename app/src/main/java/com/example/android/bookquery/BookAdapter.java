package com.example.android.bookquery;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private Book book;

    private class ViewHolder {
        private TextView title;
        private TextView publisher;
        private TextView author;
    }

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_items, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.publisher = (TextView) view.findViewById(R.id.publisher);
            viewHolder.author = (TextView) view.findViewById(R.id.author);
            view.setTag(viewHolder);
        } else {
            //复用convertView
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        book = getItem(position);
        //设置文本
        viewHolder.title.setText(book.getmTitle());
        viewHolder.publisher.setText(book.getmPublisher());
        viewHolder.author.setText(book.getmAuthor());

        return view;
    }
}
