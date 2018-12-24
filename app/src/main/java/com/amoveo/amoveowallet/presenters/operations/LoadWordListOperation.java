package com.amoveo.amoveowallet.presenters.operations;

import com.amoveo.amoveowallet.RootActivity;
import com.amoveo.amoveowallet.engine.operations.EngineOperation;
import com.amoveo.amoveowallet.presenters.results.EmptyResult;
import com.amoveo.amoveowallet.utils.HLog;
import com.amoveo.amoveowallet.wallet.BIP39Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.amoveo.amoveowallet.engine.Engine.ENGINE;

public class LoadWordListOperation extends EngineOperation<EmptyResult> {
    private static final String EN_MNEMONIC_WORDS = "en-mnemonic-word-list.txt";
    private static final String FR_MNEMONIC_WORDS = "fr-mnemonic-word-list.txt";
    private static final String SP_MNEMONIC_WORDS = "sp-mnemonic-word-list.txt";
    private static final String IT_MNEMONIC_WORDS = "it-mnemonic-word-list.txt";
    private static final String CZ_MNEMONIC_WORDS = "cz-mnemonic-word-list.txt";
    private static final String RU_MNEMONIC_WORDS = "ru-mnemonic-word-list.txt";
    private static final String UK_MNEMONIC_WORDS = "uk-mnemonic-word-list.txt";
    private static final String IN_MNEMONIC_WORDS = "in-mnemonic-word-list.txt";
    private static final String JP_MNEMONIC_WORDS = "jp-mnemonic-word-list.txt";
    private static final String KO_MNEMONIC_WORDS = "ko-mnemonic-word-list.txt";
    private static final String CH_SM_MNEMONIC_WORDS = "ch-sm-mnemonic-word-list.txt";
    private static final String CH_TR_MNEMONIC_WORDS = "ch-tr-mnemonic-word-list.txt";

    private final RootActivity mActivity;

    private LoadWordListOperation(RootActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    protected EmptyResult execute() {
        if (null == BIP39Utils.EN_WORD_LIST) {
            BIP39Utils.EN_WORD_LIST = populateWordList(EN_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.FR_WORD_LIST) {
            BIP39Utils.FR_WORD_LIST = populateWordList(FR_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.SP_WORD_LIST) {
            BIP39Utils.SP_WORD_LIST = populateWordList(SP_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.IT_WORD_LIST) {
            BIP39Utils.IT_WORD_LIST = populateWordList(IT_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.CZ_WORD_LIST) {
            BIP39Utils.CZ_WORD_LIST = populateWordList(CZ_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.RU_WORD_LIST) {
            BIP39Utils.RU_WORD_LIST = populateWordList(RU_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.UK_WORD_LIST) {
            BIP39Utils.UK_WORD_LIST = populateWordList(UK_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.IN_WORD_LIST) {
            BIP39Utils.IN_WORD_LIST = populateWordList(IN_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.JP_WORD_LIST) {
            BIP39Utils.JP_WORD_LIST = populateWordList(JP_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.KO_WORD_LIST) {
            BIP39Utils.KO_WORD_LIST = populateWordList(KO_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.CH_SM_WORD_LIST) {
            BIP39Utils.CH_SM_WORD_LIST = populateWordList(CH_SM_MNEMONIC_WORDS);
        }

        if (null == BIP39Utils.CH_TR_WORD_LIST) {
            BIP39Utils.CH_TR_WORD_LIST = populateWordList(CH_TR_MNEMONIC_WORDS);
        }

        return new EmptyResult();
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onSuccess(EmptyResult result) {
        HLog.debug(TAG, "onSuccess");
    }

    @Override
    protected void onFailure(EmptyResult result) {
        HLog.error(TAG, "onFailure");
    }

    private List<String> populateWordList(String fileName) {
        List<String> result = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(mActivity.getAssets().open(fileName)));

            // do reading, usually loop until end of file reading
            String line;
            while ((line = reader.readLine()) != null) {
                //process line
                result.add(line);
            }
        } catch (IOException e) {
            HLog.error(TAG, "populateWordList reader", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                    HLog.error(TAG, "populateWordList close", e);
                }
            }
        }

        return result;
    }

    public static void loadWordList(RootActivity activity) {
        ENGINE.submit(new LoadWordListOperation(activity));
    }
}