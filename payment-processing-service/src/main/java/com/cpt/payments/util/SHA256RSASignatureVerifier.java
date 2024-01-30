package com.cpt.payments.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class SHA256RSASignatureVerifier {

	public boolean verifySignature(String xSignature, String requestBody, String publicKey) throws Exception {
		PublicKey localPublicKey = getPublicKey(publicKey);
		Signature publicSignature = Signature.getInstance("SHA256withRSA");
		publicSignature.initVerify(localPublicKey);
		publicSignature.update(requestBody.getBytes(UTF_8));
		byte[] signatureBytes = Base64.getDecoder().decode(xSignature);
		return publicSignature.verify(signatureBytes);
	}

	private PublicKey getPublicKey(String keypath) throws Exception {
		String publicKey = new String(Files.readAllBytes(Paths.get(keypath)), Charset.defaultCharset());

		byte[] publicBytes = Base64.getDecoder().decode(getPublicKeyFromFile(publicKey));
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	private String getPublicKeyFromFile(String publicKey) {
		return publicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
				.replaceAll("\r", "").replaceAll("\n", "");
	}

//	    public static void main(String[] args) throws Exception {
//	    	String xSignature="ZOsnzNke+ZvkoPU/6+A1zj7YRjntfy7CD7LgRrtWoEmd/f/7VKvSjzEOanSq7CQUnH5Cy1tkBN+Ok9Z5BzbzGF+XGaeQikbHoOAhljm3E8PaCldDndR6neQYZJuU0XZ/hIhjMm4lRN9/U1T8d4Yts0gL8LjdlmcWUCYsNeex3oBV5RJc5UUuLOszVTyl6AEHfZHx9xo/g4vJW8Rr0ToaD0rN8YUCG2+/LMHzStIdw/9fm1Xt1Xb4BXiWuytZjmzxkKZyCkAnSL6QOx/fULVMPUlRHOOEOQ9sKj1W5a7wLOP6DPnRvpn7EFtgBzCwebkGGG3fWjli/REVLshpqpaFiA==";
//	    	String requestBody = "get|api2.ct.com/merchant/providers?countrycode=lt&pageno=1&pagesize=1000|";
//	    	String publicKey="-----BEGIN PUBLIC KEY-----\n"+
//	    			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArOX685w2/wuOtCbKMgk6\n"+
//	    			"y3GFVLDb9iF9Q/WKr4NFDIOy1fInxbmqAOvt+m99lD6ZMHG5F1z7v6QkpPgSJLdd\n"+
//	    			"WFrPRJyGOUI7nr/TL+IsxeY9pz6SWHjt2W7FqG5UTHBaQ85ojPtnJhZ/AM/Q9CYl\n"+
//	    			"sbrRcrRUMkZHSUAmrv7C+o1nOrCFs8z9lZYoyRFXYnv959L6JJNcqpC25V11dsVb\n"+
//	    			"KX0cXYMSaYvO058uAyfIqdW0WeDcdYjRN73qwI8XtaBNNp/xCZ2+BiMWNdYSJ8KR\n"+
//	    			"d2y2dH1HYIXBl57kijSAeTiLGxczNln2OO7h+58GqT0XDO6+6KeYgKqwjSA9Yes6\n"+
//	    			"2wIDAQAB\n"+
//	    			"-----END PUBLIC KEY-----";
//	    	System.out.println(verifySignature(xSignature,requestBody,publicKey));
//		}

}
