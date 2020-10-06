package com.byjus.headlines.headlines.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.byjus.headlines.R
import com.byjus.headlines.core.constants.Constant
import com.byjus.headlines.headlines.adapters.HeadlineRecyclerViewAdapter
import com.byjus.headlines.headlines.dao.HeadLineDatabase
import com.byjus.headlines.headlines.models.request.HeadLineRequest
import com.byjus.headlines.headlines.models.response.ArticlesItem
import com.byjus.headlines.headlines.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HeadlinesFragment : DaggerFragment() {

    companion object {
        fun newInstance() = HeadlinesFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var mArticlesItem = ArrayList<ArticlesItem>()
    lateinit var mHeadlineRecyclerViewAdapter: HeadlineRecyclerViewAdapter

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, mViewModelFactory).get(MainViewModel::class.java)
        viewModel.downloadHeadLines(HeadLineRequest(Constant.mCountry, Constant.mAPIKey))
        viewModel.getHeadLineList().observe(viewLifecycleOwner, Observer {
            when (it.status != null) {
                true -> {
                    noAvail.visibility = View.GONE
                    rvHeadLines.visibility = View.VISIBLE
                    mArticlesItem.clear()
                    mArticlesItem.addAll(it.articles!!)
                    setRecycleView()
                }
                false -> {
                    getCacheMemory()
                }
            }
        })
    }

    private fun setRecycleView() {
        when (::mHeadlineRecyclerViewAdapter.isInitialized) {
            false -> {
                mHeadlineRecyclerViewAdapter = HeadlineRecyclerViewAdapter(mArticlesItem)
                rvHeadLines.layoutManager = LinearLayoutManager(context)
                rvHeadLines.adapter = mHeadlineRecyclerViewAdapter
            }
        }
        mHeadlineRecyclerViewAdapter.notifyDataSetChanged()
        when (mArticlesItem.isEmpty()) {
            true -> {
                rvHeadLines.visibility = View.GONE
                noAvail.visibility = View.VISIBLE
            }
            else -> {
                rvHeadLines.visibility = View.VISIBLE
                noAvail.visibility = View.GONE
            }
        }
    }

    private fun getCacheMemory() {
        GlobalScope.launch {
            val daoHeadLineDatabase = HeadLineDatabase.getDatabase(context!!)?.DAOHeadline()
            mArticlesItem.clear()
            mArticlesItem.addAll(daoHeadLineDatabase?.getALLCache()!!)
            GlobalScope.launch(Dispatchers.Main) {
                setRecycleView()
            }
        }
    }
}