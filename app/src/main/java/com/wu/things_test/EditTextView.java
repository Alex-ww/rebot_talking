package com.wu.things_test;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Administrator on 2018/10/12.
 */
public class EditTextView extends EditText implements View.OnFocusChangeListener, TextWatcher {

    /* 右边删除图标的引用 */
    private Drawable mClearDrawable;

    /* 输入框是否有焦点 */
    private boolean hasFocus;
    public EditTextView(Context context) {
        this(context,null);
    }

    public EditTextView(Context context, AttributeSet attrs) {
        this(context,attrs,R.attr.editTextStyle);
    }

    public EditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
         /*右边的删除图片*/
        mClearDrawable = getResources().getDrawable(R.drawable.delete);
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        // 删除图标默认隐藏

        setClearIconVisible(false);
        // 设置输入框焦点改变监听
        setOnFocusChangeListener(this);
        // 设置输入框中内容发生改变监听
        addTextChangedListener(this);
    }
    /**
     * 当visible为true时，删除图标显示，当visible为false时，图标隐藏
     * @param visible
     */
    private void setClearIconVisible(boolean visible) {
        Drawable rightDrawable = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], rightDrawable,
                getCompoundDrawables()[3]);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (getCompoundDrawables()[2] != null) {
                    boolean isClear = (event.getX() > (getWidth() - getTotalPaddingRight()))
                            && (event.getX() < (getWidth() - getPaddingRight()));
                    if (isClear) {
                        setText("");
                    }
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当控件的焦点发生改变时会调用该方法。
     * 当控件有焦点并且内容长度不为0则显示clear图标，否则隐藏。
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if(hasFocus){
            setClearIconVisible(getText().length() > 0);
        }else{
            setClearIconVisible(false);
        }
    }

    /**
     * 当控件的有内容改变时调用此方法。
     * 有焦点并且内容长度不为0则显示clear图标，否则隐藏。
     */
    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(hasFocus){
            setClearIconVisible(text.length() > 0);
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub

    }

    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }



}
