package com.novraditya.recipee.main.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.novraditya.recipee.R
import com.novraditya.recipee.main.model.Recipe
import com.novraditya.recipee.utils.handleNotifyDataSetChanged
import kotlinx.android.synthetic.main.recipe_item.view.*
import org.jetbrains.anko.layoutInflater

/**
 * Created by elsennovraditya on 12/3/16.
 */
class MainAdapter(var recipeList: MutableList<Recipe?>?) : RecyclerView.Adapter<MainViewHolder>() {

    val loadMoreType: Int = 0
    val itemType: Int = 1

    override fun onBindViewHolder(holder: MainViewHolder?, position: Int) {
        if (holder != null) {
            when (position) {
                RecyclerView.NO_POSITION -> return
                else -> {
                    val recipe: Recipe? = recipeList?.get(position)
                    if (recipe is Recipe) showItem(holder, recipe)
                }
            }
        }
    }

    private fun showItem(holder: MainViewHolder, recipe: Recipe) {
        holder.itemView.recipeName.text = recipe.name
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainViewHolder? {
        val inflater: LayoutInflater? = parent?.context?.layoutInflater ?: return null
        val view: View?

        when (viewType) {
            loadMoreType -> view = inflater?.inflate(R.layout.load_more_item, parent, false)
            else -> view = inflater?.inflate(R.layout.recipe_item, parent, false)
        }

        return MainViewHolder(view, recipeList)
    }

    override fun getItemViewType(position: Int): Int {
        val recipe: Recipe? = recipeList?.get(position)
        return if (recipe != null) itemType else loadMoreType
    }

    override fun getItemCount(): Int {
        return recipeList?.size ?: 0
    }

    fun addRecipes(recipeList: List<Recipe>) {
        this.recipeList?.addAll(recipeList)
        notifyDataSetChanged()
    }

    fun showLoadMore() {
        recipeList?.add(null)
        handleNotifyDataSetChanged()
    }

    fun hideLoadMore() {
        recipeList?.remove(null)
        handleNotifyDataSetChanged()
    }

}
