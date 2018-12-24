package com.amoveo.amoveowallet.wallet

import android.util.Base64
import android.util.Log
import org.bouncycastle.asn1.sec.SECNamedCurves
import org.bouncycastle.asn1.x9.ECNamedCurveTable
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.crypto.params.ECPrivateKeyParameters
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECNamedCurveSpec
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.security.*
import java.security.spec.ECPrivateKeySpec
import java.security.spec.InvalidKeySpecException

class CryptoRepository {
    init {
        Security.insertProviderAt(BouncyCastleProvider() as Provider, 1)
    }

    private val EC_GEN_PARAM_SPEC = "secp256k1"
    private val HEX_RADIX = 16

    val curve = SECNamedCurves.getByName(EC_GEN_PARAM_SPEC)
    val domain = ECDomainParameters(curve.curve, curve.g, curve.n, curve.h)

    fun generateTransaction(tx: List<Any>, privateKey: String): String {
        return sign(tx, privateKey)
    }

    fun getPrivateKeyFromECBigIntAndCurve(s: BigInteger, curveName: String): PrivateKey? {
        val ecCurve = ECNamedCurveTable.getByName(curveName)
        val ecParameterSpec = ECNamedCurveSpec(curveName, ecCurve.getCurve(), ecCurve.getG(), ecCurve.getN(), ecCurve.getH(), ecCurve.getSeed())
        val privateKeySpec = ECPrivateKeySpec(s, ecParameterSpec)
        try {
            val keyFactory = KeyFactory.getInstance("EC")
            return keyFactory.generatePrivate(privateKeySpec)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        } catch (e: InvalidKeySpecException) {
            e.printStackTrace()
            return null
        }
    }

    fun sign(data: List<Any>, privateKey: String): String {
        Log.d("sign", "$data -- $privateKey")
        Log.d("pkey - BigInteger", "${BigInteger(privateKey, HEX_RADIX)}")
        val key = ECPrivateKeyParameters(BigInteger(privateKey, HEX_RADIX), domain)

        try {
            Log.d("data", "$data")
            Log.d("Key", key.toString())

            val serializedData = serialize(data) as List<Byte>

            val baos1 = ByteArrayOutputStream()
            serializedData.toByteArray().forEach {
                if (it < 0) {
                    baos1.write(it + 256)
                } else baos1.write(it.toInt())
            }

            baos1.toByteArray()

            Log.d("serializedData", "$serializedData")

            val ecdsaSign = Signature.getInstance("SHA256withECDSA", "BC")

            ecdsaSign.initSign(getPrivateKeyFromECBigIntAndCurve(BigInteger(privateKey, HEX_RADIX), EC_GEN_PARAM_SPEC))
            ecdsaSign.update(baos1.toByteArray())
            val signature = ecdsaSign.sign()

            return Base64.encodeToString(signature, Base64.NO_WRAP);
        } catch (e: Exception) {
            System.out.println();
        }

        return ""
    }
}