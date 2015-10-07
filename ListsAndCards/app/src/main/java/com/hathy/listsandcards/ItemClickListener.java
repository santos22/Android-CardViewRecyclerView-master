package com.hathy.listsandcards;

/**
 * Created by santossolorzano on 10/6/15.
 */

import android.view.View;

public interface ItemClickListener {

    void onClick(View view, int position, boolean isLongClick);

}