package bhapps.utilitools.kotlin.ui.views.generate

import android.content.Context
import android.widget.TextView

class TextViews {

    //region TextViews
    /*
        *
        * setTextView(context: Context, id: String, text: String, align: Int, weight: Int?, alpha: Int?, layout_width: Int?, layout_height: Int?): TextView
        * use to add TextView to View
        * bhapps.utilitools.kotlin.ui.views.Generate.setTextView(context, id, text, align, weight, alpha, layout_width, layout_height)
        * returns TextView
        *
    */

    fun setTextView(context: Context, id: String, text: String, align: Int, weight: Int?, alpha: Int?, layout_width: Int?, layout_height: Int?): TextView {
        return TextView(context)
    }

    /*
        *
        * setBasicTextView(context: Context, text: String, color: Color): TextView
        * use to add TextView to View
        * bhapps.utilitools.kotlin.ui.views.Generate.setBasicTextView(context, text, color)
        *
        * Example use:
        *
        *   view.addView(bhapps.utilitools.kotlin.ui.views.Generate().setBasicTextView(this, "Test A", resources.getColor(R.color.colorAccent)))
        *
        * returns TextView
        *
    */

    fun setBasicTextView(context: Context, text: String, color: Int): TextView {

        var buildTextView = TextView(context)
        buildTextView.setText(text)
        buildTextView.setTextColor(color)

        return buildTextView
    }

    /*
        *
        * getTextView(context: Context, view: TextView, id: String, text: String, align: Int, weight: Int?, alpha: Int?, layout_width: Int?, layout_height: Int?): TextView
        * use to get TextView View
        * bhapps.utilitools.kotlin.ui.views.Generate.getTextView(context, view, id, text, align, weight, alpha, layout_width, layout_height)
        * returns TextView
        *
    */

    fun getTextView(context: Context, view: TextView, id: String): TextView {
        return TextView(context)
    }

    /*
        *
        * updateTextView(context: Context, view: TextView, id: String, text: String, align: Int, weight: Int?, alpha: Int?, layout_width: Int?, layout_height: Int?): TextView
        * use to update TextView View
        * bhapps.utilitools.kotlin.ui.views.Generate.updateTextView(context, view, id, text, align, weight, alpha, layout_width, layout_height)
        * returns TextView
        *
    */

    fun updateTextView(context: Context, view: TextView, id: String, text: String, align: Int, weight: Int?, alpha: Int?, layout_width: Int?, layout_height: Int?): Boolean {
        return true
    }
    //endregion TextViews

}