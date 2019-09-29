package com.xian.scooter.view.searchview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.manager.ActivityManager;
import com.xian.scooter.utils.KeybordUtils;
import com.xian.scooter.utils.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * The main lib actor this is a custom FrameLayout
 * wrapper with an EditText with a simple interface to perform the search
 * it collects the user input and pass to an Activity or a Fragment or where you need
 *
 * @author Leonardo Rossetto
 */
public class FakeSearchView extends FrameLayout implements TextWatcher,
        TextView.OnEditorActionListener {

    private EditText wrappedEditText;
    private OnSearchListener searchListener;
    private RelativeLayout rl_cancel;

    public FakeSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FakeSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FakeSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public FakeSearchView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Set the search listener to be used on this search
     *
     * @param searchListener the search listener to be used on this search
     * @see OnSearchListener
     */
    public void setOnSearchListener(OnSearchListener searchListener) {
        this.searchListener = searchListener;
    }

    /**
     * Sets the search text
     *
     * @param searchText the text to set on the search
     * @see #setSearchText(int)
     */
    public void setSearchText(CharSequence searchText) {
        wrappedEditText.setText(searchText);
    }

    /**
     * Sets the search text using a resource
     *
     * @param searchTextRes the resource to set the text
     * @see #setSearchText(CharSequence)
     */
    public void setSearchText(int searchTextRes) {
        wrappedEditText.setText(searchTextRes);
    }

    /**
     * @return the current text on the search
     */
    public CharSequence getSearchText() {
        return wrappedEditText.getText();
    }

    /**
     * Set the search placeholder (hint)
     *
     * @param placeholder the placeholder
     * @see #setSearchPlaceholder(int)
     */
    public void setSearchPlaceholder(CharSequence placeholder) {
        wrappedEditText.setHint(placeholder);
    }

    /**
     * Set the search placeholder (hint)
     *
     * @param placeholderRes the placeholder
     * @see #setSearchPlaceholder(CharSequence)
     */
    public void setSearchPlaceholder(int placeholderRes) {
        wrappedEditText.setHint(placeholderRes);
    }

    /**
     * Inflate the layout to this FrameLayout wrapper
     *
     * @param context for inflate views
     */
    protected void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_search_view, this, true);
        wrappedEditText = findViewById(R.id.et_search);
        rl_cancel = findViewById(R.id.rl_cancel);
        wrappedEditText.addTextChangedListener(this);
        wrappedEditText.setOnEditorActionListener(this);
        wrappedEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                KeybordUtils.closeKeybord(wrappedEditText,context);
                if (StringUtils.isBlank(wrappedEditText.getText().toString())) {
                    ToastUtils.showShort("请输入要搜索的内容");
                    return true;
                }
                if (searchListener != null) {
                    searchListener.onSearch(FakeSearchView.this, wrappedEditText.getText().toString());
                }
                return true;
            }
            return false;
        });

        rl_cancel.setOnClickListener(v -> ActivityManager.finishUpActivity("LianXiRenActivity"));
    }


    @Override
    public void beforeTextChanged(CharSequence constraint, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence constraint, int start, int count, int after) {
//        if (searchListener != null) {
//            searchListener.onSearch(this, constraint);
//            return;
//        }
        Log.w(getClass().getName(), "SearchListener == null");
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (searchListener != null) {
            searchListener.onSearchHint(this, textView.getText());
            return true;
        }
        Log.w(getClass().getName(), "SearchListener == null");
        return false;
    }

    /**
     * This interface is an custom method to wrapp the
     * TextWatcher implementation and provide the search constraint
     *
     * @author Leonardo Rossetto
     */
    public interface OnSearchListener {

        /**
         * This method is called every time the EditText change it content
         *
         * @param fakeSearchView the searchview
         * @param constraint     the current input data
         */
        void onSearch(FakeSearchView fakeSearchView, CharSequence constraint);

        /**
         * This method is called when the user press the search button on the keyboard
         *
         * @param fakeSearchView the searchview
         * @param constraint     the current input data
         */
        void onSearchHint(FakeSearchView fakeSearchView, CharSequence constraint);

    }

}
