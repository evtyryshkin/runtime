FROM gradle:8.6-jdk17

ENV SDK_URL="https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip" \
    ANDROID_SDK_ROOT="/opt/android-sdk"

RUN mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools" .android \
    && cd "$ANDROID_SDK_ROOT/cmdline-tools" \
    && curl -o sdk.zip $SDK_URL \
    && unzip sdk.zip \
    && rm sdk.zip \
    && mv ${ANDROID_SDK_ROOT}/cmdline-tools/cmdline-tools ${ANDROID_SDK_ROOT}/cmdline-tools/latest \
    && mkdir "$ANDROID_SDK_ROOT/licenses" || true \
    && echo "24333f8a63b6825ea9c5514f83c2829b004d1fee" > "$ANDROID_SDK_ROOT/licenses/android-sdk-license" \
    && echo "84831b9409646a918e30573bab4c9c91346d8abd" > "$ANDROID_SDK_ROOT/licenses/android-sdk-preview-license"

RUN $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --update
RUN $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager "build-tools;35.0.0" \
    "platforms;android-35" \
    "platform-tools"
