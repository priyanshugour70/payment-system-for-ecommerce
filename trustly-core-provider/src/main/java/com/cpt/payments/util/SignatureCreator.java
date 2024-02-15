package com.cpt.payments.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Component;

import com.cpt.payments.pojo.request.Attributes;
import com.cpt.payments.pojo.request.Data;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class SignatureCreator {


	private static final Logger LOGGER = LogManager.getLogger(SignatureCreator.class);

	private static final String PRIVATE_KEY_PATH = "./src/main/resources/private.pem";
	
	public String serializeData(JsonNode jsonNode) {
        StringBuilder serialized = new StringBuilder();
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Stream<String> sortedFieldNames = StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(objectNode.fieldNames(), 0), false)
                .sorted();
            sortedFieldNames.forEach(fieldName -> {
                JsonNode valueNode = objectNode.get(fieldName);
                serialized.append(fieldName);
                if (valueNode.isObject() || valueNode.isArray()) {
                    serialized.append(serializeData(valueNode));
                } else {
                    serialized.append(valueNode.asText());
                }
            });
        } else if (jsonNode.isArray()) {
            jsonNode.elements().forEachRemaining(element -> serialized.append(serializeData(element)));
        } else {
            serialized.append(jsonNode.asText());
        }
        return serialized.toString();
    }

	/*
	 * public String serializeData(Object object) { if (object == null) { return "";
	 * }
	 * 
	 * if (object instanceof Map) { // Sort the keys in lexicographical order
	 * TreeMap<String, Object> sortedMap = new TreeMap<>();
	 * 
	 * @SuppressWarnings("unchecked") Map<String, Object> inputMap = (Map<String,
	 * Object>) object; sortedMap.putAll(inputMap);
	 * 
	 * StringBuilder serialized = new StringBuilder(); for (Map.Entry<String,
	 * Object> entry : sortedMap.entrySet()) { serialized.append(entry.getKey());
	 * serialized.append(serializeData(entry.getValue())); } return
	 * serialized.toString(); } else { // Handle scalar values (non-array/non-hash)
	 * return object.toString(); } }
	 */
	
	public PrivateKey getPrivate() throws IOException {
		LogMessage.debug(LOGGER, "getPrivate started:" + PRIVATE_KEY_PATH);

		String key = new String(Files.readAllBytes(Paths.get(PRIVATE_KEY_PATH)), Charset.defaultCharset());
		LogMessage.log(LOGGER, "Read file successfully!!! PRIVATE_KEY_PATH:" + PRIVATE_KEY_PATH);

		if (key.contains("-----BEGIN PRIVATE KEY-----")) {
			LogMessage.debug(LOGGER, "inside IF BEGIN PRIVATE KEY");

			String privateKeyPEM = key.replace("-----BEGIN PRIVATE KEY-----", "").replaceAll(System.lineSeparator(), "")
					.replace("-----END PRIVATE KEY-----", "");
			byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
			KeyFactory keyFactory;
			try {
				keyFactory = KeyFactory.getInstance("RSA");
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);

				LogMessage.debug(LOGGER, "IN IF About to generate & return privateKey");
				return keyFactory.generatePrivate(keySpec);
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} else {
			LogMessage.debug(LOGGER, "inside ELSE");

			Security.addProvider(new BouncyCastleProvider());
			try {
				KeyFactory factory = KeyFactory.getInstance("RSA");
				File privateKey = new File(PRIVATE_KEY_PATH);
				FileReader keyReader = new FileReader(privateKey);
				PemReader pemReader = new PemReader(keyReader);
				PemObject pemObject = pemReader.readPemObject();
				byte[] content = pemObject.getContent();

				LogMessage.debug(LOGGER, "Read File PRIVATE_KEY_PATH:" + PRIVATE_KEY_PATH);


				PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
				pemReader.close();

				LogMessage.debug(LOGGER, "IN ELSE About to generate & return privateKey");

				return (RSAPrivateKey) factory.generatePrivate(privKeySpec);
			}catch (Exception e){
				e.printStackTrace();
			} finally {

			}
		}

		return null;
	}

	public String generateSignature(String requestBody) throws Exception {
		PrivateKey privateKey = getPrivate();
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(privateKey);
		signature.update(requestBody.getBytes(StandardCharsets.UTF_8));
		byte[] digitalSignature = signature.sign();
		return Base64.getEncoder().encodeToString(digitalSignature);
	}

	public static void main(String[] args) throws Exception {
		SignatureCreator sg = new SignatureCreator();
		
		/*
		 * {
			    "user": {
			        "firstName": "john",
			        "lastName": "peter",
			        "email": "johnpeter@gmail.com",
			        "phoneNumber": "+919393939393"
			    },
			    "payment": {
			        "paymentMethod": "APM",
			        "paymentType": "SALE",
			        "amount": "18.10",
			        "currency": "EUR",
			        "merchantTransactionReference": "ecom-xyz-7",
			        "providerId": "Trustly",
			        "creditorAccount": "4242424242424242",
			        "debitorAccount": "4111111111111111"
			    }
			}
		 */
		
		//Preparing objects
		Attributes attributes = Attributes.builder()
				.country("LT")
				.locale("en")
				.currency("EUR")
				.amount(18.10)
				.firstname("john")
				.lastname("peter")
				.email("johnpeter@gmail.com")
				.failURL("https://somedomain.com/failure/trustly/ref1")
				.successURL("https://somedomain.com/success/trustly/ref1")
				.build();

		Data data = Data.builder()
				.username("CTPuser")
				.password("CTPpassword")
				.notificationURL("https://somedomain.com/trustly/notify/ref1")
				.endUserID("user1-id")
				.messageID("msg1-id")
				.attributes(attributes)
				.build();
		
		String method = "Deposit";
		String UUID = "67d6c2f3-51b3-4eed-ad1a-16b4c4063c33";
		
		/*
		 * Gson gson = new Gson(); String jsonString = gson.toJson(data);
		 * 
		 * ObjectMapper objectMapper = new ObjectMapper(); JsonNode jsonNode =
		 * objectMapper.readTree(jsonString);
		 */
		;
		
		String jsonString = JsonUtils.toJsonString(data);
		JsonNode jsonNode = JsonUtils.toJsonNode(data);
		
		String serializedData = sg.serializeData(jsonNode);
		
		System.out.println("jsonString:" + jsonString + "\n|serializedData:" + serializedData);
		
		String plainText = method + UUID + serializedData;
		String signature = sg.generateSignature(plainText);
		
		System.out.println("signature:" + signature);
		
		SHA256RSASignatureVerifier sigVerify = new SHA256RSASignatureVerifier();
		
		boolean isSigVerified = sigVerify.verifySignature(signature, plainText);
		System.out.println("isSigVerified:" + isSigVerified);
	}

}
