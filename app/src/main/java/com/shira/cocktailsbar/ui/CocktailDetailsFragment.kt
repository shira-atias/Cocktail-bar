package com.shira.cocktailsbar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.shira.cocktailsbar.R
import com.shira.cocktailsbar.Typewriter
import com.shira.cocktailsbar.action.IsFinishText
import com.shira.cocktailsbar.dataBase.DrinkRepository
import com.shira.cocktailsbar.databinding.FragmentCocktailDetailsBinding
import com.shira.cocktailsbar.model.Drink
import kotlinx.coroutines.Dispatchers



class CocktailDetailsFragment : Fragment() ,IsFinishText{

    private var _binding: FragmentCocktailDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:ViewModel
    private var ingredient = StringBuilder()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = FragmentCocktailDetailsBinding.inflate(inflater,container,false)
        viewModel= ViewModelProvider(this)[ViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvName = binding.tvName
        val ivCocktailD = binding.ivCocktailD
        val layoutDetails = binding.LayoutDetails
        val t = Typewriter(context, this)

        var cocktail:Drink? = null


        val favorite = this.activity?.let { it1 -> DrinkRepository(it1.application,Dispatchers.Main) }

        t.setCharacterDelay(50)
        layoutDetails.addView(t)


        binding.ivBackDetails.setOnClickListener{
            requireActivity().onBackPressed()
        }

        val bag = arguments
        val id = bag?.getString(ID_COCKTAIL_CONST)

        if (id != null){
            viewModel.getDetailCocktail(id).observe(viewLifecycleOwner) {
                if (it != null) {
                    cocktail = it[0]
                   val oneCocktail = cocktail?.idDrink?.let { it1 -> favorite?.getOneCocktail(it1) }
                    if (oneCocktail != null) {
                        binding.ivHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                    }
                }
                tvName.text = cocktail?.strDrink
                Glide.with(this).load(cocktail?.strDrinkThumb).into(ivCocktailD)

                cocktail?.strIngredient1?.let { it1 ->
                    cocktail!!.strMeasure1?.let { it2 ->
                        appendString(it1, it2) }
                }
                cocktail?.strIngredient2?.let { it1 ->
                    cocktail!!.strMeasure2?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient3?.let { it1 ->
                    cocktail!!.strMeasure3?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient4?.let { it1 ->
                    cocktail!!.strMeasure4?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient5?.let { it1 ->
                    cocktail!!.strMeasure5?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient6?.let { it1 ->
                    cocktail!!.strMeasure6?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient7?.let { it1 ->
                    cocktail!!.strMeasure7?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient8?.let { it1 ->
                    cocktail!!.strMeasure8?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient9?.let { it1 ->
                    cocktail!!.strMeasure9?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient10?.let { it1 ->
                    cocktail!!.strMeasure10?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient11?.let { it1 ->
                    cocktail!!.strMeasure11?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient12?.let { it1 ->
                    cocktail!!.strMeasure12?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient13?.let { it1 ->
                    cocktail!!.strMeasure13?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient14?.let { it1 ->
                    cocktail!!.strMeasure14?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                cocktail?.strIngredient15?.let { it1 ->
                    cocktail!!.strMeasure15?.let { it2 ->
                        appendString(it1, it2)
                    }
                }
                t.animateText(
                    getString(R.string.you_need) +
                            "$ingredient " +
                            "${cocktail?.strGlass} \n\n" +
                            getString(R.string.lets_start) +
                            "${cocktail?.strInstructions}"
                )

            }
        }

        binding.ivHeart.setOnClickListener{
            //save or delete from favorite
            val oneCocktailFavorite = cocktail?.idDrink?.let { it1 -> favorite?.getOneCocktail(it1) }
            if (oneCocktailFavorite == null){
                binding.ivHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                val cocktailSave = this.activity?.let { it1 -> DrinkRepository(it1.application,Dispatchers.Main) }
                cocktail?.let { it1 -> cocktailSave?.addToDatabase(it1) }
                Snackbar.make(it,getString(R.string.cocktail_add),Snackbar.LENGTH_LONG).show()

            }else{
                binding.ivHeart.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                val cocktailDelete = this.activity?.let { it1 -> DrinkRepository(it1.application,Dispatchers.Main) }
                cocktail?.let { it1 -> cocktailDelete?.deleteCocktail(it1) }
                Snackbar.make(it,getString(R.string.cocktail_delete),Snackbar.LENGTH_LONG).show()
            }
        }

    }
    private fun appendString(strIngredient:String?, strMeasure: String ){
        if (!strIngredient.isNullOrEmpty()){
            ingredient.append("$strMeasure of $strIngredient \n")
        }
    }

    override fun isTextFinish() {
        binding.scrollView.post{
            binding.scrollView.fullScroll(View.FOCUS_DOWN)
        }

    }




}