package io.alcatraz.libalcatrazui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import io.alcatraz.libalcatrazui.R
import io.alcatraz.libalcatrazui.databinding.ViewFlattenedCardBinding
import io.alcatraz.libalcatrazui.utils.Utils

@Suppress("MemberVisibilityCanBePrivate")
class FlattenedCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var cardBinding: ViewFlattenedCardBinding =
        ViewFlattenedCardBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)

    var title = ""
        set(value) {
            field = value
            cardBinding.alcFlattenedCardTitle.text = value
        }
    var subtitle = ""
        set(value) {
            field = value
            cardBinding.alcFlattenedCardSubtitle.text = value
        }
    var showSubtitle = false
        set(value) {
            field = value
            updateCardTextAndVisibility()
        }
    var cardPubMargin: Int = Utils.dp2Px(context, 16f)
        set(value) {
            field = value
            updateCardInDefaultPattern()
        }
    var cardRippleRadius: Int = Utils.dp2Px(context, 8f)
        set(value) {
            field = value
            cardBinding.alcFlattenedCard.radius = value.toFloat()
        }
    var indicatorImageSrc: Int = 0
        set(value) {
            field = value
            updateCardImage()
        }
    var indicatorImageTint: Int = 0
        set(value) {
            field = value
            updateCardImage()
        }

    init {
        loadAttributes(attrs)
        addView(cardBinding.root)
        updateAttributes()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        cardBinding.alcFlattenedCard.setOnClickListener(l)
    }

    fun loadAttributes(attrs: AttributeSet? = null) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlattenedCardView)
            title = typedArray.getString(R.styleable.FlattenedCardView_cardTitle) ?: ""
            subtitle = typedArray.getString(R.styleable.FlattenedCardView_cardSubtitle) ?: ""
            showSubtitle =
                typedArray.getBoolean(R.styleable.FlattenedCardView_cardShowSubtitle, false)
            cardPubMargin = typedArray.getDimensionPixelSize(
                R.styleable.FlattenedCardView_cardPubMargin,
                Utils.dp2Px(context, 16f)
            )
            cardRippleRadius = typedArray.getDimensionPixelSize(
                R.styleable.FlattenedCardView_cardRippleRadius,
                Utils.dp2Px(context, 8f)
            )
            indicatorImageSrc =
                typedArray.getResourceId(R.styleable.FlattenedCardView_imageSrc, 0)
            indicatorImageTint =
                typedArray.getColor(R.styleable.FlattenedCardView_imageTint, 0)
            typedArray.recycle()
        }
    }

    fun updateAttributes() {
        updateCardImage()
        updateCardTextAndVisibility()
        updateCardInDefaultPattern()
    }

    fun updateCardImage() {
        cardBinding.alcFlattenedCardImage.visibility = View.GONE
        if (indicatorImageSrc != 0) {
            cardBinding.alcFlattenedCardImage.visibility = View.VISIBLE
            if (indicatorImageTint == 0) {
                indicatorImageTint = Color.BLACK
            }
            Utils.setImageWithTint(
                cardBinding.alcFlattenedCardImage,
                indicatorImageSrc,
                indicatorImageTint
            )
        }
    }

    fun updateCardTextAndVisibility() {
        cardBinding.alcFlattenedCardSubtitle.visibility =
            if (showSubtitle) View.VISIBLE else View.GONE

        cardBinding.alcFlattenedCardTitle.text = title
        cardBinding.alcFlattenedCardSubtitle.text = subtitle
    }

    fun updateCardInDefaultPattern() {
        val cardParams = cardBinding.alcFlattenedCard.layoutParams as LinearLayout.LayoutParams
        cardParams.setMargins(
            cardPubMargin,
            Utils.dp2Px(context, 8f),
            cardPubMargin,
            cardParams.bottomMargin
        )
        cardBinding.alcFlattenedCard.layoutParams = cardParams
        cardBinding.alcFlattenedCard.radius = cardRippleRadius.toFloat()
    }

    fun setMargin(pixelMLeft: Int, pixelMTop: Int, pixelMRight: Int, pixelMBottom: Int) {
        val cardParams = cardBinding.alcFlattenedCard.layoutParams as LinearLayout.LayoutParams
        cardParams.setMargins(pixelMLeft, pixelMTop, pixelMRight, pixelMBottom)
        cardBinding.alcFlattenedCard.layoutParams = cardParams
    }
}