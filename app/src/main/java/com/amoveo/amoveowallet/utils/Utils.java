package com.amoveo.amoveowallet.utils;

import android.os.Build;
import android.util.Base64;
import android.view.View;
import com.amoveo.amoveowallet.api.results.items.TransactionInfo;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.amoveo.amoveowallet.common.WalletContext.WALLET;
import static java.lang.String.format;

public class Utils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH);
    private static final DecimalFormat DECIMAL_FORMAT = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);

    private static final String DECIMAL_SEPARATOR = ".";
    private static final int FRACTION = 8;

    public static final Gson GSON = new Gson();

    public static String formatDouble(double value) {
        DECIMAL_FORMAT.setMaximumFractionDigits(18);
        return DECIMAL_FORMAT.format(value);
    }

    public static String formatDoubleStripped(String value) {
        String result = formatDouble(value);

        return 7 < result.length() ? result.substring(0, 7).concat("...") : result;
    }

    public static String formatDouble(String value) {
        String left;
        String right;

        if (FRACTION < value.length()) {
            left = value.substring(0, value.length() - FRACTION);
            right = value.substring(value.length() - FRACTION);
        } else {
            left = "0";
            right = getZeros(FRACTION - value.length()).concat(value);
        }

        while (1 < right.length() && right.endsWith("0")) {
            right = right.substring(0, right.length() - 1);
        }

        return left.concat(DECIMAL_SEPARATOR).concat(right);
    }

    public static long parseAmount(String value) {
        String[] values = isEmpty(value) ? null : value.split(Pattern.quote(DECIMAL_SEPARATOR));

        if (null == values) {
            return 0L;
        } else if (2 < values.length) {
            throw new IllegalArgumentException();
        } else if (2 == values.length) {
            return Long.parseLong(values[0].concat(FRACTION > values[1].length() ? values[1].concat(getZeros(FRACTION - values[1].length())) : values[1].substring(0, FRACTION)));
        } else {
            return Long.parseLong(values[0].concat(getZeros(FRACTION)));
        }
    }

    private static String getZeros(int length) {
        return 0 < length ? format("%0" + length + "d", 0) : "";
    }

    public static String formatDateFull(Date value) {
        return DATE_FORMAT.format(value);
    }

    public static TransactionInfo getTransactionInfo(JSONArray body) throws JSONException {
        return new TransactionInfo(body.getString(0), body.getString(1), body.getInt(2), body.getString(3), body.getString(4), body.getString(5));
    }

    public static boolean validateAddressTo(String address) {
        try {
            byte[] decoded = Base64.decode(address, Base64.NO_WRAP);
            return 65 == decoded.length && 4 == decoded[0] && address.endsWith("=") && !WALLET.getAddress().equals(address);
        } catch (Exception e) {
            return false;
        }
    }

    public static String formatAddress(String address, int length) {
        return length * 2 < address.length() ? address.substring(0, length).concat("\n").concat(address.substring(length, length * 2)).concat("\n").concat(address.substring(length * 2)) :
                length < address.length() ? address.substring(0, length).concat("\n").concat(address.substring(length)) : address;
    }

    public static String unformatAddress(String address) {
        return address.replaceAll("\n", "");
    }

    public static void selectLogo(View logo, View logoCompat) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            logo.setVisibility(VISIBLE);
            logoCompat.setVisibility(GONE);
        } else {
            logo.setVisibility(GONE);
            logoCompat.setVisibility(VISIBLE);
        }
    }
}