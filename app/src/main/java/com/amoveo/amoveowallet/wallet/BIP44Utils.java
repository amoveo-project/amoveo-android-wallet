package com.amoveo.amoveowallet.wallet;

import static com.amoveo.amoveowallet.wallet.HDKeyNode.HARDENED_MARKER;

class BIP44Utils {
    static ECKeyPair deriveKey(byte[] seed, String path) {
        return deriveKey(seed, parsePath(path));
    }

    private static ECKeyPair deriveKey(byte[] seed, Integer[] path) {
        HDKeyNode hdKeyNode = HDKeyNode.fromSeed(seed);

        for (Integer node : path) {
            hdKeyNode = hdKeyNode.createChildNodeByIndex(node);
        }

        return hdKeyNode.getECKeyPair();
    }

    private static Integer[] parsePath(String path) {
        String[] nodes = path.toLowerCase().split("/");
        if ("m".equals(nodes[0].trim())) {
            Integer[] result = new Integer[nodes.length - 1];
            for (int index = 1; index < nodes.length; index++) {
                String trim = nodes[index].trim();
                result[index - 1] = trim.endsWith("\'") ? Integer.valueOf(trim.substring(0, trim.length() - 1)) | HARDENED_MARKER : Integer.valueOf(trim);
            }

            return result;
        } else {
            throw new IllegalArgumentException("Invalid derive path");
        }
    }
}
