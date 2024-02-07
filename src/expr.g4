grammar expr;

// Lexer rules
INT: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]* | '.' [0-9]+;
STRING: '"' .*? '"';
VARIABLE: [a-zA-Z]+ | [0-9]+;
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
ASSIGN: '=';
PRINT: 'print>>';

// Whitespace and newline rules
WS: [ \t\r\n]+ -> skip;

// Parser rules
program: statement+;

statement: assignment | print;

assignment: VARIABLE ASSIGN (value | operation | arithmetic | STRING);

print: PRINT (value | operation | arithmetic | VARIABLE | STRING);

arithmetic: value ((PLUS | MINUS) value)*;

operation: value ((MULTIPLY | DIVIDE) value)*;

value: FLOAT | INT | VARIABLE;