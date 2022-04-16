package com.example.cryptoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun createIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
    private lateinit var binding: ActivityCoinDetailBinding
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[CoinViewModel::class.java]
        if (fromSymbol != null) {
            viewModel.getDetailedInfo(fromSymbol).observe(this, {
                binding.apply {
                    tvPrice.text = it.price.toString()
                    tvMinPerDayPrice.text = it.lowday.toString()
                    tvMaxPerDayPrice.text = it.highday.toString()
                    tvStockExchangeName.text = it.lastmarket
                    tvCoinInfoUpdatedAt.text = it.getFormattedTime()
                    tvFromSymbol.text = it.fromsymbol
                    tvToSymbol.text = it.tosymbol
                    Picasso.get().load(it.getFullImageUrl()).into(this.ivLogoCoin)
                }
            })
        }
    }
}