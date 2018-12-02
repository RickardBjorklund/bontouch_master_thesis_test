package com.example.bontouch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class AutoCompleteWordAdapter extends ArrayAdapter<WordItem> {
    private List<WordItem> wordListFull;

    public AutoCompleteWordAdapter(@NonNull Context context, @NonNull List<WordItem> wordList) {
        super(context, 0, wordList);
        wordListFull = new ArrayList<>(wordList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return wordFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.word_autocomplete_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        WordItem wordItem = getItem(position);

        if (wordItem != null) {
            textViewName.setText(wordItem.getWord());
        }

        return convertView;
    }

    private Filter wordFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<WordItem> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(wordListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (WordItem item : wordListFull) {
                    if (item.getWord().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            if (suggestions.size() > 9) {
                results.values = suggestions.subList(0, 9);
                results.count = 10;
            } else {
                results.values = suggestions;
                results.count = suggestions.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((WordItem) resultValue).getWord();
        }
    };
}