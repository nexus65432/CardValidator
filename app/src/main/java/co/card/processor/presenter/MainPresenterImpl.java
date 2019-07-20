package co.card.processor.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;

import co.card.processor.R;
import co.card.processor.listener.MainPresenter;
import co.card.processor.listener.MainView;
import co.card.processor.model.CardType;
import io.reactivex.disposables.CompositeDisposable;

public class MainPresenterImpl implements MainPresenter {

    private static final String TAG = "MainPresenterImpl";

    private CompositeDisposable mCompositeDisposable = null;
    private MainView mMainView;

    @VisibleForTesting
    public MainPresenterImpl() {
    }

    public MainPresenterImpl(@NonNull MainView view) {
        this.mMainView = view;
    }

    @Override
    public void onAttach() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void prepareToExit() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void onDetach() {
        mCompositeDisposable = null;
        mMainView = null;
    }

    private Context getLocalContext() {
        return mMainView.getViewContext();
    }

    @VisibleForTesting
    public boolean isValidLuhn(@NonNull String inputNumber) {

        if (TextUtils.isEmpty(inputNumber)) {
            return false;
        }

        // remove all non-numerics
        final String cardNumber = inputNumber.replaceAll("[^0-9]+", "");

        // number must be validated as 0..9 numeric first!!
        int digits = cardNumber.length();
        int oddOrEven = digits & 1;
        long sum = 0;
        for (int count = 0; count < digits; count++) {
            int digit = 0;
            try {
                digit = Integer.parseInt(cardNumber.charAt(count) + "");
            } catch (NumberFormatException e) {
                mMainView.onError(getLocalContext().getString(R.string.error_input_alpha_numberic));
            }

            if (((count & 1) ^ oddOrEven) == 0) { // not
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        if (sum % 10 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks for a valid credit card number.
     *
     * @param inputNumber Credit Card Number.
     * @return Whether the card number passes the luhnCheck.
     */
    @Override
    public void checkCardCheckSum(@NonNull String inputNumber) {

        mMainView.hideKeyboard();

        if (TextUtils.isEmpty(inputNumber)) {
            mMainView.onError(getLocalContext().getString(R.string.error_input_valid_card));
            return;
        }

        if (isValidLuhn(inputNumber)) {
            mMainView.onSuccess(getLocalContext().getString(R.string.success_valid_card));
        } else {
            mMainView.onError(getLocalContext().getString(R.string.error_invalid_card_number));
        }
    }

    public void updateCardWithIcon(@NonNull String inputNumber) {

        Log.d(TAG, " updateCardWithIcon inputNumber " + inputNumber);

        // remove all non-numerics
        final String cardNumber = inputNumber.replaceAll("[^0-9]+", "");

        CardType type = CardType.detect(cardNumber);
        Log.d(TAG, " updateCardWithIcon CardType " + type);
        mMainView.setCardType(type);
        int retVal = -1;

        switch (type) {
            case AMERICAN_EXPRESS:
                retVal = R.mipmap.amex_card_type_ic;
                break;

            case JCB:
                retVal = R.mipmap.jcb_card_type_ic;
                break;

            case DISCOVER:
                retVal = R.mipmap.discover_card_type_ic;
                break;

            case MASTERCARD:
                retVal = R.mipmap.mc_card_type_ic;
                break;

            case VISA:
                retVal = R.mipmap.visa_card_type_ic;
                break;

            default:
                retVal = R.mipmap.unknown_card_type_ic;
                mMainView.updateUnknownCardDrawable(retVal);
                break;
        }
        mMainView.updateValidCardDrawable(retVal);
    }

}
