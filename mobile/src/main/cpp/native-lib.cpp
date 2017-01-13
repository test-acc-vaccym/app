#include <jni.h>
#include <string>

/*JNIEXPORT jstring JNICALL
Java_com_gitlab_PCU_PCU_MainScreen_do_1auth(JNIEnv *env, jobject instance) {

    // TODO


    return env->NewStringUTF(returnValue);
}*/

extern "C"
jstring
Java_com_gitlab_PCU_PCU_MainScreen_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++\n(yay)";
    return env->NewStringUTF(hello.c_str());
}
