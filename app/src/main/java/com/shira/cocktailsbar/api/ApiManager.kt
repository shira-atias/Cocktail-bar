package com.shira.cocktailsbar.api


import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.shira.cocktailsbar.model.Drink
import com.shira.cocktailsbar.model.DrinksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager() {

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://www.thecocktaildb.com").build()
        .create(Service::class.java)

    fun getRandomCocktail(randomCocktailLiveData:MutableLiveData<MutableList<Drink>>){
        val retrofitData = retrofitBuilder.getRandomCocktail()
        retrofitData.enqueue(object :Callback<DrinksResponse>{
            override fun onResponse(call: Call<DrinksResponse>, response: Response<DrinksResponse>) {
                val drinksResponse = response.body()
                if (drinksResponse != null){
                    randomCocktailLiveData.postValue(drinksResponse.drinks as MutableList<Drink>)
                }else{
                    val database = FirebaseDatabase.getInstance().getReference("drinks")
                    database.get().addOnSuccessListener {
                        val cocktailList = mutableListOf<Drink>()
                        val index = (0..it.childrenCount).random().toInt()
                        for((count, drink) in it.children.withIndex()){
                            if (count == index){
                                val cocktail = drink.getValue(Drink::class.java)
                                if (cocktail != null)
                                    cocktailList.add(cocktail)
                            }
                        }
                        randomCocktailLiveData.postValue(cocktailList)

                    }
                }
            }
            override fun onFailure(call: Call<DrinksResponse>, t: Throwable) {
                val database = FirebaseDatabase.getInstance().getReference("drinks")
                    database.get().addOnSuccessListener {
                        val cocktailList = mutableListOf<Drink>()
                        val index = (0..it.childrenCount).random().toInt()
                        for((count, drink) in it.children.withIndex()){
                            if (count == index){
                                val cocktail = drink.getValue(Drink::class.java)
                                if (cocktail != null)
                                    cocktailList.add(cocktail)
                            }
                        }
                        randomCocktailLiveData.postValue(cocktailList)
                }
            }
        })
    }

    fun getCocktailID(id:String,cocktailIdLiveData:MutableLiveData<MutableList<Drink>>){
        val retrofitData = retrofitBuilder.getCocktailID(id)
        retrofitData.enqueue(object :Callback<DrinksResponse>{
            override fun onResponse(call: Call<DrinksResponse>, response: Response<DrinksResponse>) {
                val cocktailDetail = response.body()
                if (cocktailDetail != null){
                    cocktailIdLiveData.postValue(cocktailDetail.drinks as MutableList<Drink>?)
                }else{
                    val database = FirebaseDatabase.getInstance().getReference("drinks")
                    val dbId = database.child(id)
                    dbId.get().addOnSuccessListener {
                        val cocktail = it.getValue(Drink::class.java)
                        val cocktailList = mutableListOf<Drink>()
                        if (cocktail != null){
                            cocktailList.add(cocktail)
                            cocktailIdLiveData.postValue(cocktailList)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<DrinksResponse>, t: Throwable) {
                val database = FirebaseDatabase.getInstance().getReference("drinks")
                val db = database.child(id)
                db.get().addOnSuccessListener {
                    val cocktail = it.getValue(Drink::class.java)
                    val cocktailList = mutableListOf<Drink>()
                    if (cocktail != null)
                        cocktailList.add(cocktail)
                    cocktailIdLiveData.postValue(cocktailList)
                }
            }
        })
    }


}