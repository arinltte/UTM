/*
UCCD 3223 Mobile Applications Development
June 2024 Trimester

Chen Jin Shen	2202076
Chin Whye Ting	2200559
Ong Jing Yang	2200327
Tan Zong Ting	2302731
*/

package com.example.groupassignment;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;
    private final int textColor;
    private final float textSize;
    private final Typeface typeface;

    public CustomAdapter(Context context, ArrayList<String> values, int textColor, float textSize, Typeface typeface) {
        super(context, android.R.layout.simple_list_item_1, values);
        this.context = context;
        this.values = values;
        this.textColor = textColor;
        this.textSize = textSize;
        this.typeface = typeface;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);

        textView.setText(values.get(position));
        textView.setTextColor(textColor);

        // Set padding (in pixels)
        int padding = 32;
        textView.setPadding(padding, padding, padding, padding);

        // Set text alignment
        textView.setGravity(Gravity.CENTER);

        // Set text size
        textView.setTextSize(textSize);

        // Set font to urbanist
        textView.setTypeface(typeface);

        return rowView;
    }
}
