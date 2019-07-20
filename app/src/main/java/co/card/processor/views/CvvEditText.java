package co.card.processor.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import co.card.processor.R;

/**
 * This calls will handle the Cvv number of the card number
 */
public class CvvEditText extends NumericEditText {

    private Context mContext;

    public CvvEditText(Context context) {
        super(context);
        mContext = context;
    }

    public CvvEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CvvEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setOnTextChangeListener() {
        addTextChangedListener(mCvvListener);
    }

    private TextWatcher mCvvListener = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() >= 1 && editable.length() < getMaxValue()) {
                setError(String.format(mContext.getString(R.string.cvv_number_hint), getMaxValue()));
            } else {
                setError(null);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s,
                                      int start,
                                      int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s,
                                  int start,
                                  int before,
                                  int count) {
        }
    };

}
