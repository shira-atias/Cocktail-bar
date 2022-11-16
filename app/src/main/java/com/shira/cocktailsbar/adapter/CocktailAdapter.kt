package com.shira.cocktailsbar.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.shira.cocktailsbar.R
import com.shira.cocktailsbar.model.Drink

class CocktailAdapter(private var cocktailList:MutableList<Drink>):RecyclerView.Adapter<CocktailAdapter.ViewHolder>() {

    class ViewHolder(val view : View):RecyclerView.ViewHolder(view){
        val tvDrink:TextView = view.findViewById(R.id.tvDrink)
        val image:RoundedImageView = view.findViewById(R.id.imageSlide1)
        val tvIng:TextView = view.findViewById(R.id.tvIng)
        val tvNumOfItem:TextView = view.findViewById(R.id.tvNumOfItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cocktail_item,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cocktail = cocktailList[position]

        holder.tvDrink.text = cocktail.strDrink
        holder.tvIng.text = "${cocktail.strIngredient1}, ${cocktail.strIngredient2}, ${cocktail.strIngredient3}"
        holder.tvNumOfItem.text = "${position + 1}"

        Glide.with(holder.view.context).load(cocktail.strDrinkThumb).into(holder.image)

        val arg = Bundle()
        arg.putString("idCocktail",cocktail.idDrink)
        holder.itemView.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_cocktailListFragment_to_cocktailDetailsFragment,arg)

        }
    }

    override fun getItemCount() = cocktailList.size

}