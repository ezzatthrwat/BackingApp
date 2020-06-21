package com.forudacity.backingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.forudacity.backingapp.R;
import com.forudacity.backingapp.model.Ingredient;
import com.forudacity.backingapp.model.StepsAndIngredient;

import java.util.List;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Ingredient> ingredientList ;

    ListRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        ingredientList = StepsAndIngredient.getInstance().getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredientList==null)return 0;
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);

        Ingredient ingredient = ingredientList.get(position);
        String ingredientTextItem = ingredient.getQuantity()+" "+ingredient.getMeasure()+" "+ingredient.getIngredient();
        views.setTextViewText(R.id.IngredientTextView, ingredientTextItem);

        Intent fillInIntent = new Intent();
        views.setOnClickFillInIntent(R.id.IngredientTextView, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
