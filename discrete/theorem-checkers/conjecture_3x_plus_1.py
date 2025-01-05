def three_x_plus_1_conjecture_steps(start_num: int) -> str:
    """
    Returns the steps of the 3x + 1 conjecture for a given number.
    """
    cur_step_num = 0
    steps = []
    while start_num != 1:
        cur_step_num += 1
        if start_num % 2 == 0:
            next_num = start_num // 2
            steps.append(
                f"{cur_step_num}. $T({start_num})"
                + "= \\frac{"
                + str(start_num)
                + "}{2} = {"
                + str(next_num)
                + "}$\n"
            )
            start_num = next_num
        else:
            next_num = 3 * start_num + 1
            steps.append(
                f"{cur_step_num}. $T({start_num})"
                + "= 3 \\times "
                + str(start_num)
                + f" + 1 = {next_num}$\n"
            )
            start_num = next_num
    return "".join(steps)


if __name__ == "__main__":
    start_num = 17

    print(three_x_plus_1_conjecture_steps(start_num))
