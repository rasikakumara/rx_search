package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.database.Birds
import com.example.myapplication.database.BirdsDataBase
import com.example.myapplication.database.BirdsUtil
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_birds.*

abstract class BaseSearchActivity:AppCompatActivity() {
    protected lateinit var searchEngine: BirdsSearchEngine
    private val BirdsAdapter = BirdsAdapter()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birds)

        list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        list.adapter = BirdsAdapter

        searchEngine = BirdsSearchEngine(this@BaseSearchActivity)

        val initialLoadDisposable = loadInitialData(this@BaseSearchActivity).subscribe()
        compositeDisposable.add(initialLoadDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        BirdsDataBase.destroyInstance()
        compositeDisposable.clear()
    }

    protected fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    protected fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    protected fun showResult(result: List<Birds>) {
        if (result.isEmpty()) {
            Toast.makeText(this, R.string.nothing_found, Toast.LENGTH_SHORT).show()
        }
        BirdsAdapter.birds = result
    }

    private fun loadInitialData(context: Context): Flowable<List<Long>> {
        return Maybe.fromAction<List<Long>> {

            val database = BirdsDataBase.getInstance(context = context).birdsDao()

            val BirdsList = arrayListOf<Birds>()
            for (bird in BirdsUtil.BIRDS) {
                BirdsList.add(Birds(name = bird))
            }
            database.insertAll(BirdsList)

        }.toFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_LONG).show()
            }
            .doOnError {
                Toast.makeText(context, context.getString(R.string.error_inserting_birds), Toast.LENGTH_LONG).show()
            }
    }
}
