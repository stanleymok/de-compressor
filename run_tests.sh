export JUNIT_HOME=$(pwd)
export CLASSPATH=$CLASSPATH:$JUNIT_HOME/junit-4.13.2.jar
export CLASSPATH=$CLASSPATH:$JUNIT_HOME/hamcrest-2.2.jar
cd test
echo "Compiling..." && javac *.java && echo "Compilation Successful." \
	&& echo "Running JUnit Tests..." && java TestRunner && echo "Tests Successful."
