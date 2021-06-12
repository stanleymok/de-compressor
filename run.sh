# Compile and run unit tests
echo "Compiling Source Files..." && javac Main.java && echo "Compilation Done." && \
cd test && ./run_tests.sh && cd -
java Main /mnt/c/Users/Stanley_Mok/OneDrive/Desktop/to_comp /mnt/c/Users/Stanley_Mok/OneDrive/Desktop/to_decomp 0.5  && \
java Main /mnt/c/Users/Stanley_Mok/OneDrive/Desktop/to_decomp /mnt/c/Users/Stanley_Mok/OneDrive/Desktop/to_comp3
rm src/*.class *.class