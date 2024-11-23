import itertools
from collections import deque
from typing import List, Deque
from rich import print

# Initial sequence of pairs
pairs = "EL DO BY ED EM EF UT ZA EN WO ER MU KI NO MI OD FE AG AD HA HE TA RE SI PE SO AB HI ID AA GO AI KA AS IT AL PA UN TI PI QI NU LA MA LI MO BI BE OH ON".split()

# Configuration for the buffers
buffer_size = 4  # Buffer holds up to 4 pairs
num_buffers = 4  # Total of 4 buffers available (3 input + 1 output)
output_frequency = 5  # Show buffer states every fifth write


# Multiway merge function simulating external merge sort
def multiway_merge(num_buffers, buffer_size, output_frequency):
    """
    This algorithm summary omits many details in the interests of space and intellectual investigation. Assume
    that an unordered file of records is given, and a quantity (B, where B ≥ 3) of buffer pages is provided:
    1 // Create the initial collection of sorted runs
    2
    3 Pass 0: repeat
    4       Fill all B buffers with blocks from the file
    5       Sort the records in the buffers in-place
    6       Output the sorted run of records
    7   until the input file is exhausted
    8
    9 // Now use B-1 buffers for input and 1 for output
    10
    11 Passes 1-n: while the number of sorted runs is > 1:
    12  while the input file still has unread runs:
    13      repeat
    14          Read in next block from each of the next B-1 runs
    15          Merge those blocks into the remaining buffer,
    16              writing it out as it fills, and
    17              replacing input blocks as content is exhausted
    18      until the longest of those B-1 runs is exhausted
    19  end while
    20 end while
    """
    # Initialize the run counter
    run_counter = 0

    # Initialize the list of runs
    runs = []

    buffers = [deque() for _ in range(num_buffers)]

    # Assume that we can store buffer_size records per file block, 
    # and there are num_buffers available buffers. For the following comments,
    # We will use the example collection of key values:
    # `91 16 3 21 46 18 31 71 63 82 12 85 6 42 8`

    def sort_buffers_inplace(buffers: List[Deque[int]]) -> List[Deque[int]]:
        sorted_buffers = [deque(sorted(list(buffer))) for buffer in buffers]
        return sorted_buffers

    def merge_buffers(buffers: List[Deque[int]], output: Deque[int]):
        while any(buffers):
            # Find the smallest key
            smallest_index = None
            smallest_value = None
            for i, buffer in enumerate(buffers):
                if buffer:
                    head = buffer.popleft()
                    if smallest_index is None or head < smallest_value:
                        smallest_index = i
                        smallest_value = head
                    else:
                        buffer.appendleft(head)

            output.append(smallest_value)

        return output

    # Pass 0: Fill buffers, sort, output the sorted run, 
    #         and repeat until each key is in a sorted run:
    # Go in chunks of buffer_size * num_buffers
    initial_input = deque(pairs)
    sorted_runs: List[Deque[int]] = []
    while initial_input:
        # Fill all buffers with blocks from the file

        added_num = 0
        current_buffer_index = 0

        while added_num < buffer_size * num_buffers and initial_input:
            if len(buffers[current_buffer_index]) == buffer_size:
                current_buffer_index += 1

            buffers[current_buffer_index].append(initial_input.popleft())
            added_num += 1

        # Sort the records in the buffers in-place
        buffers = sort_buffers_inplace(buffers)

        # Merge the buffers
        sorted_run = deque(sorted(list(itertools.chain(*buffers))))
        # print(f"Run {run_counter}: {sorted_run}")

        # Output the sorted run of records
        sorted_runs.append(sorted_run)

    print(f"Num sorted runs: {len(sorted_runs)}")
    print(f"Pass 0: Sorted runs: `{[list(run) for run in sorted_runs]}`")
    final_output = []
    # Pass 1: This is the only merging pass, because there are two sorted runs and B − 1 = 2. To start, bring in the first block of each sorted run. Current [window] positions within runs are shown on the right
    # print(sorted_runs)     
    for sorted_run_index, sorted_run in enumerate(sorted_runs):
        for i in range(0, buffer_size):
            buffers[sorted_run_index].append(sorted_run.popleft())

    # Merge values from the input buffers to the output buffer until the output is full or an input is exhausted. Here the output buffer filled first, it was written, and 16 & 12 (in bold) are remembered as the next to be compared
    write_ct = 0
    idx = 1
    while any(sorted_runs):
        # Find the smallest key
        smallest_index = None
        smallest_value = None
        for i, buffer in enumerate(buffers):
            if buffer:
                head = buffer.popleft()
                if smallest_index is None or head < smallest_value:
                    smallest_index = i
                    smallest_value = head
                else:
                    buffer.appendleft(head)

        if smallest_index is None:
            break

        buffers[-1].append(smallest_value)

        # If output buffer is full, output it
        if len(buffers[-1]) == buffer_size:
            write_ct += 1
            final_output = merge_buffers(buffers, final_output)
            if write_ct % 5 == 0:
                print(f"\n\nInput buffers: `{[list(buffer) for buffer in buffers]}`")
            # print(f"Output: {final_output}")
            # print(f"Buffers: {buffers}")

        # Replenish any empty buffers
        for i, buffer in enumerate(buffers):
            if i == len(buffers) - 1:
                continue
            if not buffer:
                for j in range(0, buffer_size):
                    if i < len(sorted_runs) and sorted_runs[i]:
                        buffers[i].append(sorted_runs[i].popleft())
                    else:
                        #When one of the runs has been completely consumed (in this example, the second), we copy all remaining items from the remaining (first) run to the output and write it:
                        final_output = merge_buffers(buffers, final_output)
                        # Add the remaining items from the buffer_count - 1 runs to the output
                        for k in range(len(buffers) - 1):
                            if k >= len(sorted_runs):
                                break
                            while sorted_runs[k]:
                                final_output.append(sorted_runs[k].popleft())

                        sorted_runs = sorted_runs[len(buffers) - 1:]
                        print(f"Pass {idx}: Sorted runs: `{[list(run) for run in sorted_runs]}`")
                        idx += 1
                        break
                

    return final_output


# Run the multiway merge algorithm
output = multiway_merge(num_buffers, buffer_size, output_frequency)
