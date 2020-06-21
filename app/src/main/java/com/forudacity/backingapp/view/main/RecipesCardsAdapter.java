package com.forudacity.backingapp.view.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.forudacity.backingapp.R;
import com.forudacity.backingapp.databinding.RecipesCardListItemBinding;
import com.forudacity.backingapp.model.Recipes;

import java.util.List;

public class RecipesCardsAdapter extends RecyclerView.Adapter<RecipesCardsAdapter.RecipeViewHolder> {

    public interface CardClickListener{

        void OnCardClickListener(Recipes recipes);
    }
    private CardClickListener mCardClickListener ;

    private List<Recipes> recipes;
    private Context context;

    public RecipesCardsAdapter(List<Recipes> recipes, Context context , CardClickListener mCardClickListener) {
        this.recipes = recipes;
        this.context = context;
        this.mCardClickListener = mCardClickListener ;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipesCardListItemBinding recipesCardListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context) , R.layout.recipes_card_list_item , parent , false);

        return new RecipeViewHolder(recipesCardListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipesCardListItemBinding.setRecipes(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        RecipesCardListItemBinding recipesCardListItemBinding ;
        RecipeViewHolder(@NonNull RecipesCardListItemBinding recipesCardListItemBinding) {
            super(recipesCardListItemBinding.getRoot());
            this.recipesCardListItemBinding = recipesCardListItemBinding ;

            recipesCardListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCardClickListener.OnCardClickListener(recipes.get(getAdapterPosition()));
                }
            });
        }

    }
}
