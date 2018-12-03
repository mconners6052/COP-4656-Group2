package com.fitness.sm.smartmuscle.helpers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
///THIS CLASS IS A WORKAROUND FOR A BUG WITH THE EXPANDING RECYCLER VIEW
public class LinearLayoutWrapper extends LinearLayoutManager {
    public LinearLayoutWrapper(Context context) {
        super(context);
    }

    public LinearLayoutWrapper(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
