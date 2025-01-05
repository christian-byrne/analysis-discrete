from termcolor import colored


def create_power_set(input_set):
    power_set = []
    for i in range(2 ** len(input_set)):
        subset = []
        for j in range(len(input_set)):
            if (i >> j) % 2 == 1:
                subset.append(input_set[j])
        power_set.append(subset)
    return power_set


if __name__ == "__main__":

    # ------ Example Usage ------
    # input_set = "a"
    # input_set = [1, 2, 3]
    # input_set = [1, 2, 3, [1], 4]
    # input_set = ["a", "b", 3, [1, 59], 4]
    # input_set = [[], "a", ["a"], [["a"]]]

    input_set = [[]]

    if type(input_set) == str or type(input_set) == int or type(input_set) == float:
        input_set = list(input_set)
    power_set = create_power_set(input_set)

    print(f"\n\n\n{colored('Input Set', 'cyan')} {input_set}\n")
    print(colored("Elements of Power Set:", "green"))
    for i in range(len(power_set)):
        print(f"{i+1}. {power_set[i]}")
    print()
    print(colored("Power Set:", "light_green"))
    print("{" + f"{', '.join([str(subset) for subset in power_set])}" + "}")
    print()
    print(colored("Number of Elements in Power Set:", "green"), len(power_set))
