"""


Cache lookup for tuple/string keys:
  O(k + 1)
    k ⊨ length of the key
    1 ⊨ constant time for hash lookup

Cache insertion (amortized):
  O(k + 1)
    k ⊨ length of the key
    1 ⊨ constant time for hash lookup
  
Cache insertion (amortized) assuming hash not repeated:
  O(1)
    1 ⊨ constant time for hash lookup

"""

from rich import print
from rich.table import Table
from rich.panel import Panel
from rich.console import Console

console = Console()


def expected_value_cache_hit(
    parameter_space: int,
    n_calls: int,
    preview_interval: int = 1000,
    print_progress_prevs: bool = False,
    print_equations: bool = False,
    print_parameters: bool = True,
):
    parameter_space = float(parameter_space)
    n_calls = float(n_calls)

    EV_cache_hits = 0
    EV_cache_misses = 0

    print("\n\n")

    if print_parameters:
        console.print(
            Panel(
                f"[bold green]Estimating Expected Cache Hits[/bold green]\n[bold]Parameter Space:[/bold] {int(parameter_space)}\n[bold]Number of Calls:[/bold] {int(n_calls)}",
                expand=False,
            ),
            justify="left",
        )
        print()

    if print_progress_prevs:
        summary_table = Table(title="Cache Hit/Miss Estimation Progress")
        summary_table.add_column("Call Number", justify="right")
        summary_table.add_column("Expected Cache Hits", justify="right")
        summary_table.add_column("Expected Cache Misses", justify="right")
        summary_table.add_column("P(Cache Hit)", justify="right")
        summary_table.add_column("P(Cache Miss)", justify="right")

    for call_n in range(int(n_calls)):
        if call_n == 0:
            EV_cache_misses += 1
            continue

        if EV_cache_hits >= parameter_space:
            console.print(
                Panel(
                    f"[bold yellow]Cache expected to span parameter space at call number {call_n}[/bold yellow]\n[bold cyan]Remaining calls will all be cache hits. Breaking loop...[/bold cyan]"
                )
            )
            EV_cache_hits += n_calls - call_n
            break

        EV_unseen = (parameter_space - call_n) + EV_cache_hits

        P_cache_miss = EV_unseen / parameter_space
        P_cache_hit = 1 - P_cache_miss

        EV_cache_hits += P_cache_hit
        EV_cache_misses += P_cache_miss

        if print_progress_prevs and call_n % preview_interval == 0:
            summary_table.add_row(
                str(call_n),
                f"{round(EV_cache_hits)} ({EV_cache_hits:.6f})",
                f"{round(EV_cache_misses)} ({EV_cache_misses:.6f})",
                f"{P_cache_hit:.4f}",
                f"{P_cache_miss:.4f}",
            )

    if print_progress_prevs:
        console.print(summary_table)

    EV_cache_insertions = min(EV_cache_hits, parameter_space)
    original_time_complexity = f"O({int(n_calls)}r)"
    memoized_time_complexity = f"O({int(n_calls)}k + {int(n_calls)} + {EV_cache_insertions:.4f} + {EV_cache_misses:.4f})r"
    if print_equations:
        print(
            "\nk := mean length of (string/tuple) parameter key\nr := function call work\nn := number of calls\nm := expected number of cache misses\nc := work for hash lookup and insertion"
        )
        print(f"Original Complexity:\n\t{original_time_complexity}")
        print(f"Memoized Complexity:\n\t{memoized_time_complexity}")
        print(
            "\nFind instance variables s.t.\n\tr * n > n * k + c + m * r ⇒\n\tr > (nk + c)/(n - m)"
        )
        print()

    insert_lookup_work = n_calls + EV_cache_insertions

    def get_r_threshold(k: int):
        """k: mean length of (string/tuple) parameter key"""
        if n_calls - EV_cache_misses == 0:
            return float("inf")
        return (n_calls * k + insert_lookup_work) / (n_calls - EV_cache_misses)

    # Create table for k values
    k_table = Table(
        title="Function Work Thresholds to Justify Memoization per k (mean key length)",
        title_style="bold magenta",
    )
    k_table.add_column("k", justify="right")
    k_table.add_column("r Threshold", justify="right")

    k_values_to_consider = (4, 16, 32, 128, 512)
    r_thresholds = []
    for k in k_values_to_consider:
        r_threshold = get_r_threshold(k)
        k_table.add_row(f"{k}", f"{r_threshold:.4f}")
        r_thresholds.append(r_threshold)

    console.print(k_table, justify="left")

    # Final Results
    final_results_table = Table(title="Final Results", title_style="bold magenta")
    final_results_table.add_column("Metric", justify="left")
    final_results_table.add_column("Value", justify="right")

    final_results_table.add_row(
        "Expected Cache Hits", f"{round(EV_cache_hits)} ({EV_cache_hits:.6f})"
    )
    final_results_table.add_row(
        "Expected Cache Misses", f"{round(EV_cache_misses)} ({EV_cache_misses:.6f})"
    )
    final_results_table.add_row(
        "Final Hit Probability", f"{EV_cache_hits / n_calls:.6f}"
    )

    console.print(final_results_table, justify="left")

    results = {
        "Parameter Space": parameter_space,
        "Calls": n_calls,
        "E[Cache Hit]": EV_cache_hits,
        "E[Cache Miss]": EV_cache_misses,
        "E[Cache Ins]": EV_cache_insertions,
        # "original_time_complexity": original_time_complexity,
        # "memoized_time_complexity": memoized_time_complexity,
    }
    TUPLE_OVERHEAD = 24  # bytes
    for kv, rv in zip(k_values_to_consider, r_thresholds):
        tuple_mem_estimate = round(
            EV_cache_insertions * (kv + float(TUPLE_OVERHEAD))
        )  # bytes
        string_mem_estimate = round(EV_cache_insertions * kv)

        tuple_mem_estimate_kb = tuple_mem_estimate / 1024
        string_mem_estimate_kb = string_mem_estimate / 1024

        results[f"O(Fn) for k={kv} (Tuple mb, Str kb)"] = (
            f"{round(rv)} ({tuple_mem_estimate_kb:.2f}kb,{string_mem_estimate_kb:.2f}kb)"
        )

    return results


param_spaces = [16, 32, 64, 128, 512, 2049, 65536]
n_call_space = [8, 32, 128, 2048, 32768]
rows = []

from itertools import product

for param_space, n_calls in product(param_spaces, n_call_space):
    results = expected_value_cache_hit(param_space, n_calls, print_parameters=False)
    rows.append(results)

results_table = Table(
    title="Results for Various Parameter Space Sizes and Number of Calls",
    title_style="bold magenta",
)

for col in rows[0].keys():
    results_table.add_column(col, justify="right")

for row in rows:
    results_table.add_row(
        *[str(round(v)) if isinstance(v, (int, float)) else v for v in row.values()]
    )

console.print(results_table, justify="left")
