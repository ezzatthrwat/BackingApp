package com.forudacity.backingapp.model;

import java.util.List;

public class StepsAndIngredient {

    private static StepsAndIngredient stepsAndIngredient ;
    private static List<Ingredient> ingredients ;
    private static List<Step> steps ;

    private StepsAndIngredient() {
    }

    public static StepsAndIngredient getInstance(){
        if (stepsAndIngredient == null){
            stepsAndIngredient = new StepsAndIngredient();
        }
        return stepsAndIngredient;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public  void setIngredients(List<Ingredient> ingredients) {
        StepsAndIngredient.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        StepsAndIngredient.steps = steps;
    }

    public int getStepsSize(){
        return steps.size();
    }
}
