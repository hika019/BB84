load("@rules_java//java:defs.bzl", "java_binary")
java_binary(
    name = "BB84",
    srcs = glob(["src/BB84.java", "src/BraKetVector.java"]),
    deps = ["aparapi3", "aparapi-jni", "apach"],
    main_class = "BB84",
)

java_binary(
    name = "test",
    srcs = glob(["src/Test.java", "src/BraKetVector.java"]),
    main_class = "start",
)


java_import(
    name = "aparapi2",
    jars = ["aparapi-2.0.0.jar"],
)

java_import(
    name = "aparapi3",
    jars = ["aparapi-3.0.0.jar"],
)

java_import(
    name = "aparapi-jni",
    jars = ["aparapi-jni-1.4.3.jar"],
)

java_import(
    name = "apach",
    jars = ["bcel-6.6.0.jar", "commons-lang3-3.12.0.jar"],
)