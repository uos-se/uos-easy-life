# Build from microsoft's dev container base image
ARG BASE_IMAGE_VERSION_CODENAME=bookworm
FROM mcr.microsoft.com/devcontainers/base:${BASE_IMAGE_VERSION_CODENAME}

# Install Java
USER root
ARG TARGET_JAVA_VERSION=21
ENV JAVA_HOME /usr/lib/jvm/msopenjdk-current
ENV PATH "${JAVA_HOME}/bin:${PATH}"
ENV LANG en_US.UTF-8
COPY javainstall.sh /tmp/javainstall.sh
RUN chmod +x /tmp/javainstall.sh && /tmp/javainstall.sh

# Install required tools
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive && apt-get -y install \
  git-crypt\
  supervisor