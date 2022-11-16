package com.shira.cocktailsbar.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.shira.cocktailsbar.R

import com.shira.cocktailsbar.Typewriter
import com.shira.cocktailsbar.action.IsFinishText
import com.shira.cocktailsbar.adapter.CocktailAdapter
import com.shira.cocktailsbar.databinding.FragmentCocktailListBinding

import com.shira.cocktailsbar.model.Drink
import java.util.*
import kotlin.math.abs


@Suppress("DEPRECATION")
class CocktailListFragment : Fragment() ,IsFinishText{

    private var _binding: FragmentCocktailListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:ViewModel

    private var sliderHandler = Handler()
    private lateinit var viewPager2:ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentCocktailListBinding.inflate(inflater,container,false)
        viewModel= ViewModelProvider(this)[ViewModel::class.java]
        return binding.root
    }

    @SuppressLint("StringFormatMatches")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutList = binding.linearLayoutList
        viewPager2 = binding.viewP2List
        val t = Typewriter(context, this)
        val cocktails= mutableListOf<Drink>()
        t.setCharacterDelay(50)

        binding.ivBack.setOnClickListener{
            requireActivity().onBackPressed()
        }

        var status: String? = null
        val bag = arguments
        if (bag != null) {
            status = bag.getString(STATUS_CONST)
        }

        if (status.equals(LIST_CONST) && status != null) {
            viewModel.getCocktailFromFirebase().observe(viewLifecycleOwner){ drinks ->
                binding.searchView.setQuery("",false)
                if (cocktails.size == 0){
                    //sorted by a..z
                    val cocktailsSorted = drinks.sortedBy { it.strDrink }
                    cocktails.addAll(cocktailsSorted)
                }
                binding.searchView.visibility = View.VISIBLE
                t.animateText(getString(R.string.list_page,drinks.size))
                if (t.parent != null){
                    layoutList.removeView(t)
                }
                layoutList.addView(t)
                configurePage(cocktails)
            }
        }
        if (status.equals(FAVORITE_CONST)&& status != null) {
            viewModel.getAllFavorite().observe(viewLifecycleOwner) { drinks ->
                if (cocktails.size == 0) {
                    //sorted by a..z
                    val cocktailsSorted = drinks.sortedBy { it.strDrink}
                    cocktails.addAll(cocktailsSorted)
                }
                if (cocktails.size == 0){
                    t.animateText(getString(R.string.favorite_empty))
                    binding.linearLayoutList.layoutParams.height = 300
                }else{
                    t.animateText(getString(R.string.favorite_not_empty,cocktails.size))
                }
                if (t.parent != null){
                    layoutList.removeView(t)
                }
                layoutList.addView(t)
                configurePage(cocktails)
            }
        }
        if (status.equals(RANDOM_CONST) && status != null) {
            viewModel.getRandomCocktail().observe(viewLifecycleOwner) { drinks ->
                configurePage(drinks)
                t.animateText(getString(R.string.random_page))

                if (t.parent != null){
                    layoutList.removeView(t)
                }
                layoutList.addView(t)
                binding.ivLikeDown.visibility = View.VISIBLE
                binding.ivLikeDown.setOnClickListener{
                    viewModel.getRandomCocktail().observe(viewLifecycleOwner){
                        configurePage(it)
                    }
                }
                binding.ivLikeUp.visibility = View.VISIBLE
                binding.ivLikeUp.setOnClickListener{
                    val arg = Bundle()
                    arg.putString(ID_COCKTAIL_CONST, drinks[0].idDrink)
                    Navigation.findNavController(it).navigate(R.id.action_cocktailListFragment_to_cocktailDetailsFragment,arg)
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                configurePage(cocktails)
              return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val newCocktail= mutableListOf<Drink>()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    cocktails.forEach {
                        if ( it.strDrink.lowercase(Locale.getDefault()).contains(searchText)||
                            it.strIngredient1?.lowercase(Locale.getDefault())!!.contains(searchText) ||
                            it.strIngredient2?.lowercase(Locale.getDefault())!!.contains(searchText) ||
                            it.strIngredient3?.lowercase(Locale.getDefault())!!.contains(searchText) ||
                            it.strIngredient4?.lowercase(Locale.getDefault())!!.contains(searchText) ||
                            it.strIngredient5?.lowercase(Locale.getDefault())!!.contains(searchText) ||
                            it.strIngredient6?.lowercase(Locale.getDefault())!!.contains(searchText) ||
                            it.strIngredient7?.lowercase(Locale.getDefault())!!.contains(searchText) ){
                            newCocktail.add(it)
                        }
                    }
                    configurePage(newCocktail)
                    t.animateText(getString(R.string.list_page,newCocktail.size))
                }else{
                    configurePage(cocktails)
                    t.animateText(getString(R.string.list_page,cocktails.size))
                }

                return false
            }
        })

    }

    var sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    fun configurePage(cocktails:MutableList<Drink>){
        viewPager2.adapter = CocktailAdapter(cocktails)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 5
        viewPager2.getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val comp =  CompositePageTransformer()

        comp.addTransformer(MarginPageTransformer(20))
        comp.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.55f + r * 0.45f
        }
        viewPager2.setPageTransformer(comp)
        //The list moves after 3 seconds
        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable,2000)
            }
        })

    }

    override fun isTextFinish() {
        print("is finish")
    }
}