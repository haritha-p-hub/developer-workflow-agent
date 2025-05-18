# Cursor Demo Structured Project

## Structure
- `.cursor/`: Contains individual workflow and command `.mdc` files

## Usage
Run the deployment frequency workflow with Cursor or Copilot:

```bash
cursor run .cursor/workflow-deployment-frequency.mdc --set developer=haritha.ponugoti
```

## Using the Cursor Agent CLI

### 1. Show Help and Available Commands
```bash
cursor --help
```

### 2. Run a Workflow
To execute a workflow defined in an `.mdc` file:
```bash
cursor run .cursor/workflow-executor.mdc --set developer="Haritha Ponugoti" --set usecase="leadtimeforchange" --set requirements="Implement lead time for change for a given time range and interval for requested team"
```

### 3. Set Workflow Inputs
You can pass workflow variables using `--set`:
```bash
cursor run .cursor/workflow-executor.mdc --set developer="Your Name" --set usecase="yourusecase" --set requirements="Your requirements here"
```

### 4. Troubleshooting
- If you do not see any output, try `cursor --help` or check your installation.
- Ensure the `cursor` CLI is in your system PATH.
- If you get errors about missing files or folders, make sure your `.mdc` files and referenced scripts are up to date and paths are correct.
- For more details, consult the Cursor Agent documentation or run with verbose/debug flags if available.
