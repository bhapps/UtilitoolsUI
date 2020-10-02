package bhapps.utilitools.ui.kotlin.helpers

import android.R
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.util.StateSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlin.math.abs

object UIHelpers {


    //todo:
    //test, update, validate, close.
    //might require supportFragmentManager()
    fun loadFragmentIntoPassedViewFromFragment(fragmentManager: FragmentManager, fragment: Fragment, addToBackStack: Boolean, uiField: Int) {

        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(uiField, fragment)
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.toString())
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()

    }

    //region NetworkConnected UI

    /*
        *
        * NetworkConnected UI
        * use to provide value of the network state of the device
        * typical used to notify the UI of network state
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.updateConnectionStatus(activity)
        * returns boolean
        *
    */

    /*
        *
        * isNetworkConnected(activity)
        * determine if device has network/internet/external connection
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.isNetworkConnected(activity)
        * returns boolean
        *
    */

    fun isNetworkConnected(activity: Activity): Boolean {
        return activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager != null
    }

    fun isDeviceOnline(activity: Activity): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected()
    }

    /*
        *
        * updateConnectionStatus(activity)
        * return state if device has network/internet/external connection
        * uses method(s):
        *               isNetworkConnected(activity)
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.updateConnectionStatus(activity)
        * returns boolean
        *
    */

    fun updateConnectionStatus(activity: Activity): Boolean {
        var result = false
        if (isNetworkConnected(activity)) {
            result = true
        }
        return result
    }

    //endregion NetworkConnected UI

    //region misc UI Functions

    /*
        *
        * getRandomColor(context)
        * return color from system
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.getRandomColor(context)
        * returns int
        *
    */

    fun getRandomColor(context: Context): Int {
        var returnColor = Color.WHITE
        val arrayId = context.resources.getIdentifier("mdcolor_random", "array", context.packageName)

        if (arrayId != 0) {
            val colors = context.resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }

    /*
        *
        * getRandomColor(context)
        * return color from system
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.getRandomColor(context)
        * returns int
        *
    */

    fun getRandomColor(context: Context, string: String, index: Int): Int {
        var returnColor = Color.WHITE
        val arrayId = context.resources.getIdentifier("mdcolor_random", "array", context.packageName)

        if (arrayId != 0) {
            val colors = context.resources.obtainTypedArray(arrayId)
            var idx = index
            while (idx >= colors.length()) {
                idx = idx - 5
            }
            while (idx < 0) {
                idx = idx + 2
            }
            returnColor = colors.getColor(idx, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }

    /*
        *
        * setSystemStatusBarColorToTransparentColor(activity)
        * set the system bar colour to transparent
        * requires:
        *           R.color.bhapps_utilitools_colors_transparent.xml
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.setSystemStatusBarColorToTransparentColor(activity)
        *
    */

    fun setSystemStatusBarColorToTransparentColor(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = activity.resources.getColor(R.color.transparent)
        }
    }


    /*
        *
        * setSystemStatusBarColorToColor(activity, int)
        * set the system bar colour from passed R.color.*
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.setSystemStatusBarColorToColor(activity, int)
        *
    */

    fun setSystemStatusBarColorToColor(activity: Activity, @ColorRes colour: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = activity.resources.getColor(colour)
        }
    }

    /*
        *
        * setSystemStatusBarColorFromDialogToColor(activity, dialog, int)
        * set the system dialog colour from passed R.color.*
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.setSystemStatusBarColorFromDialogToColor(activity, int)
        *
    */

    fun setSystemStatusBarColorFromDialogToColor(activity: Context, dialog: Dialog, @ColorRes color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = dialog.window
            window!!.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = activity.resources.getColor(color)
        }
    }

    /*
        *
        * setSystemStatusBarToLightColor(activity)
        * set the system bar flag to SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.setSystemStatusBarToLightColor(activity)
        *
    */

    fun setSystemStatusBarToLightColor(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val view = activity.findViewById<View>(android.R.id.content)
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        }
    }

    /*
        *
        * clearSystemStatusBarLightColor(activity, int)
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.clearSystemStatusBarLightColor(activity, int)
        *
    */

    fun clearSystemStatusBarLightColor(activity: Activity, @ColorRes colour: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = activity.window
            window.statusBarColor = ContextCompat.getColor(activity, colour)
        }
    }

    /*
        *
        * setSystemDialogToLightColor(dialog)
        * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.setSystemDialogToLightColor(dialog)
        *
    */

    fun setSystemDialogToLightColor(dialog: Dialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val view = dialog.findViewById<View>(android.R.id.content)
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        }
    }

    /*
       *
       * setSystemStatusBarToTransparentColor(activity)
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.setSystemStatusBarToTransparentColor(activity)
       *
   */

    fun setSystemStatusBarToTransparentColor(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * @param colorId id of color
     * @param isDarkTheme Light or Dark color
     */
    fun updateStatusBarColor(activity: Activity, @ColorRes colorId: Int, isDarkTheme: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(activity, colorId)
            setSystemBarTheme(activity, isDarkTheme)
        }
    }

    /** Changes the System Bar Theme.  */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun setSystemBarTheme(activity: Activity, isDarkTheme: Boolean) {
        val lFlags = activity.window.decorView.systemUiVisibility
        activity.window.decorView.systemUiVisibility = if (isDarkTheme) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    public fun statusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /*
       *
       * getIntToDp(context, int)
       * use to change passed int to dp for providing width, height dp values
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.getIntToDp(context, int)
       * returns int
   */

    fun getIntToDp(context: Context, changeToDP: Int): Int {

        var IntToDp = changeToDP
        val r = context.resources
        IntToDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            changeToDP.toFloat(),
            r.displayMetrics
        ).toInt()

        return IntToDp

    }

    /*
       *
       * getDpToPx(context, float)
       * use to change passed float to px for providing width, height px values
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.getDpToPx(context, float)
       * returns float
   */

    fun getDpToPx(context: Context, dp: Float): Float {
        val metrics: DisplayMetrics = context.getResources().getDisplayMetrics()
        return dp * (metrics.densityDpi / 160f)
    }

    /*
       *
       * isSoftKeyboardInUIView(view, activity)
       * use to access status of soft keyboard is open or in view
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.isSoftKeyboardInUIView(view, activity)
       * returns boolean
   */

    fun isSoftKeyboardInUIView(view: View, activity: Activity): Boolean {

        var result = false;
        val view = activity.currentFocus
        view?.let { v ->
            val inputMethodManager = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager
            result = true
        }

        return result
    }


    /*
       *
       * hideKeyboardFromUIView(context)
       * use to set the hide/close status of the soft keyboard
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.hideKeyboardFromUIView(context)
   */

    fun hideKeyboardFromUIView(context: Context) {
        try {
            (context as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            if (context.currentFocus != null && context.currentFocus!!.windowToken != null) {
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    context.currentFocus!!.windowToken,
                    0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /*
       *
       * hideSoftKeyboardFromUIView(view)
       * use to set the hide/close status of the soft keyboard in passed view
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.hideSoftKeyboardFromUIView(view)
   */

    fun hideSoftKeyboardFromUIView(view: View) {
        val inputMethodManager = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /*
       *
       * showSoftKeyboard(view)
       * use to set the show/open status of the soft keyboard in passed view
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.showSoftKeyboard(view)
   */

    fun showSoftKeyboard(view: View?) {
        if (view == null) return
        try {
            val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e: Exception) {
            Log.e("showKeyboard", "Can't show keyboard ", e)
        }

    }

    /*
       *
       * showKeyboard(context)
       * use to set the show/open status of the soft keyboard
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.showKeyboard(context)
   */

    fun showKeyboard(context: Context) {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /*
       *
       * hideKeyboardFromActivity(activity, view)
       * use to set the show/open status of the soft keyboard
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.hideKeyboardFromActivity(activity, view)
   */

    fun hideKeyboardFromActivity(activity: Activity, view: View) {
        (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /*
       *
       * showKeyboardFromActivity(activity)
       * use to set the show/open status of the soft keyboard
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.showKeyboardFromActivity(activity)
   */

    fun showKeyboardFromActivity(activity: Activity) {
        (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
            toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    /*
       *
       * changeDrawableTintToColor(drawable, color)
       * change the tint color for passed drawable
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.changeDrawableTintToColor(drawable, color)
   */

    fun changeDrawableTintToColor(drawable: Drawable, color: Int): Drawable {
        if(drawable !=null && color !=null ){
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
        return drawable
    }

    /*
       *
       * getAllChildrenFromView(view)
       * retrieve all the children from passed view
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.getAllChildrenFromView(view)
   */

    fun getAllChildrenFromView(view: View) {
        val viewGroup = view as ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            if (child is ImageView) {
                val view = child as ImageView
                Log.e("getAllChildren: ", child.id.toString() + "\n" + "    - ImageView")
            }else if (child is View) {
                val view = child as View
                Log.e("getAllChildren: ", child.id.toString() + "\n" + "    - View")
            }else if (child is CheckBox) {
                val view = child as CheckBox
                Log.e("getAllChildren: ", child.id.toString() + "\n" + "    - CheckBox")
            }else if (child is RadioButton) {
                val view = child as RadioButton
                Log.e("getAllChildren: ", child.id.toString() + "\n" + "    - RadioButton")
            }else if (child is EditText) {
                val view = child as EditText
                Log.e("getAllChildren: ", child.id.toString() + "\n" + "    - EditText")
            }else if (child is FrameLayout) {
                val view = child as FrameLayout
                Log.e("getAllChildren: ", child.id.toString() + "\n" + "    - FrameLayout")
            }else if (child is LinearLayout) {
                val view = child as LinearLayout
                Log.e("getAllChildren: ", child.id.toString() + "\n" + "    - LinearLayout")
            }else if (child is RelativeLayout) {
                val view = child as RelativeLayout
                Log.e("getAllChildren: ", child.id.toString() + "\n" + "    - RelativeLayout")
            }
        }

    }

    /*
       *
       * loopViews(view)
       * cycle through all children of passed view
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.loopViews(view)
   */

    //@SuppressLint("RestrictedApi")
    private fun loopViews(view: ViewGroup) {
        for (i in 0 until view.childCount) {
            val v = view.getChildAt(i)
            Log.e("View:", "View found: $v")
            loopViews(v as ViewGroup)

        }
    }

    /*
       *
       * getTypeFaceByFont(context, font)
       * get TypeFace Font from passed Font
       * bhapps.utilitools.ui.kotlin.helpers.UIHelpers.getTypeFaceByFont(context, font)
   */

    fun getTypeFaceByFont(context: Context, font: Int): Typeface {
        return ResourcesCompat.getFont(context, font)!!
    }

    //endregion misc UI Functions

    //region Screen Width/Height Functions
    fun getScreenWidthFromDisplayMetrics(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeightFromDisplayMetrics(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }


    fun getScreenHeight(activity: Activity): Int {

        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var DEVICE_HEIGHT = size.y

        return DEVICE_HEIGHT
    }

    fun getScreenWidth(activity: Activity): Int {

        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var DEVICE_WIDTH = size.x

        return DEVICE_WIDTH
    }

    fun getHeightFromPercentageFromScreenHeightToDp(activity: Activity, context: Context, percentage: Int, min_height_in_dp: Int, max_height_in_dp: Int): Int {

        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var height = size.y

        if(percentage.equals(0) || percentage.equals(100)){
            return height
        }else{

            var percentage_to_dp = percentage / height
            percentage_to_dp * 100

            //if percentage is less than min or Greater than max so return as per min/max
            if(percentage_to_dp < min_height_in_dp){
                height = min_height_in_dp
            }else if(percentage_to_dp > max_height_in_dp) {
                height = max_height_in_dp
            }
        }

        return getIntToDp(context, height)
    }

    fun getWidthFromPercentageFromScreenWidthToDp(activity: Activity, context: Context, percentage: Int, min_width_in_dp: Int, max_width_in_dp: Int): Int {

        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var width = size.x

        if(percentage.equals(0) || percentage.equals(100)){
            return width
        }else{

            var percentage_to_dp = percentage / width
            percentage_to_dp * 100

            //if percentage is less than min or Greater than max so return as per min/max
            if(percentage_to_dp < min_width_in_dp){
                width = min_width_in_dp
            }else if(percentage_to_dp > max_width_in_dp) {
                width = max_width_in_dp
            }
        }

        return getIntToDp(context, width)
    }

    fun getParentViewHeightToDpUseInAfterViewTreeObserverOnGlobalLayoutListener(activity: Activity, context: Context, view: View, viewTreeObserver: ViewTreeObserver): Int {

        var height = 0
        var parent = view.parent
        if (parent == null) {
            Log.d("utilitools", "this.getParent() is null");
        }
        return getIntToDp(context, height)
    }

    //incomplete
    fun getParentViewWidthToDpUseInAfterViewTreeObserverOnGlobalLayoutListener(activity: Activity, context: Context, view: View, viewTreeObserver: ViewTreeObserver): Int {

        var width = 0
        if (viewTreeObserver.isAlive()) {
          viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener() {
              @Override
              fun onGlobalLayout() {
                  width = view.layoutParams.width
              }
          });
        }

        return getIntToDp(context, width)
    }

    //endregion Screen Width/Height Functions

    //region scroll functions
    open fun scrollToViewInNestedScrollView(nested: NestedScrollView, targetView: View): Unit {
        nested.post(Runnable { nested.scrollTo(500, targetView.bottom) })
    }

    //endregion  scroll functions

    fun copyStringToClipboard(context: Context, data: String?) {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("clipboard", data)
        clipboard.primaryClip = clip
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun insertStringPeriodically(
        text: String,
        insert: String,
        period: Int
    ): String? {
        val builder =
            StringBuilder(text.length + insert.length * (text.length / period) + 1)
        var index = 0
        var prefix = ""
        while (index < text.length) {
            builder.append(prefix)
            prefix = insert
            builder.append(text.substring(index, Math.min(index + period, text.length)))
            index += period
        }
        return builder.toString()
    }

    fun capitalizeFirstLetterOfEveryWord(wordsAsString: String): String
    {
        if (wordsAsString.isEmpty() || wordsAsString.isBlank()){
            return ""
        }

        if (wordsAsString.length == 1){
            return Character.toUpperCase(wordsAsString[0]).toString()
        }

        val wordsAsStringArray = wordsAsString.split(" ")
        val stringBuilder = StringBuilder()

        for ((index, item) in wordsAsStringArray.withIndex())
        {
            // If item is empty string, continue to next item
            if (item.isEmpty()){
                continue
            }

            stringBuilder.append(Character.toUpperCase(item[0]))

            // If the item has only one character then continue to next item because we have already capitalized it.
            if (item.length == 1){
                continue
            }

            for (i in 1 until item.length){
                stringBuilder.append(Character.toLowerCase(item[i]))
            }

            if (index < wordsAsStringArray.lastIndex){
                stringBuilder.append(" ")
            }
        }

        return stringBuilder.toString()
    }

    @ColorInt
    fun getThemeAttributeResourceColour(context: Context, @AttrRes colorAttr: Int): Int {
        val array: TypedArray = context.obtainStyledAttributes(null, intArrayOf(colorAttr))
        return try {
            array.getColor(0, 0)
        } finally {
            array.recycle()
        }
    }

    private fun createColorStateList(context: Context): ColorStateList? {
        return ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),  // Disabled state.
                StateSet.WILD_CARD
            ), intArrayOf(
                getThemeAttributeResourceColour(context, R.attr.colorAccent),  // Disabled state.
                getThemeAttributeResourceColour(context, R.attr.colorPrimary)
            )
        )
    }

    fun getThemeAttrColourFromPassedColourInt(context: Context, attr_resource: Int): Int {
        var result = 0
        val TypedValue = TypedValue()
        val theme = context.theme
        val checkAttrColourFount = theme.resolveAttribute(attr_resource, TypedValue, true)
        var colorFromTheme: Int
        if(checkAttrColourFount) {
            result = TypedValue.data
        }

        return result
    }

    @ColorInt
    fun getThemeAttributeResourceColourFromPassedColourInt(
        context: Context,
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
    ): Int {
        context.theme.resolveAttribute(attrColor, typedValue, resolveRefs)
        return typedValue.data
    }

}