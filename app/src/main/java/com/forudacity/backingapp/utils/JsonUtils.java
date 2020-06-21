package com.forudacity.backingapp.utils;

import com.forudacity.backingapp.model.Recipes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {


    public static List<Recipes> parseRecipesJsonToPojo(String json) {


//            JSONArray jsonArray = new JSONArray(json);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                Recipes recipes = new Recipes();
//                JSONObject object = jsonArray.getJSONObject(i);
//
//                recipes.setId(object.getInt("id"));
//                recipes.setName(object.getString("name"));
//
//                JSONArray ingredients = object.getJSONArray("ingredients");
//                for (int j = 0; j <ingredients.length(); j++) {
//                    jsonArray
//                }

        Type listType = new TypeToken<List<Recipes>>() {}.getType();
        return new Gson().fromJson(json, listType);

    }

}

