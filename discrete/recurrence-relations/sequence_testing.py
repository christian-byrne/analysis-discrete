import math


def seq(n):
    """Return floor(n / 2) + ceil(n / 2)"""
    return (n // 2) + math.ceil(n / 2)


def test_seq():
    for n in range(4):
        print(seq(n))


def question_8():
    out = [1, 2, 0]

    for i in range(3, 5):
        next_term = out[-1] + out[-3]
        out.append(next_term)

    out = [str(i) for i in out]
    print("$" + ", ".join(out) + "$")


for i in range(5):
    print(2**i + 5 * 3**i)

if __name__ == "__main__":
    # test_seq()
    # question_8()
    pass
