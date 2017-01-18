#include <jni.h>

extern "C"

JNIEXPORT jboolean JNICALL
Java_com_gitlab_PCU_PCU_MainScreen_do_1auth(JNIEnv *env, jobject instance, jintArray arr) {
    return (jboolean) true;
}

