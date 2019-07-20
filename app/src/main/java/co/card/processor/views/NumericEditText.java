package co.card.processor.views;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.util.AttributeSet;

import co.card.processor.presenter.MainPresenterImpl;

/**
 * This class will handle the number limit validation on the Edit Text
 */
public abstract class NumericEditText extends AppCompatEditText {

    protected int max_value = Integer.MAX_VALUE;
    protected int min_value = Integer.MIN_VALUE;

    protected boolean isUserTyping = false;
    protected int mMaxLengthOfEditText;

    protected MainPresenterImpl mMainPresenterImpl = null;

    public NumericEditText(Context context) {
        super(context);
    }

    public NumericEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumericEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // checks whether the limits are set and corrects them if not within limits
    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {

        if (max_value != Integer.MAX_VALUE) {
            try {
                if (Integer.parseInt(this.getText().toString()) > max_value) {
                    // change value and keep cursor position
                    int selection = this.getSelectionStart();
                    this.setText(String.valueOf(max_value));
                    if (selection >= this.getText().toString().length()) {
                        selection = this.getText().toString().length();
                    }
                    this.setSelection(selection);
                }
            } catch (NumberFormatException exception) {
                super.onTextChanged(text, start, before, after);
            }
        }
        if (min_value != Integer.MIN_VALUE) {
            try {
                if (Integer.parseInt(this.getText().toString()) < min_value) {
                    // change value and keep cursor position
                    int selection = this.getSelectionStart();
                    this.setText(String.valueOf(min_value));
                    if (selection >= this.getText().toString().length()) {
                        selection = this.getText().toString().length();
                    }
                    this.setSelection(selection);
                }
            } catch (NumberFormatException exception) {
                super.onTextChanged(text, start, before, after);
            }
        }
        super.onTextChanged(text, start, before, after);

        if (mMainPresenterImpl != null) {
            mMainPresenterImpl.updateCardWithIcon(text.toString());
        }
    }

    public void setMainViewPresenter(MainPresenterImpl presenter) {
        mMainPresenterImpl = presenter;
    }

    // set the max number of digits the user can enter
    public void setMaxLength(int length) {
        mMaxLengthOfEditText = length;
        setFilter(length);
    }

    private void setFilter(int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        this.setFilters(FilterArray);
    }

    public int getMaxValue() {
        return mMaxLengthOfEditText;
    }

}
