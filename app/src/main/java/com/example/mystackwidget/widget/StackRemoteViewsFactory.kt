package com.example.mystackwidget.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.example.mystackwidget.R

internal class StackRemoteViewsFactory(private val mContext:Context):RemoteViewsService.RemoteViewsFactory {
    private val mWidgetItems= ArrayList<Bitmap>()
    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv=RemoteViews(mContext.packageName,R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView,mWidgetItems[position])

        val extras= bundleOf(
            ImageBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent= Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView,fillInIntent)
        return rv

    }

    override fun getLoadingView(): RemoteViews ?=null

    override fun getViewTypeCount(): Int=1

    override fun getItemId(i: Int): Long =1

    override fun hasStableIds(): Boolean =false


    override fun onDataSetChanged() {
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.sandro_pole))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.tanjung_menangis))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.pulau_kenawa))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.pulau_moyo))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.pantai_jempol))
    }

}