package io.vertx.redis.containers;

import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.security.auth.x500.X500Principal;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public final class KeyPairAndCertificate {
  private final PrivateKey privateKey;
  private final PublicKey publicKey;
  private final X509Certificate certificate;

  private KeyPairAndCertificate(PrivateKey privateKey, PublicKey publicKey, X509Certificate certificate) {
    this.privateKey = privateKey;
    this.publicKey = publicKey;
    this.certificate = certificate;
  }

  /**
   * Generates a new key pair (RSA, 2048 bits) and a self-signed certificate for it (SHA256withRSA).
   * The certificate has a distinguished name of {@code CN=name}, has no subject alternative names
   * and is valid for 1 year.
   */
  public static KeyPairAndCertificate generateSelfSigned(String name) {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      KeyPair keyPair = keyPairGenerator.generateKeyPair();

      Date from = Date.from(Instant.now().minus(2, ChronoUnit.DAYS));
      Date to = Date.from(Instant.now().plus(365, ChronoUnit.DAYS));
      BigInteger serialNumber = new BigInteger(64, new SecureRandom());
      X500Principal owner = new X500Principal("CN=" + name);

      X509V3CertificateGenerator certificateGenerator = new X509V3CertificateGenerator();
      certificateGenerator.setIssuerDN(owner);
      certificateGenerator.setSubjectDN(owner);
      certificateGenerator.setNotBefore(from);
      certificateGenerator.setNotAfter(to);
      certificateGenerator.setSerialNumber(serialNumber);
      certificateGenerator.setPublicKey(keyPair.getPublic());
      certificateGenerator.setSignatureAlgorithm("SHA256withRSA");
      X509Certificate certificate = certificateGenerator.generate(keyPair.getPrivate());

      return new KeyPairAndCertificate(keyPair.getPrivate(), keyPair.getPublic(), certificate);
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates a new keystore of type JKS that contains a single entry with alias of {@code key} and empty password.
   * The entry contains the private key and the certificate. The keystore has an empty password as well.
   */
  public byte[] privateKeyAsJKS() {
    try {
      KeyStore keyStore = KeyStore.getInstance("JKS");
      keyStore.load(null, null);
      keyStore.setKeyEntry("key", privateKey, "".toCharArray(), new Certificate[]{certificate});
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      keyStore.store(bytes, "".toCharArray());
      return bytes.toByteArray();
    } catch (GeneralSecurityException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String privateKeyAsPEM() {
    return new StringBuilder()
      .append("-----BEGIN PRIVATE KEY-----\n")
      .append(Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(privateKey.getEncoded()))
      .append("\n-----END PRIVATE KEY-----\n\n")
      .toString();
  }

  /**
   * Creates a new truststore of type JKS that contains a single entry with alias of {@code cert}.
   * The entry contains the certificate. The truststore has an empty password.
   */
  public byte[] certificateAsJKS() {
    try {
      KeyStore trustStore = KeyStore.getInstance("JKS");
      trustStore.load(null, null);
      trustStore.setCertificateEntry("cert", certificate);
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      trustStore.store(bytes, "".toCharArray());
      return bytes.toByteArray();
    } catch (GeneralSecurityException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String certificateAsPEM() {
    try {
      return new StringBuilder()
        .append("-----BEGIN CERTIFICATE-----\n")
        .append(Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(certificate.getEncoded()))
        .append("\n-----END CERTIFICATE-----\n\n")
        .toString();
    } catch (CertificateEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
