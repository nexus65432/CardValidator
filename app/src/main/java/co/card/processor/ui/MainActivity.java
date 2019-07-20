package co.card.processor.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import co.card.processor.R;
import co.card.processor.presenter.MainPresenterImpl;
import co.card.processor.listener.MainView;
import co.card.processor.model.CardType;
import co.card.processor.views.CardNumberEditText;
import co.card.processor.views.CvvEditText;
import co.card.processor.views.ExpiryEditText;

/**
 * Main View to present the UI for user interaction
 */
public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private static final int DEFAULT_CARD_NUMBER_LENGTH = 19;
    private static final int DEFAULT_CARD_CVV_LENGTH = 3;
    private static final int AMEX_CARD_NUMBER_LENGTH = 18;
    private static final int AMEX_CARD_CVV_LENGTH = 4;
    private static final int DEFAULT_CARD_EXPIRY_LENGTH = 5;

    private MainPresenterImpl mMainPresenterImpl;

    private TextView mCardStatus;
    private ImageView mCardTypeImage;
    private CardNumberEditText mCardNumberInputType;
    private ExpiryEditText mCardExpiry;
    private CvvEditText mCardCvv;
    private Button mSearchButton;
    private CardType mCardType = CardType.UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCardStatus = findViewById(R.id.card_status);
        mCardTypeImage = findViewById(R.id.card_type_img);

        mCardNumberInputType = findViewById(R.id.card_number_field);
        mCardExpiry = findViewById(R.id.card_expiry_input);
        mCardCvv = findViewById(R.id.card_cvv_input);

        mCardNumberInputType.setOnTextChangeListener();
        mCardExpiry.setOnTextChangeListener();
        mCardCvv.setOnTextChangeListener();

        mSearchButton = findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(this);

        mMainPresenterImpl = new MainPresenterImpl(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainPresenterImpl.onAttach();
        mCardNumberInputType.setMainViewPresenter(mMainPresenterImpl);
        // Initialize
        setCardType(mCardType);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenterImpl.prepareToExit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenterImpl.onDetach();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.search_button:
                mMainPresenterImpl.checkCardCheckSum(mCardNumberInputType.getText().toString());
                break;
        }
    }

    @Override
    public Context getViewContext() {
        return MainActivity.this;
    }

    @Override
    public void setCardType(CardType type) {
        mCardType = type;
        if (mCardType == CardType.AMERICAN_EXPRESS) {
            mCardNumberInputType.setMaxLength(AMEX_CARD_NUMBER_LENGTH);
            mCardCvv.setMaxLength(AMEX_CARD_CVV_LENGTH);
        } else {
            mCardNumberInputType.setMaxLength(DEFAULT_CARD_NUMBER_LENGTH);
            mCardCvv.setMaxLength(DEFAULT_CARD_CVV_LENGTH);
        }
        mCardExpiry.setMaxLength(DEFAULT_CARD_EXPIRY_LENGTH);
    }

    @Override
    public void updateValidCardDrawable(int resourceId) {
        mCardTypeImage.setImageResource(resourceId);
    }

    @Override
    public void updateUnknownCardDrawable(int resourceId) {
        mCardTypeImage.setImageResource(resourceId);
    }

    @Override
    public void onError(String message) {
        mCardStatus.setText(message);
        mCardStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(String message) {
        mCardStatus.setText(message);
        mCardStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideKeyboard() {
        Log.d(TAG, " hideKeyboard ");
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
