package com.beeshop.beeshop.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @DESC: 处理额度小数点以及US 金额带逗号处理
 * @Author: Jason
 * @Date: 16/7/4
 * @Time: 12:02
 */

public class DecimalFormatUtils {

    /**
     * 整型 带逗号
     *
     * @return
     */
    public static DecimalFormat getIntegerCommaText() {
        return new DecimalFormat("#,##0"); // 带,不带小数点
    }

    /**
     * 浮点型 带逗号 两位小数
     */
    public static DecimalFormat getDoubleCommaDotText() {
        return new DecimalFormat("#,##0.00");
    }

    /**
     * 浮点型 不带逗号 两位小数
     */
    public static DecimalFormat getDoubleDotText() {
        return new DecimalFormat("#0.00");
    }

    /**
     * 保留两位小数 四舍五入
     * @return
     */
    public static String decimalFormatRoundUp(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(value);
    }

    /**
     * 浮点型 不带逗号 一位小数
     */
    public static DecimalFormat getSingleDotText() {
        return new DecimalFormat("#0.0");
    }

    /**
     * 抹零保留小数
     */
    public static DecimalFormat getRemoveODec() {
        return new DecimalFormat("#.##");
    }

    /**
     * 保留两位小数 四舍五入 带逗号
     * @return
     */
    public static String decimalFormatCommaDotRoundUp(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(value);
    }

    /**
     * 保留两位小数 四舍五入
     * @return
     */
    public static String decimalFormatCommaDotRoundUpNormal(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("###0.00000");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(value);
    }

    /**
     * 保留两位小数 不四舍五入
     * @param value
     * @return
     */
    public static String formatKeepTwoDec(double value) {
        DecimalFormat formater = new DecimalFormat("#0.00");
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(value);
    }

}
