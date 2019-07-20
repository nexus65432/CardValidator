package co.card.processor.listener;

import android.support.annotation.NonNull;

public interface MainPresenter {

    /**
     * Attach to the View, this will bind a communication between UI
     */
    void onAttach();

    /**
     * Check card checkSum for the given card number
     */
    void checkCardCheckSum(@NonNull String cardNumber);

    /**
     * Prepare to exit the UI, stop any schedulers and cleanup any other views
     */
    void prepareToExit();

    /**
     * Reset any values and make room for GC to cleanup any used resources
     */
    void onDetach();

}
