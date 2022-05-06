package eu.maxkim.boredombuster.activity.ui

import androidx.annotation.StringRes
import eu.maxkim.boredombuster.R
import eu.maxkim.boredombuster.activity.model.Activity

val Activity.accessibilityLabelRes: Int
    @StringRes
    get() {
        return when (accessibility) {
            in 0f..0.3f -> R.string.label_easy
            in 0.3f..0.7f -> R.string.label_moderate
            else -> R.string.label_hard
        }
    }

val Activity.priceLabelRes: Int
    @StringRes
    get() {
        return when (price) {
            0.0f -> R.string.label_free
            in 0f..0.3f -> R.string.label_cheap
            in 0.3f..0.7f -> R.string.label_pricey
            else -> R.string.label_expensive
        }
    }