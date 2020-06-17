package io.alcatraz.libalcatrazui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import io.alcatraz.libalcatrazui.R
import io.alcatraz.libalcatrazui.databinding.ViewInteractiveCardBinding
import io.alcatraz.libalcatrazui.utils.AnimateUtils
import io.alcatraz.libalcatrazui.utils.Utils

class InteractiveCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var cardBinding: ViewInteractiveCardBinding =
        ViewInteractiveCardBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
    private var showOverlay = false
    private var showProgress = false
    private var showOverlaySubInfo = true
    private var showOverlayInfo = true
    private var showSubtitle = true
    private var indicatorImageSrc: Int = 0
    private var indicatorImageTint: Int = 0
    private var baseCardBackgroundColor: Int = 0
    private var overlayIndicatorImageSrc: Int = 0
    private var overlayIndicatorImageTint: Int = 0
    private var overlayCardBackgroundColor: Int = 0
    private var title = ""
    private var subtitle = ""
    private var overlayTitle = ""
    private var overlayInfo = ""
    private var overlaySubInfo = ""
    private var cardMargin: Int = Utils.dp2Px(context, 16f)
    private var cardCornerRadius: Int = Utils.dp2Px(context, 8f)

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.InteractiveCardView)
            showOverlay = typedArray.getBoolean(R.styleable.InteractiveCardView_showOverlay, false)
            showProgress =
                typedArray.getBoolean(R.styleable.InteractiveCardView_showProgress, false)
            showOverlaySubInfo =
                typedArray.getBoolean(R.styleable.InteractiveCardView_showOverlaySubInfo, true)
            showOverlayInfo =
                typedArray.getBoolean(R.styleable.InteractiveCardView_showOverlayInfo, true)
            showSubtitle = typedArray.getBoolean(R.styleable.InteractiveCardView_showSubtitle, true)
            indicatorImageSrc =
                typedArray.getResourceId(R.styleable.InteractiveCardView_indicatorImageSrc, 0)
            indicatorImageTint =
                typedArray.getColor(R.styleable.InteractiveCardView_indicatorImageTint, 0)
            baseCardBackgroundColor =
                typedArray.getColor(R.styleable.InteractiveCardView_baseCardBackgroundColor, 0)
            overlayIndicatorImageSrc = typedArray.getResourceId(
                R.styleable.InteractiveCardView_overlayIndicatorImageSrc,
                0
            )
            overlayIndicatorImageTint =
                typedArray.getColor(R.styleable.InteractiveCardView_overlayIndicatorImageTint, 0)
            overlayCardBackgroundColor =
                typedArray.getColor(R.styleable.InteractiveCardView_overlayCardBackgroundColor, 0)
            title = typedArray.getString(R.styleable.InteractiveCardView_title) ?: ""
            subtitle = typedArray.getString(R.styleable.InteractiveCardView_subtitle) ?: ""
            overlayTitle = typedArray.getString(R.styleable.InteractiveCardView_overlayTitle) ?: ""
            overlayInfo = typedArray.getString(R.styleable.InteractiveCardView_overlayInfo) ?: ""
            overlaySubInfo =
                typedArray.getString(R.styleable.InteractiveCardView_overlaySubInfo) ?: ""
            cardMargin = typedArray.getDimensionPixelSize(
                R.styleable.InteractiveCardView_cardMargin,
                Utils.dp2Px(context, 16f)
            )
            cardCornerRadius = typedArray.getDimensionPixelSize(
                R.styleable.InteractiveCardView_cardCornerRadius,
                Utils.dp2Px(context, 8f)
            )
            typedArray.recycle()
        }
        addView(cardBinding.root)
        loadAttributes()
    }

    fun loadAttributes() {
        if (showOverlay)
            cardBinding.alcInteractiveCardOverlayLayer.visibility = View.VISIBLE
        else
            cardBinding.alcInteractiveCardOverlayLayer.visibility = View.GONE

        cardBinding.alcInteractiveCardIndicatorImage.visibility = View.GONE
        if (showProgress) {
            cardBinding.alcInteractiveCardIndicatorProgress.visibility = View.VISIBLE
        } else {
            if (indicatorImageSrc != 0) {
                cardBinding.alcInteractiveCardIndicatorImage.visibility = View.VISIBLE
                if (indicatorImageTint == 0) {
                    indicatorImageTint = Color.BLACK
                }
                Utils.setImageWithTint(
                    cardBinding.alcInteractiveCardIndicatorImage,
                    indicatorImageSrc,
                    indicatorImageTint
                )
            }
        }

        cardBinding.alcInteractiveCardOverlayIndicatorImage.visibility = View.GONE
        if (overlayIndicatorImageSrc != 0) {
            cardBinding.alcInteractiveCardOverlayIndicatorImage.visibility = View.VISIBLE
            if (overlayIndicatorImageTint == 0) {
                overlayIndicatorImageTint = Color.BLACK
            }
            Utils.setImageWithTint(
                cardBinding.alcInteractiveCardOverlayIndicatorImage,
                overlayIndicatorImageSrc,
                overlayIndicatorImageTint
            )
        }

        cardBinding.alcInteractiveCardOverlaySubinfo.visibility =
            if (showOverlaySubInfo) View.VISIBLE else View.GONE
        cardBinding.alcInteractiveCardOverlayInfo.visibility =
            if (showOverlayInfo) View.VISIBLE else View.GONE
        cardBinding.alcInteractiveCardSubtitle.visibility =
            if (showOverlayInfo) View.VISIBLE else View.GONE

        cardBinding.alcInteractiveCardTitle.text = title
        cardBinding.alcInteractiveCardSubtitle.text = subtitle
        cardBinding.alcInteractiveCardOverlayTitle.text = overlayTitle
        cardBinding.alcInteractiveCardOverlayInfo.text = overlayInfo
        cardBinding.alcInteractiveCardOverlaySubinfo.text = overlaySubInfo

        if (baseCardBackgroundColor != 0) {
            cardBinding.alcInteractiveCardBaseLayer.setBackgroundColor(baseCardBackgroundColor)
        }

        cardBinding.alcInteractiveCardOverlayLayer.setBackgroundColor(overlayCardBackgroundColor)

        val cardParams = cardBinding.alcInteractiveCard.layoutParams as LinearLayout.LayoutParams
        cardParams.setMargins(
            cardMargin,
            Utils.dp2Px(context, 8f),
            cardMargin,
            cardParams.bottomMargin
        )
        cardBinding.alcInteractiveCard.layoutParams = cardParams
        cardBinding.alcInteractiveCard.radius = cardCornerRadius.toFloat()
    }

    @JvmOverloads
    fun setShowOverlay(value: Boolean, animate: Boolean = true) {
        if (value) {
            if (cardBinding.alcInteractiveCardOverlayLayer.visibility == View.GONE) {
                cardBinding.alcInteractiveCardOverlayLayer.visibility = View.VISIBLE
                if (animate) {
                    cardBinding.alcInteractiveCardOverlayLayer.post {
                        AnimateUtils.playStart(
                            cardBinding.alcInteractiveCardOverlayLayer,
                            null
                        )
                    }
                }
            }
        } else {
            if (cardBinding.alcInteractiveCardOverlayLayer.visibility == View.VISIBLE) {
                if (animate) {
                    cardBinding.alcInteractiveCardOverlayLayer.post {
                        AnimateUtils.playEnd(
                            cardBinding.alcInteractiveCardOverlayLayer
                        )
                    }
                } else {
                    cardBinding.alcInteractiveCardOverlayLayer.visibility = View.GONE
                }
            }
        }
    }

    fun setShowProgress(value: Boolean) {
        if (value) {
            cardBinding.alcInteractiveCardIndicatorProgress.visibility = View.VISIBLE
            cardBinding.alcInteractiveCardIndicatorImage.visibility = View.GONE
        } else {
            cardBinding.alcInteractiveCardIndicatorProgress.visibility = View.GONE
            cardBinding.alcInteractiveCardIndicatorImage.visibility = View.VISIBLE
        }
    }

    fun setMargin(pixelMLeft: Int, pixelMTop: Int, pixelMRight: Int, pixelMBottom: Int) {
        val cardParams = cardBinding.alcInteractiveCard.layoutParams as LinearLayout.LayoutParams
        cardParams.setMargins(pixelMLeft, pixelMTop, pixelMRight, pixelMBottom)
        cardBinding.alcInteractiveCard.layoutParams = cardParams
    }
}