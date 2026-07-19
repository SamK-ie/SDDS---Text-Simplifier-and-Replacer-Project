# Algorithmic Text Similarity & Word Embeddings Toolset

---

## Overview
This project is a command-line Java utility built to parse raw text streams, clean data using common-word dictionaries, and map individual vocabulary tokens against high-density word embeddings. Developed as a core project for the Higher Diploma in Software Development curriculum, the application demonstrates solid object-oriented fundamentals, input sanitization, and the practical use of memory-efficient lookup maps (`HashMap`). The codebase is intentionally structured to showcase algorithmic problem-solving and structured software engineering principles to technical recruiters.

---

## Features
- **In-Memory Map Lookups:** Loads high-volume word embeddings files directly into optimized `HashMap` structures, ensuring rapid constant-time $O(1)$ coordinate retrieval.
- **Dynamic Programming Array Matching:** Implements a bottom-up 2D matrix Levenshtein distance algorithm ($O(L_1 \times L_2)$ string complexity) to find exact typographical edits between text tokens and known dictionary targets.
- **Stop-Word Filtering:** Automatically filters input text streams against a common-words dictionary to ignore structural noise and calculate aggregated text similarity metrics.
- **Sanitized Terminal Loop:** Features a robust CLI state machine that strips trailing white space and literal quote artifacts (`^\"(.*?)\"$`) often created when dragging files into modern terminals.
- **Clean Separation of Concerns:** Isolates the main bootstrap environment initialization, user interface loops, and computational backends into separate, dedicated classes.
- **Zero Third-Party Libraries:** Built entirely using native standard Java packages (`java.io` and `java.util`), making the codebase highly portable and trivial to run out-of-the-box.

---

## Installation

1. Clone the repository:
   ```bash
   git clone [https://github.com/SamK-ie/word-embeddings-similarity-search.git](https://github.com/SamK-ie/word-embeddings-similarity-search.git)

2. Navigate to the project root directoy:
   ```bash
   cd word-embeddings-similarity-search

3. Compile the source code architecture into a separate binaries directory:
   ```bash
   javac -d bin src/ie/atu/sw/*.java

---

## Execution and Usage

1. Launch the application bytecode through the Java Virtual Machine:
   ```bash
   java -cp bin ie.atu.sw.Runner

2. Follow the stateful menu prompts to configure your file infrastructure and execute the analysis loop.

### User Interface Preview

```Code snippet
************************************************************
*      ATU - Dept. of Computer Science & Applied Physics   *
*                                                          *
*           Similarity Search with Word Embeddings         *
*                                                          *
************************************************************
(1) Specify Embedding File
(2) Specify Common Words File
(3) Specify an Input File
(4) Specify an Output File (default: ./out.txt)
(5) Run Comparison
(6) Change Font Colour
(7) Quit

Please Select an Option: 1
Please input the Embedding text file path: /users/sam/data/embeddings.txt

```
---

## System Design and Complexity Analysis

The codebase divides presentation logic, file I/O operations, and dynamic string processing algorithms across separate files within the 'ie.atu.sw' package layout:

- **`Runner.java`**: Acts as the initial entry point, coordinating the startup lifecycle of the runtime menu environment.
- **`MenuHandler.java`**: Houses the interactive interface loop, input stream formatting rules, and step-by-step menu validation checks.
- **`EmbeddingsHandler.java`**: Handles disk I/O, streaming file rows, parsing plain text strings, and loading the data arrays into memory buffers.
- **`WordComparison.java`**: Contains the core similarity math engines, managing matrix distance calculations and writing matching text summaries back to a destination path.

---

### Runtime Analysis

Let $N$ be the total unique words in the input text map, $M$ be the total words in the common words dictionary, and $L_1, L_2$ represent the average lengths of strings compared.

| Code Module/Layer | Complexity | Underlaying Data Structure | Performance Profile |
| :--- | :--- | :--- | :--- |
| **Bootstrap Ingestion** | $O(1)$ | Stack Reference | Instantiates underlying console handlers and state parameters without upfront processing weight. |
| **State Validation** | $O(1)$ | Conditional Flags | Checks local variables for path references to prevent premature calculation calls. |
| **Token Update Pass** | $O(N)$ | `Map.Entry` Iterator | Loops through active map structures to calculate input stream coverage coefficients against a target map. |
| **String Edit Distance** | $O(L_1 \times L_2)$ | 2D Primitive Array | Builds an in-memory cost matrix to find optimal single-character insertions, deletions, and substitutions. |
| **Full Matrix Mapping** | $O(N \times M)$ | Nested Entry Loop | Evaluates each item in the text stream against the full layout of the common dictionary array. |

---

## Production Hardening Roadmap
To demonstrate how this localised utility scales toward production-grade processing systems, the following milestones are planned:

- **Stream-Based Buffer Ingestion:** Replacing standard `Scanner` file reading with a lower-level `BufferedReader` and custom token splitters to drastically lower memory usage and garbage collection spikes when processing multi-gigabyte text files.
- **Centralised Operational Control:** Re-factoring the relationship between presentation handlers and life-cycle loops to transition toward a unified loop control structure, avoiding split execution layers between the UI classes and the bootstrap runner.
- **Concurrent Processing Engine:** Migrating the core text evaluation loops from a single-threaded architecture to an asynchronous worker thread pool (`ExecutorService`), allowing large files to be broken into chunks and processed in parallel.

## License
This project is open-source software distributed under the terms of the MIT License.

## Contributing
Contributions are welcome! If you have suggestions for performance optimisations, multi-threading layers, or extra data parsing features, please fork the repository and submit a pull request.

## Authors
- Samantha Kenny [@SamK-ie](https://github.com/SamK-ie)
