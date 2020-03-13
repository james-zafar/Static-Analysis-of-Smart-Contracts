# Static Analysis of Smart Contracts Dissertaion Project
To compile the project from the command line, navigate to the root directory and run:

`java -jar target/Implementatxion-1.0-SNAPSHOT.jar <arg>`

By default a list of opcodes will be produced, for alternative outputs replace `<arg>` with:

'opcodes' for a full list of opcodes

'branch' to produce the full branch structure

'simple branch' to produce a simplified branch structure

Bytecode and caller address may be provided using the variables "bytecode" and "address" respectively in src/main/java/decode/decodeBytes/Decode.java.
