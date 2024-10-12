#!/bin/bash

# Get the system architecture
arch="$(dpkg --print-architecture)"

# Determine JDK download URL based on the architecture
case "$arch" in
  "amd64")
    jdkUrl="https://aka.ms/download-jdk/microsoft-jdk-${TARGET_JAVA_VERSION}-linux-x64.tar.gz"
    ;;
  "arm64")
    jdkUrl="https://aka.ms/download-jdk/microsoft-jdk-${TARGET_JAVA_VERSION}-linux-aarch64.tar.gz"
    ;;
  *)
    echo >&2 "error: unsupported architecture: '$arch'"
    exit 1
    ;;
esac

# Download the JDK and checksum files
wget --progress=dot:giga -O msopenjdk.tar.gz "${jdkUrl}"
wget --progress=dot:giga -O sha256sum.txt "${jdkUrl}.sha256sum.txt"

# Validate the downloaded file with the checksum
sha256sumText=$(cat sha256sum.txt)
sha256=$(expr substr "${sha256sumText}" 1 64)
echo "${sha256} msopenjdk.tar.gz" | sha256sum --strict --check -

# Clean up the checksum file
rm sha256sum.txt*

# Create the JAVA_HOME directory and extract the JDK files
mkdir -p "$JAVA_HOME"
tar --extract \
    --file msopenjdk.tar.gz \
    --directory "$JAVA_HOME" \
    --strip-components 1 \
    --no-same-owner

# Clean up the tar file
rm msopenjdk.tar.gz*

# Create symbolic links for JAVA_HOME
ln -s ${JAVA_HOME} /docker-java-home
ln -s ${JAVA_HOME} /usr/local/openjdk-${TARGET_JAVA_VERSION}
