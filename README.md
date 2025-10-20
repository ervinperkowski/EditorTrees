# EditorTrees

A **height-balanced, rank-based tree** implementation in Java designed to optimize internal text editor structures. This project focuses on algorithmic efficiency and data organization for fast character insertion, deletion, and retrieval operations.

## Overview

EditorTrees uses an **AVL-style balanced binary search tree** where each node stores:
- A single character
- A **rank** (size of left subtree) enabling O(log n) position-based access
- A **balance code** (LEFT, SAME, RIGHT) for height tracking
- Left and right child references

This structure supports efficient text editor operations such as inserting characters at arbitrary positions, deleting characters, and retrieving substrings—all with logarithmic time complexity.

## Project Structure

```
EditorTrees/
├── editortrees/
│   ├── EditTree.java               # Main tree wrapper class
│   ├── Node.java                   # Core node implementation with rotations
│   ├── DisplayableBinaryTree.java  # Visual tree display utility
│   ├── DisplayableNodeWrapper.java # Node wrapper for visualization
│   ├── EditTreeMilestone1Test.java # Tests for basic operations
│   ├── EditTreeMilestone2Test.java # Tests for insertions/deletions
│   └── EditTreeMilestone3Test.java # Tests for complex rotations
├── lib/
│   ├── junit-4.13.2.jar           # JUnit testing framework
│   └── hamcrest-core-1.3.jar      # Hamcrest matchers for JUnit
├── out/                            # Compiled .class files
└── README.md
```

## Key Features

### Core Operations
- **Insertion**: Add characters at any position with automatic rebalancing
- **Deletion**: Remove characters while maintaining tree balance
- **Get**: Retrieve individual characters or substrings by position
- **Clone**: Deep copy of entire tree structure

### Balancing
- **Single rotations**: Fix left-left and right-right imbalances
- **Double rotations**: Fix left-right and right-left imbalances
- Automatic balance code updates during insertions and deletions
- Rank maintenance across all tree modifications

### Utilities
- String-based tree construction with recursive partitioning
- Rank-based position tracking for O(log n) character access
- Debug output showing node ranks and balance codes
- Rotation count tracking for performance analysis

## Building the Project

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- JUnit 4.13.2 and Hamcrest 1.3 (included in `lib/`)

### Compilation

From the project root directory:

```powershell
javac -cp ".;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" -d out editortrees\*.java
```

**Note for PowerShell users**: The classpath must be quoted to prevent `;` from being interpreted as a command separator.

### Compiling Without Tests

To compile only the core classes (without test dependencies):

```powershell
javac -d out editortrees\EditTree.java editortrees\Node.java editortrees\DisplayableBinaryTree.java editortrees\DisplayableNodeWrapper.java
```

## Running Tests

Execute milestone test suites using JUnit:

```powershell
# Milestone 1: Basic operations
java -cp "out;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore editortrees.EditTreeMilestone1Test

# Milestone 2: Insertions and deletions
java -cp "out;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore editortrees.EditTreeMilestone2Test

# Milestone 3: Complex rotations
java -cp "out;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore editortrees.EditTreeMilestone3Test
```

## Architecture

### EditTree Class
The main wrapper providing public API methods:
- `add(char ch, int pos)` - Insert character at position
- `delete(int pos)` - Remove character at position
- `get(int pos)` - Retrieve character at position
- `get(int pos, int length)` - Retrieve substring
- `toString()` - Generate full string representation

### Node Class
Internal tree node with sophisticated balancing logic:
- Recursive insert/delete with rebalancing
- Single and double rotation methods
- Balance code management
- Rank updates during modifications
- In-order traversal for string generation

### Balance Codes
- **SAME (=)**: Left and right subtrees have equal height
- **LEFT (/)**: Left subtree is one level taller
- **RIGHT (\\)**: Right subtree is one level taller

### Rank System
Each node's rank equals the size of its left subtree, enabling O(log n) position lookups without storing explicit indices.

## Code Style

The codebase uses **minimal inline comments** in the format `//comment` (no space after `//`). Comments are concise labels summarizing complex logic blocks:
- `//leaf-insert` - Base case for insertion
- `//rot-L` / `//rot-R` - Single rotations
- `//fix-LL` / `//fix-LR` - Imbalance corrections
- `//step-left` / `//step-right` - Traversal directions

## Testing Status

### ✅ Milestone 1: **PASSING** (23/23 tests)
- Basic tree construction
- Simple insertions and deletions
- String representation
- Rank validation

### ✅ Milestone 2: **PASSING** (tests passing)
- Complex insertions with rotations
- Deletion with rebalancing
- Edge case handling

### ⚠️ Milestone 3: **PARTIALLY COMPLETE** (89/93 tests passing)
- Advanced rotation scenarios
- Large-scale tree operations
- **Note**: 4 test failures remain in complex rotation cases

---

## Project Status

This implementation successfully handles the majority of balanced tree operations required for text editor functionality. However, **Milestone 3 is not entirely complete**, with 4 test failures remaining in advanced rotation scenarios involving complex sequences of insertions and deletions. These edge cases represent rare but specific imbalance patterns that require further debugging in the rebalancing logic.

The core functionality for typical text editing operations (insert, delete, retrieve) works correctly for standard use cases.
