package com.kkpfg.fundtrading.view.adapters


import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.kkpfg.fundtrading.data.api.models.BannerItem
import com.kkpfg.fundtrading.databinding.BannerListItemBinding
import com.kkpfg.fundtrading.utils.imageloader.ImageLoader
import com.kkpfg.fundtrading.view.activities.WebViewActivity


class BannerViewHolder(private val binding: BannerListItemBinding): RecyclerView.ViewHolder(binding.root) {

    companion object{
        fun create(context: Context): BannerViewHolder {
            val binding = BannerListItemBinding.inflate(LayoutInflater.from(context))
            return BannerViewHolder(binding)
        }
    }

    private var onClickFundItem: OnClickFundItem? = null

    fun setModel(banner: BannerItem){
        banner.image?.let{
            ImageLoader.loadImage(binding.root.context,  binding.image, it)
        }

        binding.title.text = banner.title
        binding.shortDesc.text = banner.shortDescription

        binding.root.setOnClickListener{

            banner.url?.let{
                val intent = WebViewActivity.createIntent(binding.root.context, banner.url)
                binding.root.context.startActivity(intent)
            }

//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(banner.url)   )
//            binding.root.context.startActivity(intent)

        }

    }

}