# Static Analysis of Smart Contracts Dissertaion Project

To run the system run Run.java

You may provide bytecode and address by using the variables "bytecode" and "address" respectively in Decode.java.

To compile the project from the command line and run the system run: 

`javac src/decode/*.java && javac src/decode/formatBytecode/*.java && javac src/decode/programFlow/*.java  && javac src/opcodes/*.java && javac src/stack/*.java && javac Run.java && java Run`

By default a list of opcodes will be produced, for alternative outputs replace `java Run` with `java Run arg` where arg is:

'opcodes' for a full list of opcodes

'branch' to produce the full branch structure

'simple branch' to produce a simplified branch structure
