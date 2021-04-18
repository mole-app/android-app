package com.mole.android.mole.test

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.mole.android.mole.R
import com.mole.android.mole.ui.actionbar.MoleActionBar


class TestScreenFragment : Fragment() {

    private var toolbar: MoleActionBar? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.test_screen, container, false)
        val circleImageButton: AppCompatImageButton = view.findViewById(R.id.test_screen_circle_button_disable)
        circleImageButton.isEnabled = false

        toolbar = view.findViewById(R.id.moleToolbarWithText)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
//
//        inflater.inflate(R.menu.search_menu, menu)
//
//        val searchItem: MenuItem? = menu.findItem(R.id.actionSearch)
//        val searchManager = getSystemService(requireActivity(), Context.SEARCH_SERVICE) as SearchManager
//        val searchView: SearchView? = searchItem?.actionView as SearchView?
//
//        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//
        // First clear current all the menu items
        menu.clear()

        // Add the new menu items
        inflater.inflate(R.menu.profile_menu, menu)
        toolbar?.bindMenu()
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.profile_menu, menu)
//        val searchViewItem = menu.findItem(R.id.actionSearch)
//        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                searchView.clearFocus()
//                /*   if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }*/
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                adapter?.filter?.filter(newText)
//                return false
//            }
//        })
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_menu_item -> {
                Toast.makeText(this.context, "edit_menu_item", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
