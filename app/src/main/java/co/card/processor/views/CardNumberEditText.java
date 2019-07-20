package co.card.processor.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * This calls will handle the expiry date format of the card number
 */
public class CardNumberEditText extends NumericEditText {

    public CardNumberEditText(Context context) {
        super(context);
    }

    public CardNumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardNumberEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnTextChangeListener() {
        addTextChangedListener(mExpiryDateListener);
    }

    private TextWatcher mExpiryDateListener = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable editable) {
            if (isUserTyping &&
                    editable.length() > 0 &&
                    (editable.length() % 5) == 0) {
                editable.insert(editable.length() - 1, String.valueOf(" "));
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s,
                                      int start,
                                      int count,
                                      int after) {
            if (count < after) {
                isUserTyping = true;
            } else {
                isUserTyping = false;
            }
        }

        @Override
        public void onTextChanged(CharSequence s,
                                  int start,
                                  int before,
                                  int count) {
        }
    };

}
