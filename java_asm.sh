rm -fr *.class
java -javaagent:instr_helloworld/target/instr_helloworld-1.0-SNAPSHOT-jar-with-dependencies.jar -jar helloword/target/helloword-1.0-SNAPSHOT-jar-with-dependencies.jar
