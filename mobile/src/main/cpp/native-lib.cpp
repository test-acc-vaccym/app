#include <jni.h>
#include <string>

extern "C"
jstring
Java_net_nghorst_l_tim_rust_MainScreen_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++\n(yay)";
    return env->NewStringUTF(hello.c_str());
}
