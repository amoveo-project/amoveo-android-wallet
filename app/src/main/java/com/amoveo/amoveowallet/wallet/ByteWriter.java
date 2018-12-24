package com.amoveo.amoveowallet.wallet;

class ByteWriter {
    private byte[] mBuf;
    private int mIndex;

    ByteWriter(int capacity) {
        mBuf = new byte[capacity];
        mIndex = 0;
    }

    void putBytes(byte[] value) {
        ensureCapacity(value.length);
        System.arraycopy(value, 0, mBuf, mIndex, value.length);
        mIndex += value.length;
    }

    void putIntBE(int value) {
        ensureCapacity(4);
        mBuf[mIndex++] = (byte) (0xFF & (value >> 24));
        mBuf[mIndex++] = (byte) (0xFF & (value >> 16));
        mBuf[mIndex++] = (byte) (0xFF & (value >> 8));
        mBuf[mIndex++] = (byte) (0xFF & (value >> 0));
    }

    byte[] toBytes() {
        byte[] bytes = new byte[mIndex];
        System.arraycopy(mBuf, 0, bytes, 0, mIndex);
        return bytes;
    }

    void put(byte b) {
        ensureCapacity(1);
        mBuf[mIndex++] = b;
    }

    private void ensureCapacity(int capacity) {
        if (mBuf.length - mIndex < capacity) {
            byte[] temp = new byte[mBuf.length * 2 + capacity];
            System.arraycopy(mBuf, 0, temp, 0, mIndex);
            mBuf = temp;
        }
    }
}
