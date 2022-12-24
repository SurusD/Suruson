# Suruson
Parsing values
# Example
```Java
String code = "@Open(file.java) @File(path)";
Suruson parser = new Suruson(code);
parser.START = '@';
parser.START_PARAMETER = '(';
parser.END_PARAMETER = ')';
try {
println(Suruson.SurusonValue.valuesToString(parser.values(false)));
} catch (Exception e) {
println(e.getMessage());
}
```
