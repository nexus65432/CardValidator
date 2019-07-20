package co.card.processor.listener;

import android.content.Context;

import co.card.processor.model.CardType;

/**
 * This interface provides card Info on UI thread
 */
public interface MainView {

    /**
     * Provide the context to the presenter
     * @return context
     */
    Context getViewContext();

    /**
     * Set the cardtype
     * @param type
     */
    void setCardType(CardType type);

    /**
     * update the cardType image
     * @param resourceId
     */
    void updateValidCardDrawable(int resourceId);

    /**
     * Update icon with unknown image
     * @param resourceId
     */
    void updateUnknownCardDrawable(int resourceId);

    /**
     * When the Algorithm gives error then show Error
     * @param message
     */
    void onError(String message);

    /**
     * When the Algorithm passes then show Success
     * @param message
     */
    void onSuccess(String message);

    /**
     * Hide keyboard after user action
     */
    void hideKeyboard();
}
