# Clear workspace
printf "\nClearing workspace...\n"
rm ca* fake* localhost*

# Create CA
#echo "Creating CA..."
printf "\nCreating CA...\n"
openssl req -new \
            -x509 \
            -keyout fake-ca-key \
            -out fake-ca-cert \
            -days 365 \
            -passin pass:test1234 \
            -passout pass:test1234 \
            -subj "/C=BR/ST=Sao Paulo/L=Sao Paulo/O=Hatanaka/OU=Hatanaka/CN=localhost"

# Create a Key Store
printf "\nCreating Key Store JKS...\n"
keytool -keystore localhost.keystore.jks \
        -alias localhost \
        -validity 365 \
        -genkey \
        -keyalg RSA \
        -storepass test1234 \
        -keypass test1234 \
        -dname "CN=localhost, OU=Hatanaka, O=Hatanaka, L=Sao Paulo, ST=Sao Paulo, C=BR"

# Create a Certificate Signing Request
printf "\nCreating Certificate Signing Request...\n"
keytool -keystore localhost.keystore.jks \
        -alias localhost \
        -certreq \
        -file ca-cert \
        -storepass test1234 \
        -keypass test1234

# Import CA to truststore
printf "\nImporting CA to Trust Store JKS...\n"
keytool -keystore localhost.truststore.jks \
        -alias CARoot \
        -import \
        -file fake-ca-cert \
        -storepass test1234 \
        -keypass test1234 \
        -noprompt

# Sign the Certificate
printf "\nSigning Certificate...\n"
openssl x509 -req \
              -CA fake-ca-cert \
              -CAkey fake-ca-key \
              -in ca-cert \
              -out ca-cert-signed \
              -days 365 \
              -CAcreateserial \
              -passin pass:test1234

# Import the CA cert into the Key Store
printf "\nImporting CA into Key Store...\n"
keytool -keystore localhost.keystore.jks \
        -alias CARoot \
        -import \
        -file fake-ca-cert \
        -storepass test1234 \
        -keypass test1234 -noprompt

# Import the signed cert into the Key Store
printf "\nImporting Signed Certificate...\n"
keytool -keystore localhost.keystore.jks \
        -alias localhost \
        -import \
        -file ca-cert-signed \
        -storepass test1234 \
        -keypass test1234 \
        -noprompt

# Create credentials
printf "\nCreating credentials...\n"
tee localhost_sslkey_creds << EOF >/dev/null
test1234
EOF

tee localhost_keystore_creds << EOF >/dev/null
test1234
EOF

tee localhost_truststore_creds << EOF >/dev/null
test1234
EOF

############### PKCS12 - keystore and truststore #############################
printf "\nCreating CA for Kafka...\n"
openssl req -new \
            -newkey rsa:2048 \
            -keyout fake-kafka-ca-key \
            -out fake-kafka-ca-cert \
            -passin pass:test1234 \
            -passout pass:test1234 \
            -subj "/C=BR/ST=Sao Paulo/L=Sao Paulo/O=Hatanaka/OU=Hatanaka/CN=localhost" \
            -nodes

printf "\nSigning Certificate Kafka...\n"
openssl x509 -req \
              -days 365 \
              -in fake-kafka-ca-cert \
              -CA fake-ca-cert \
              -CAkey fake-ca-key \
              -CAcreateserial \
              -out fake-kafka-ca-cert-signed \
              -passin pass:test1234

printf "\nCreating PEM file...\n"
cat fake-ca-cert fake-ca-key > fake-ca.pem

printf "\nCreating .p12 certificate...\n"
openssl pkcs12 -export \
    -in fake-kafka-ca-cert-signed \
    -inkey fake-kafka-ca-key \
    -CAfile fake-ca.pem \
    -name fake-ca-cert \
    -out fake-ca-cert.p12 \
    -password pass:test1234

printf "\nCreating Key Store PKCS12...\n"
keytool -importkeystore \
    -deststorepass test1234 \
    -destkeystore localhost.keystore.pkcs12 \
    -srckeystore fake-ca-cert.p12 \
    -deststoretype PKCS12  \
    -srcstoretype PKCS12 \
    -noprompt \
    -srcstorepass test1234

printf "\nListing Keys...\n"
keytool -list -v \
    -keystore localhost.keystore.pkcs12 \
    -storepass test1234

printf "\nImporting CA to Trust Store PKCS12...\n"
keytool -keystore localhost.truststore.pkcs12 \
    -alias CARoot \
    -import \
    -file fake-ca-cert \
    -storepass test1234  \
    -noprompt \
    -storetype PKCS12

printf "\nCoppying Key Store and Trust Store PKCS12...\n"
DIR_SSL="../../src/main/resources/ssl/"

if [ ! -d "$DIR_SSL" ]; then
  mkdir "$DIR_SSL"
fi

cp ./*.pkcs12 $DIR_SSL