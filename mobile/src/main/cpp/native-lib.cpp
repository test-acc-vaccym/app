#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_gitlab_PCU_PCU_MainScreen_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++\n(yay)";
    return env->NewStringUTF(hello.c_str());
}
