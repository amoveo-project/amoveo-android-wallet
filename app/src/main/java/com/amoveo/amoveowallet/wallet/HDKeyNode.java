package com.amoveo.amoveowallet.wallet;

import com.google.common.base.Preconditions;

import java.math.BigInteger;
import java.util.Arrays;

import static com.amoveo.amoveowallet.wallet.ECKeyPair.createPrivateKey;
import static com.amoveo.amoveowallet.wallet.HDUtils.sha512;
import static com.amoveo.amoveowallet.wallet.Sign.CURVE;
import static java.nio.charset.StandardCharsets.UTF_8;

public class HDKeyNode {
    static final int HARDENED_MARKER = 0x80000000;
    private static final String NODE_SALT = "Bitcoin seed";

    private static final int CHAIN_CODE_SIZE = 32;

    private final ECKeyPair mECKeyPair;
    private final byte[] mChainCode;

    private HDKeyNode(ECKeyPair ecKeyPair, byte[] chainCode) {
        mECKeyPair = ecKeyPair;
        mChainCode = chainCode;
    }

    /**
     * Generate a master HD key node from a seed.
     *
     * @param seed the seed to generate the master HD wallet key from.
     * @return a master HD key node for the seed
     */
    // see https://github.com/bitcoinj/bitcoinj/blob/master/core/src/main/java/org/bitcoinj/crypto/HDKeyDerivation.java#L63
    static HDKeyNode fromSeed(byte[] seed) {
        byte[] I = sha512(NODE_SALT.getBytes(UTF_8), seed);

        return new HDKeyNode(createPrivateKey(Arrays.copyOfRange(I, 0, 32)), Arrays.copyOfRange(I, 32, 32 + CHAIN_CODE_SIZE));
    }

    /**
     * Create the child node of this node with the corresponding index
     *
     * @param index the index to use
     * @return the child node corresponding to the specified index
     */
    HDKeyNode createChildNodeByIndex(int index) {
        byte[] data;
        if (0 == (index & HARDENED_MARKER)) {
            byte[] publicKeyBytes = mECKeyPair.getPublicKeyBytes(true);
            // Not hardened key
            ByteWriter writer = new ByteWriter(publicKeyBytes.length + 4);
            writer.putBytes(publicKeyBytes);
            writer.putIntBE(index);
            data = writer.toBytes();
        } else {
            // Hardened key
            ByteWriter writer = new ByteWriter(33 + 4);
            writer.put((byte) 0);
            writer.putBytes(bigIntegerTo32Bytes(mECKeyPair.getPrivateKey()));
            writer.putIntBE(index);
            data = writer.toBytes();
        }
        byte[] l = sha512(mChainCode, data);
        // Always isPrivateHDKeyNode == true
        // Make a 32 byte result where k is copied to the end

        return new HDKeyNode(createPrivateKey(new BigInteger(1, Arrays.copyOfRange(l, 0, 32)).add(new BigInteger(1, bigIntegerTo32Bytes(mECKeyPair.getPrivateKey()))).mod(CURVE.getN())), Arrays.copyOfRange(l, 32, 64));
    }

    public static byte[] bigIntegerTo32Bytes(BigInteger b) {
        // Returns an array of bytes which is at most 33 bytes long, and possibly
        // with a leading zero
        byte[] bytes = b.toByteArray();
        Preconditions.checkArgument(bytes.length <= 33);
        if (bytes.length == 33) {
            // The result is 32 bytes, but with zero at the beginning, which we
            // strip
            Preconditions.checkArgument(bytes[0] == 0);
            return Arrays.copyOfRange(bytes, 1, 33);
        }
        // The result is 32 bytes or less, make it 32 bytes with the data at the
        // end
        byte[] result = new byte[32];
        System.arraycopy(bytes, 0, result, result.length - bytes.length, bytes.length);
        return result;
    }

    public ECKeyPair getECKeyPair() {
        return mECKeyPair;
    }
}
