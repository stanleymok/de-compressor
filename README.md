# de-compressor
Software to compress / decompress files in Java, with unit/integration tests in JUnit.

## To Run
These have been tested on WSL on windows.

### Prerequisites
- java
- javac

### To Compile and Run Unit + Integration Tests
Simply run the bash script that runs compilation and JUnit tests
```
$ ./run.sh
```

### Compress
```
$ java Main <folder-to-be-compressed> <output-folder> <max-compressed-file-in-MB>
```
### Decompress
```
$ java Main <folder-to-be-decompressed> <output-folder>
```
