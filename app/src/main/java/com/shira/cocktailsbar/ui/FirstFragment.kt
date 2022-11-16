package com.shira.cocktailsbar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.shira.cocktailsbar.R
import com.shira.cocktailsbar.Typewriter
import com.shira.cocktailsbar.action.IsFinishText
import com.shira.cocktailsbar.databinding.FragmentFirstBinding

const val STATUS_CONST = "status"
const val LIST_CONST = "list"
const val FAVORITE_CONST = "favorite"
const val RANDOM_CONST = "random"
const val ID_COCKTAIL_CONST = "idCocktail"

class FirstFragment : Fragment(), IsFinishText {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutFirst = binding.linearLayoutFirst
        val arg = Bundle()
        val t = Typewriter(context, this)

        t.setCharacterDelay(50)
        t.animateText(getString(R.string.first_page_hello))
        layoutFirst.addView(t)

        binding.btnRandomCocktail.visibility = View.GONE
        binding.btnListCocktail.visibility = View.GONE
        binding.btnFavorite.visibility = View.GONE


        binding.btnRandomCocktail.setOnClickListener {
            arg.putString(STATUS_CONST, RANDOM_CONST)
            findNavController().navigate(R.id.action_FirstFragment_to_cocktailListFragment,arg)
        }
        binding.btnListCocktail.setOnClickListener{
            arg.putString(STATUS_CONST, LIST_CONST)
            findNavController().navigate(R.id.action_FirstFragment_to_cocktailListFragment,arg)
        }
        binding.btnFavorite.setOnClickListener{
            arg.putString(STATUS_CONST, FAVORITE_CONST)
            findNavController().navigate(R.id.action_FirstFragment_to_cocktailListFragment,arg)
        }
    }


    override fun isTextFinish() {
        binding.btnRandomCocktail.visibility = View.VISIBLE
        binding.btnListCocktail.visibility = View.VISIBLE
        binding.btnFavorite.visibility = View.VISIBLE

    }
}