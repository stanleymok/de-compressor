export LIB_FOLDER=lib
export JUNIT_HOME=$(pwd)/$LIB_FOLDER
export CLASSPATH=$CLASSPATH:$JUNIT_HOME/junit-4.13.2.jar
export CLASSPATH=$CLASSPATH:$JUNIT_HOME/hamcrest-2.2.jar
echo "Compiling Test Files..." && javac *.java && echo "Compilation Successful." \
	&& echo "Running JUnit Tests..." && java TestRunner && echo "Tests Successful."