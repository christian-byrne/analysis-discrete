from typing import List, Tuple
from collections import Counter

class ApphineasCipher:
    def __init__(self, key: Tuple[int, int], target: str, initial_state: str = "unencrypted"):
        self.multipler = key[0]
        self.shift = key[1]
        self.target = target
        self.state = initial_state



    def __call__(self, print_out=True):
        prev = self.target
        prev_numbers = self.__letters_as_numbers(prev)
        self.target = self.__cipher(self.target)
        new_numbers = self.__letters_as_numbers(self.target)
        self.__switch_state()
        out_str = "\n" + f"- {prev}\n- " + str(prev_numbers) + f"\n- {new_numbers}" +  ":\n- " + self.target
        if print_out:
            print(out_str)
        return self

    def __cipher(self, target: str):
        return "".join(
            [
                chr(((ord(char) - 65) * self.multipler + self.shift) % 26 + 65)
                if char.isalpha()
                else char
                for char in target
            ]
        )
    
    def __switch_state(self):
        if self.state == "unencrypted":
            self.state = "encrypted"
        else:
            self.state = "unencrypted"

        self.multipler = -self.multipler
        self.shift = -self.shift

    def __letters_as_numbers(self, target: str, range: int = 26):
        return [ord(char) - 65 if char.isalpha() else char for char in target]
    
class ShiftCipher:
    def __init__(self, shift: int, target: str, initial_state: str = "unencrypted"):
        self.shift = shift
        self.target = target
        self.state = initial_state

    def __call__(self, print_out=True):
        prev = self.target
        prev_numbers = self.__letters_as_numbers(prev)
        self.target = self.__shift_string(self.target)
        new_numbers = self.__letters_as_numbers(self.target)
        self.__switch_state()
        out_str = "\n" + f"Previous: {prev}\n" + str(prev_numbers) + "\n" + self.state.upper() + ":\n" + self.target + f"\n{new_numbers}"
        if print_out:
            print(out_str)
        return self

    def __letters_as_numbers(self, target: str, range: int = 26):
        return [ord(char) - 65 if char.isalpha() else char for char in target]

    def __shift_string(self, target: str):
        return "".join(
            [
                chr((ord(char) - 65 + self.shift) % 26 + 65)
                if char.isalpha()
                else char
                for char in target
            ]
        )

    def __switch_state(self):
        if self.state == "unencrypted":
            self.state = "encrypted"
        else:
            self.state = "unencrypted"

        self.shift = -self.shift

class TransposeCipher:
    PAD_CHAR = "X"

    def __init__(self, key: List[int], target, initial_state: str = "unencrypted"):
        self.target = target
        print(f"Target: {self.target}")
        self.key = key
        print(f"Key: {str(key):>40}")
        self.chunk_size = len(key)
        print(f"Chunk size: {self.chunk_size}")
        self.target_len = len(target)
        self.state = initial_state
        pad_n = self.chunk_size - (self.target_len % self.chunk_size)
        print(f"Padding Lenth = chunk_size - (target_length % chunk_size) = {pad_n}")
        self.target += TransposeCipher.PAD_CHAR * pad_n
        print(f"Padded target: {self.target}")

        if key == self.invert_key():
            print(
                f"Key is self-reciprocal\nKey: {str(key):>40}\nInverted key: {str(self.invert_key()):>31}"
            )
        else:
            print(
                f"Key is not self-reciprocal\nKey: {str(key):>40}\nInverted key: {str(self.invert_key()):>40}"
            )

    def __call__(self, print_out=True):
        self.target = "".join(
            ["".join([chunk[i] for i in self.key]) for chunk in self.chunkify()]
        )
        self.__switch_state()
        out_str = "\n" + self.state.upper() + ":\n" + self.target + "\n"
        if print_out:
            print(out_str)
        return self

    def invert_key(self):
        return [self.key.index(i) for i in range(len(self.key))]

    def __switch_state(self):
        if self.state == "unencrypted":
            self.state = "encrypted"
        else:
            self.state = "unencrypted"

        self.key = self.invert_key()

    def chunkify(self):
        return [
            self.target[i : i + self.chunk_size]
            for i in range(0, self.target_len, self.chunk_size)
        ]


class ShiftDecrypter:
    def __init__(self, target: str):
        self.target = target
        self.most_common = self.most_common_letter(target.replace(" ", "").upper())
        print(f"- Most common letter: {str(self.most_common)}")
        self.shift = ord(self.most_common) - ord("E")
        print(f"- Shift = {ord(self.most_common)} - 4 = {self.shift}")
        self.decrypted = self.__shift_string(target)
        self.decrypted_nums = self.__letters_as_numbers(self.decrypted)
        print(f"- {self.decrypted_nums}")
        print(f"- Decrypted: {self.decrypted}")


    def most_common_letter(self, target: str):
        return max(set(target), key=target.count) 

    def __shift_string(self, target: str):
        return "".join(
            [
                chr((ord(char) - 65 - self.shift) % 26 + 65)
                if char.isalpha()
                else char
                for char in target
            ]
        )
    
    def __letters_as_numbers(self, target: str, range: int = 26):
        return [ord(char) - 65 if char.isalpha() else char for char in target]

if __name__ == "__main__":
    key = [2, 3, 0, 4, 1]
    target = "GRIZZLYBEARS"
    cipher = TransposeCipher(key, target)()()

    # shift = 13
    # target = "DO NOT PASS GO"
    # cipher = ShiftCipher(shift, target)()
    
    # key = (
    #     1, # multiplier
    #     -17, # shift
    # )
    # target = "DVE CFMV KF NFEUVI, REU KYRK ZJ KYV JVVU FW JTZVETV"
    # cipher = ApphineasCipher(key, target)()

    # target = "ODVE OPCFGYFEVJ IRKVU OOO"
    # ShiftDecrypter(target)
