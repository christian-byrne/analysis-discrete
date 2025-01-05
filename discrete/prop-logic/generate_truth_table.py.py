# https://docs.sympy.org/latest/modules/logic.html
# https://pypi.org/project/prettytable/
# https://github.com/tr3buchet/truths/blob/master/truths/truths.py


from sympy import symbols, simplify_logic, ask, Q
from sympy.logic.boolalg import And, Or, Not, Implies, Equivalent, truth_table, to_cnf
from sympy.logic.inference import satisfiable, valid
from sympy.logic.tests import test_boolalg, test_inference, test_dimacs
import itertools
from prettytable import PrettyTable
from prettytable import MARKDOWN
import re

# Define propositional variables
p, q, r, s = symbols("p q r s")

# Example of simplifying a logical expression
expr = (p & q) | (p & ~q)
simplified_expr = simplify_logic(expr)

import itertools
import re
from prettytable import PrettyTable, MARKDOWN
from sympy.logic.boolalg import And, Or, Not, Implies, Equivalent
from sympy.abc import symbols

from itertools import product
from prettytable import PrettyTable, MARKDOWN
from sympy.logic.boolalg import to_cnf
from sympy.abc import symbols

from sympy import sympify, true, false, Or

class TruthTableGenerator:
    def __init__(self, variables, expressions, ints=True):
        self.variables = variables
        self.expressions = expressions
        self.ints = ints

    def _convert_to(self, expression, to_format):
        replacements = {
            "0": "False",
            "1": "True",
            "&": "&and;",
            "|": "&or;",
            "~": "&not;",
            ">>": "&rarr;",
            "==": "&equiv;",
        }
        for sympy_symbol, html_symbol in replacements.items():
            if to_format == "html":
                expression = expression.replace(sympy_symbol, html_symbol)
            elif to_format == "sympy" or True:
                expression = expression.replace(html_symbol, sympy_symbol)
        return expression

    def generate_truth_table(self):
        # Initialize the table with the variable names and the expressions in header row
        table = PrettyTable(
            self.variables + [self._convert_to(expr, "html") for expr in self.expressions],
            border=False,
            preserve_internal_border=True,
        )
        table.set_style(MARKDOWN)

        for row_values in product([1, 0], repeat=len(self.variables)):
            # Evaluate the expressions in each variable-truth-permuatation's row
            row_evaluations = [
                str(self.evaluate_expression(expr, row_values))
                for expr in self.expressions
            ]
            # Convert the sympy symbols and bits in the expression to html
            row_evaluations = [self._convert_to(expr, "html") for expr in row_evaluations]
            table.add_row([self._convert_to(str(val), "html") for val in row_values] + row_evaluations)

        string_table = str(table)

        return string_table

    def evaluate_expression(self, expression, values):
        # Create a dictionary of the variable names and their values
        symbols_dict = {var: val for var, val in zip(self.variables, values)}
        expression = self._convert_to(expression, "sympy")

        # Get the truth table for the expression
        table = truth_table(expression, symbols_dict.keys())
        for t in table:
            # Find the matching row
            is_match = False
            table_values = t[0]
            for i in range(len(values)):
                if str(table_values[i]) == str(values[i]):
                    is_match = True
                else:
                    is_match = False
                    break
            if is_match:
                result = t[1]
                break

        return str(result)


# ---------------------------------------------------------
# Usable Functions


def create_truth_table(variables, propositions, ints=False):
    table = TruthTableGenerator(variables, propositions, ints=ints)
    print(table.generate_truth_table())

def convert_format(expression, from_format, to_format):
    replacements = [
        ["0", "False", "F", "Zero"],
        ["1", "True", "T", "One"],
        ["&", "&and;", "∧", "^"],
        ["|", "&or;", "∨", "V"],
        ["~", "&not;", "¬", "!"],
        [">>", "&rarr;", "→", "->"],
        ["==", "&harr;", "↔", "<->"],
        ["==", "&equiv;", "≡", "="],
    ]
    keys = ["sympy", "html", "unicode", "ascii"]
    if from_format not in keys or to_format not in keys:
        raise ValueError(f"Invalid format: {to_format}. Must be 'sympy', 'html', 'unicode', or 'ascii'.")
    
    from_index = keys.index(from_format)
    to_index = keys.index(to_format)

    for replacement in replacements:
        expression = expression.replace(replacement[from_index], replacement[to_index]) 

    return expression

def print_simplified(expression):
    expression = to_cnf(expression, True)
    print(expression)

def print_solutions(sympy_expression):
    solutions = satisfiable(sympy_expression, all_models=True)
    for solution in solutions:
        print(solution)

# ---------------------------------------------------------

variables = ["p", "q", "r", "s"]
propositions = [
    "~p | ~q | r",
    "~p | q | ~s",
    "p | ~q | ~s",
    "~p | ~r | ~s",
    "p | q | ~r",
    "p | ~r | ~s"
]

print(convert_format("(~p | ~q | r) & (~p | q | ~s) & (p | ~q | ~s) & (~p | ~r | ~s) & (p | q | ~r) & (p | ~r | ~s)", "sympy", "html"))