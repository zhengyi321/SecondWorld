package com.et.secondworld.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public class SysUtils {

    /**
     * 将dp转换为px
     *
     * @param dp
     * @return
     */
    public static int convertDpToPixel(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (metrics.density * dp + 0.5f);
    }

    public static int convertDpToPixel(float dp) {
        DisplayMetrics metrics = GlobalParams.mApplication.getResources().getDisplayMetrics();
        return (int) (metrics.density * dp + 0.5f);
    }

    public static int convertSpToPixel(Context context, float sp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, metrics));
    }

    /**
     * 将字符串转换成算术式并进行计算
     *
     * @param str 算术
     * @return 结果
     */
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') {
                    nextChar();
                }
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) {
                        // addition
                        x += parseTerm();
                    } else if (eat('-')) {
                        // subtraction
                        x -= parseTerm();
                    } else {
                        return x;
                    }
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) {
                        // multiplication
                        x *= parseFactor();
                    } else if (eat('/')) {
                        // division
                        x /= parseFactor();
                    } else {
                        return x;
                    }
                }
            }

            double parseFactor() {
                if (eat('+')) {
                    // unary plus
                    return parseFactor();
                }
                if (eat('-')) {
                    // unary minus
                    return -parseFactor();
                }

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        nextChar();
                    }
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') {
                    // functions
                    while (ch >= 'a' && ch <= 'z') {
                        nextChar();
                    }
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) {
                        x = Math.sqrt(x);
                    } else if (func.equals("sin")) {
                        x = Math.sin(Math.toRadians(x));
                    } else if (func.equals("cos")) {
                        x = Math.cos(Math.toRadians(x));
                    } else if (func.equals("tan")) {
                        x = Math.tan(Math.toRadians(x));
                    } else {
                        throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) {
                    // exponentiation
                    x = Math.pow(x, parseFactor());
                }
                return x;
            }
        }.parse();
    }

    /**
     * 字符转化为浮点数
     */
    public static float parseFloat(String nStr) {
        float n;
        try {
            n = Float.parseFloat(nStr);
        } catch (Exception e) {
            n = 0.0f;
        }
        return n;
    }

    /**
     * 字符转化为数字(正整数)
     */
    public static int parseInt(String nStr) {
        int n;
        try {
            n = Integer.parseInt(nStr);
        } catch (Exception e) {
            n = -1;
        }
        return n;
    }

    /**
     * 字符转化为数字(正整数)
     */
    public static long parseLong(String nStr) {
        long n;
        try {
            n = Long.parseLong(nStr);
        } catch (Exception e) {
            n = -1;
        }
        return n;
    }

    /**
     * 字符转化为浮点数
     */
    public static double parseDouble(String nStr) {
        double n;
        try {
            n = Double.parseDouble(nStr);
        } catch (Exception e) {
            n = 0.0f;
        }
        return n;
    }

    public static boolean isEmpty(String string) {
        if (string == null || string.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(GlobalParams.mApplication, resId);
    }

    /**
     * 获取不为空的字符串
     *
     * @param string
     * @return string
     */
    public static String getSafeString(String string) {
        if (!isEmpty(string)) {
            return string;
        } else {
            return "";
        }
    }

    /**
     * 判断String是不是为纯字母数字
     */
    public static boolean IsNumLetter(String letter) {
        if (letter == null || letter.length() <= 0) {
            return false;
        }
        for (int i = 0; i < letter.length(); i++) {
            char c = letter.charAt(i);
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断String是不是为纯数字
     */
    public static boolean IsNumber(String nNum) {
        if (nNum == null || nNum.length() <= 0) {
            return false;
        }
        for (int i = 0; i < nNum.length(); i++) {
            char c = nNum.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context context
     * @return int
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context context
     * @return int
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 针对全面屏的屏幕高度
     *
     * @param activity activity
     * @return int
     */
    public static int getScreenRealHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        } else {
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        return dm.heightPixels;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context context
     * @return int
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
