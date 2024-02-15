grammar expr;

// Lexer rules
INT: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]* | '.' [0-9]+;
STRING: '"' .*? '"';
//VARIABLE: [a-zA-Z]+; // Adjusted rule for variable names
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
ASSIGN: '=';
PRINT: 'print>>';
FOR: 'for';
IN_RANGE: 'in range';
WHILE: 'while';
IF: 'if';
EQUAL: '==';
NOT_EQUAL: '!=';
GREATER: '>';
LESS: '<';
GREATER_EQUAL: '>=';
LESS_EQUAL: '<=';


// Whitespace and newline rules
WS: [ \t\r\n]+ -> skip;

// Lexer predicate to exclude reserved keywords
VARIABLE: [a-zA-Z]+ { getText().matches("^(?!if$|while$|for$).*$") };

// Parser rules
program: statement+;

statement: assignment | print | control_statement;

control_statement: while_loop| for_loop | if_statemnt;

while_loop: WHILE boolean;

if_statemnt: IF boolean;

for_loop: FOR VARIABLE IN_RANGE (INT | VARIABLE);

assignment: VARIABLE ASSIGN (value | operation | arithmetic | STRING);

print: PRINT (value | operation | arithmetic | STRING);

boolean: value (EQUAL | NOT_EQUAL | GREATER | LESS | GREATER_EQUAL | LESS_EQUAL) value;

arithmetic: value ((PLUS | MINUS) value)+;

operation: value ((MULTIPLY | DIVIDE) value)+;

value: FLOAT | INT | VARIABLE;
