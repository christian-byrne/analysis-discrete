import sympy as sp

OPCOUNT = 100_000

def solve_for_x(func_str):
    # Define the symbol
    x = sp.symbols('x')
    
    # Parse the input function string into a sympy expression
    func = sp.sympify(func_str)
    
    # Set the function equal to 10000 and subtract 10000 to find the root
    equation = func - OPCOUNT
    
    # Try to find a numerical solution using nsolve, providing an initial guess
    try:
        solution = sp.nsolve(equation, x, 10)  # You can adjust the initial guess (10)
        return int(round(solution))  # Round to the nearest integer
    except sp.SympifyError:
        return "Invalid function"
    except Exception as e:
        return f"Error: {str(e)}"

# Example usage:
func = '10*x'
result = solve_for_x(func)
print(f"Function '{func}' = 10_000 => x = {result}")

func = '20*x'
result = solve_for_x(func)
print(f"Function '{func}' = 10_000 => x = {result}")

func = '5*x*log(x, 2)'  # This is 5x*log2(x)
result = solve_for_x(func)
print(f"Function '{func}' = 10_000 => x = {result}")

func = '2*n**2'
result = solve_for_x(func)
print(f"Function '{func}' = 10_000 => x = {result}")

func = '2**x'
result = solve_for_x(func)
print(f"Function '{func}' = 10_000 => x = {result}")
