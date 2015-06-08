package com.example.admin.someapp;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by ADMIN on 8.6.2015..
 */
public class PreCachingLayoutManager extends LinearLayoutManager {

    private static final int DEFAULT_EXTRA_LAYOUT_SPACE  = 600;
    private Context mContext;
    private int extraLayoutSpace = -1;

    public PreCachingLayoutManager(Context context) {
        super(context);
        this.mContext = context;
    }

    public PreCachingLayoutManager(Context context, int extraLayoutSpace) {
        super(context);
        this.mContext = context;
        this.extraLayoutSpace = extraLayoutSpace;
    }

    public PreCachingLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.mContext = context;
    }

    public void setExtraLayoutSpace(int extraLayoutSpace) {
        this.extraLayoutSpace = extraLayoutSpace;
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        if(extraLayoutSpace > 0){
            return extraLayoutSpace;
        } else{
            return DEFAULT_EXTRA_LAYOUT_SPACE;
        }
    }
}
