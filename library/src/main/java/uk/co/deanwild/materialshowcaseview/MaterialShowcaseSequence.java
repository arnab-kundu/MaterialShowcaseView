package uk.co.deanwild.materialshowcaseview;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MaterialShowcaseSequence implements IDetachedListener {

    PrefsManager mPrefsManager;
    List<MaterialShowcaseView> mShowcaseQueue;
    private boolean mSingleUse = false;
    Activity mActivity;
    private ShowcaseConfig mConfig;
    private int mSequencePosition = 0;

    private OnSequenceItemShownListener mOnItemShownListener = null;
    private OnSequenceItemDismissedListener mOnItemDismissedListener = null;
    int count = -1;
    private boolean isNext = true;

    public MaterialShowcaseSequence(Activity activity) {
        mActivity = activity;
        mShowcaseQueue = new ArrayList<>();
    }

    public MaterialShowcaseSequence(Activity activity, String sequenceID) {
        this(activity);
        this.singleUse(sequenceID);
    }

   /* public MaterialShowcaseSequence addSequenceItem(View targetView, String content, String dismissText) {
        addSequenceItem(targetView, "", content, dismissText);
        return this;
    }*/

    public MaterialShowcaseSequence addSequenceItem(View targetView, String title, String content) {

        MaterialShowcaseView sequenceItem = new MaterialShowcaseView.Builder(mActivity)
                .setTarget(targetView)
                .setTitleText(title)
                .setDismissText("")
                .setContentText(content)
                .setSequence(true)
                .build();

        if (mConfig != null) {
            sequenceItem.setConfig(mConfig);
        }

        mShowcaseQueue.add(sequenceItem);
        return this;
    }

    private MaterialShowcaseSequence addSequenceItem(MaterialShowcaseView sequenceItem) { //need to make private

        if (mConfig != null) {
            sequenceItem.setConfig(mConfig);
        }

        mShowcaseQueue.add(sequenceItem);
        return this;
    }

    public MaterialShowcaseSequence singleUse(String sequenceID) {
        mSingleUse = true;
        mPrefsManager = new PrefsManager(mActivity, sequenceID);
        return this;
    }

    public void setOnItemShownListener(OnSequenceItemShownListener listener) {
        this.mOnItemShownListener = listener;
    }

    public void setOnItemDismissedListener(OnSequenceItemDismissedListener listener) {
        this.mOnItemDismissedListener = listener;
    }

    public boolean hasFired() {

        if (mPrefsManager.getSequenceStatus() == PrefsManager.SEQUENCE_FINISHED) {
            return true;
        }

        return false;
    }

    public void start() {

        /**
         * Check if we've already shot our bolt and bail out if so         *
         */
        if (mSingleUse) {
            if (hasFired()) {
                return;
            }

            /**
             * See if we have started this sequence before, if so then skip to the point we reached before
             * instead of showing the user everything from the start
             */
            mSequencePosition = mPrefsManager.getSequenceStatus();

            if (mSequencePosition > 0) {
                for (int i = 0; i < mSequencePosition; i++) {
                    //mShowcaseQueue.poll();
                }
            }
        }


        // do start
        if (mShowcaseQueue.size() > 0)
            showNextItem();
    }

    private void showNextItem() {
        if (isNext) {
            count++;
        } else {
            count--;
        }
        if (mShowcaseQueue.size() > 0 && !mActivity.isFinishing() && count <= mShowcaseQueue.size() - 1 && count >= 0) {
            MaterialShowcaseView sequenceItem = mShowcaseQueue.get(count);
            sequenceItem.setDetachedListener(this);
            sequenceItem.show(mActivity, mShowcaseQueue.size(), count);
            if (mOnItemShownListener != null) {
                mOnItemShownListener.onShow(sequenceItem, mSequencePosition);
            }
        } else {
            /**
             * We've reached the end of the sequence, save the fired state
             */
            if (mSingleUse && count != -1) {
                mPrefsManager.setFired();
            }
        }
    }

    private void skipTutorial() {

        mShowcaseQueue.clear();

        if (mShowcaseQueue.size() > 0 && !mActivity.isFinishing()) {
            MaterialShowcaseView sequenceItem = mShowcaseQueue.get(0);
            sequenceItem.setDetachedListener(this);
            sequenceItem.show(mActivity, mShowcaseQueue.size(), 0);
            if (mOnItemShownListener != null) {
                mOnItemShownListener.onShow(sequenceItem, mSequencePosition);
            }
        } else {
            /**
             * We've reached the end of the sequence, save the fired state
             */
            if (mSingleUse) {
                mPrefsManager.setFired();
            }
        }
    }


    @Override
    public void onShowcaseDetached(MaterialShowcaseView showcaseView, boolean wasDismissed, boolean wasSkipped) {

        showcaseView.setDetachedListener(null);

        /**
         * We're only interested if the showcase was purposefully dismissed
         */
        if (wasDismissed) {

            if (mOnItemDismissedListener != null) {
                mOnItemDismissedListener.onDismiss(showcaseView, mSequencePosition);
            }

            /**
             * If so, update the prefsManager so we can potentially resume this sequence in the future
             */
            if (mPrefsManager != null) {
                mSequencePosition++;
                mPrefsManager.setSequenceStatus(mSequencePosition);
            }
            if (!wasSkipped)
                showNextItem();
        }

        if (wasSkipped) {
            if (mOnItemDismissedListener != null) {
                mOnItemDismissedListener.onDismiss(showcaseView, mSequencePosition);
            }

            /**
             * If so, update the prefsManager so we can potentially resume this sequence in the future
             */
            if (mPrefsManager != null) {
                mSequencePosition++;
                mPrefsManager.setSequenceStatus(mSequencePosition);
            }

            skipTutorial();
        }
    }

    public void setConfig(ShowcaseConfig config) {
        this.mConfig = config;
    }

    @Override
    public boolean swipeForNext(boolean isNext) {
        this.isNext = isNext;
        return isNext;
    }

    public interface OnSequenceItemShownListener {
        void onShow(MaterialShowcaseView itemView, int position);
    }

    public interface OnSequenceItemDismissedListener {
        void onDismiss(MaterialShowcaseView itemView, int position);
    }

}
