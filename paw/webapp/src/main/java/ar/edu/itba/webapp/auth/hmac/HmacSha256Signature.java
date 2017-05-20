package ar.edu.itba.webapp.auth.hmac;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Hex;
import org.slf4j.Logger;


public class HmacSha256Signature {
	private static final Logger logger = LoggerFactory.getLogger(HmacSha256Signature.class);
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	private static final Charset charset = Charset.forName("US-ASCII");

	public static String calculateRFC2104HMAC(String data, String key)
		throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
	{
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(charset), HMAC_SHA256_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
		mac.init(signingKey);
		logger.debug("key is {}", key);
		logger.debug("data is {}", data);
		return String.valueOf(Hex.encode(mac.doFinal(data.getBytes(charset))));
	}
}
