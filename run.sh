# Compile and run unit tests
echo "Compiling Source Files..." && javac Main.java && echo "Compilation Done." && \
cd test && ./run_tests.sh && cd -
# housekeep
rm src/*.class *.class
